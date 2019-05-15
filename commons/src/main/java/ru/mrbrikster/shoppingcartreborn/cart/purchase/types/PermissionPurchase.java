package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public class PermissionPurchase implements Purchase {

    @Getter private final String permission;
    @Getter private final long time;
    private final PurchaseDisplayData purchaseDisplayData;
    private final PermissionProvider permissionProvider;
    @Getter private int id;

    /**
     * Purchase of permission.
     * @param permission Permission.
     * @param time Time in seconds (-1 to permanent).
     * @param purchaseDisplayData Display data for Menu.
     * @param permissionProvider PermissionProvider.class instance.
     */
    public PermissionPurchase(String permission, long time, PurchaseDisplayData purchaseDisplayData, PermissionProvider permissionProvider) {
        this.permission = permission;
        this.time = time;
        this.purchaseDisplayData = purchaseDisplayData;
        this.permissionProvider = permissionProvider;
    }

    public static Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, PurchaseDisplayData purchaseDisplayData, JsonObject jsonObject) {
        String permission = null;
        long time = -1;

        if (jsonObject.has("permission")) {
            permission = jsonObject.get("permission").getAsString();
        }

        if (jsonObject.has("time")) {
            time = jsonObject.get("time").getAsLong();
        }

        return new PermissionPurchase(permission, time, purchaseDisplayData, shoppingCartRebornPlugin.getPermissionProvider());
    }

    @Override
    public Purchase setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean give(User user) {
        if (time == -1)
            return permissionProvider.addToGroup(user, permission);
        else return permissionProvider.addToGroup(user, permission, time);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "permission");

        JsonObject purchaseData = new JsonObject();
        purchaseData.addProperty("permission", permission);

        if (time != -1)
            purchaseData.addProperty("time", time);

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());

        return jsonObject;
    }

}
