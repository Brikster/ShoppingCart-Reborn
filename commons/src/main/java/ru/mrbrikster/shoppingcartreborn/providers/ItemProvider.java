package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.objects.Item;

public interface ItemProvider {

    boolean give(User user, Item item);

}
