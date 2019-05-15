package ru.mrbrikster.shoppingcartreborn.spigot.cart;

import ru.mrbrikster.shoppingcartreborn.spigot.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.menu.Icon;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.menu.PaginatedMenu;
import ru.mrbrikster.shoppingcartreborn.spigot.config.Messages;

import java.util.List;

public class CartMenu extends PaginatedMenu {

    public CartMenu(List<Icon> iconList) {
        super(ShoppingCartReborn.getInstance(), Messages.get("menu-title"), 3, iconList);
    }

}
