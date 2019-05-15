package ru.mrbrikster.shoppingcartreborn;

import ru.mrbrikster.shoppingcartreborn.database.DatabaseManager;
import ru.mrbrikster.shoppingcartreborn.providers.CommandExecutionProvider;
import ru.mrbrikster.shoppingcartreborn.providers.EconomyProvider;
import ru.mrbrikster.shoppingcartreborn.providers.ItemProvider;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public interface ShoppingCartRebornPlugin {

    EconomyProvider getEconomyProvider();

    PermissionProvider getPermissionProvider();

    CommandExecutionProvider getCommandExecutionProvider();

    ItemProvider getItemProvider();

    DatabaseManager getDatabaseManager();

}
