package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;

public interface CommandExecutionProvider {

    boolean execute(User user, String command);

}
