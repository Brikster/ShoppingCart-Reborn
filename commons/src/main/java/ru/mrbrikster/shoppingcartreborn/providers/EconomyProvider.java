package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;

public interface EconomyProvider {

    boolean deposit(User user, double money);

}
