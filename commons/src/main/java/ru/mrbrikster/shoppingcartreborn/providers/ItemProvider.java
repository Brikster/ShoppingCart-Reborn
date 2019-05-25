package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.objects.SerializableItem;

public interface ItemProvider {

    boolean give(User user, SerializableItem serializableItem);

}
