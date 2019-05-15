package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public class GroupPurchase implements Purchase {

    @Getter private final String group;
    @Getter private final long time;
    private final PurchaseDisplayData purchaseDisplayData;
    private final PermissionProvider permissionProvider;
    @Getter private int id;

    /**
     * Purchase of permissions group.
     * @param group Name of the group.
     * @param time Time in seconds (-1 to permanent).
     * @param purchaseDisplayData Display data for Menu.
     * @param permissionProvider PermissionProvider.class instance.
     */
    public GroupPurchase(String group, long time, PurchaseDisplayData purchaseDisplayData, PermissionProvider permissionProvider) {
        this.group = group;
        this.time = time;
        this.purchaseDisplayData = purchaseDisplayData;
        this.permissionProvider = permissionProvider;
    }

    public static Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, PurchaseDisplayData purchaseDisplayData, JsonObject jsonObject) {
        String group = null;
        long time = -1;

        if (jsonObject.has("group")) {
            group = jsonObject.get("group").getAsString();
        }

        if (jsonObject.has("time")) {
            time = jsonObject.get("time").getAsLong();
        }

        return new GroupPurchase(group, time, purchaseDisplayData, shoppingCartRebornPlugin.getPermissionProvider());
    }

    @Override
    public Purchase setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean give(User user) {
        if (time == -1)
            return permissionProvider.addToGroup(user, group);
        else return permissionProvider.addToGroup(user, group, time);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "group");

        JsonObject purchaseData = new JsonObject();
        purchaseData.addProperty("group", group);

        if (time != -1)
            purchaseData.addProperty("time", time);

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());

        return jsonObject;
    }

}
