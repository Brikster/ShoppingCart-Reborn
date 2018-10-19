package ru.mrbrikster.shoppingcartreborn.cart;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.money.EconomyProvider;

public class MoneyPurchase implements Purchase {

    private final double money;

    /**
     * Purchase of Vault money.
     * @param money money value.
     */
    public MoneyPurchase(double money) {
        this.money = money;
    }

    @Override
    public boolean give(Player player) {
        EconomyProvider economyProvider = ShoppingCartReborn.getInstance().getEconomyProvider();

        if (economyProvider == null) return false;

        return economyProvider.getEconomy().depositPlayer(player, money).transactionSuccess();
    }

    @Override
    public JsonObject serialize(Purchase object) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("money", money);

        return jsonObject;
    }

}
