package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.providers.EconomyProvider;

public class MoneyPurchase implements Purchase {

    @Getter private final double money;
    private final PurchaseDisplayData purchaseDisplayData;
    private final EconomyProvider economyProvider;
    @Getter private int id;

    /**
     * Purchase of Vault money.
     * @param money Money value.
     * @param purchaseDisplayData Display data for Menu.
     * @param economyProvider EconomyProvider.class instance.
     */
    public MoneyPurchase(double money, PurchaseDisplayData purchaseDisplayData, EconomyProvider economyProvider) {
        this.money = money;
        this.purchaseDisplayData = purchaseDisplayData;
        this.economyProvider = economyProvider;
    }

    public static Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, PurchaseDisplayData purchaseDisplayData, JsonObject jsonObject) {
        double money = 0;

        if (jsonObject.has("money")) {
            money = jsonObject.get("money").getAsInt();
        }

        return new MoneyPurchase(money, purchaseDisplayData, shoppingCartRebornPlugin.getEconomyProvider());
    }

    @Override
    public Purchase setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean give(User user) {
        if (economyProvider == null) return false;

        return economyProvider.deposit(user, money);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "money");

        JsonObject purchaseData = new JsonObject();
        purchaseData.addProperty("money", money);

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());


        return jsonObject;
    }

}
