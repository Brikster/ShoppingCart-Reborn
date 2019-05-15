package ru.mrbrikster.shoppingcartreborn.cart;

import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;

import java.util.List;

public class Cart {

    private final ShoppingCartRebornPlugin shoppingCartRebornPlugin;
    private final User user;

    private Cart(ShoppingCartRebornPlugin shoppingCartRebornPlugin, User user) {
        this.shoppingCartRebornPlugin = shoppingCartRebornPlugin;
        this.user = user;
    }

    public List<Purchase> getPurchases() {
        return shoppingCartRebornPlugin.getDatabaseManager().getPurchases(user);
    }

    public boolean removePurchase(int id) {
        return shoppingCartRebornPlugin.getDatabaseManager().removePurchase(id);
    }

    public static Cart of(ShoppingCartRebornPlugin shoppingCartRebornPlugin, User user) {
        return new Cart(shoppingCartRebornPlugin, user);
    }

}
