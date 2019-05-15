package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.providers.CommandExecutionProvider;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public class CommandPurchase implements Purchase {

    @Getter private final String command;
    private final PurchaseDisplayData purchaseDisplayData;
    private final CommandExecutionProvider commandExecutionProvider;
    @Getter private int id;

    /**
     * Purchase of permission.
     * @param command Command for execution (%player% variable replaces with player name).
     * @param purchaseDisplayData Display data for Menu.
     * @param commandExecutionProvider CommandExecutionProvider.class instance.
     */
    public CommandPurchase(String command, PurchaseDisplayData purchaseDisplayData, CommandExecutionProvider commandExecutionProvider) {
        this.command = command;
        this.purchaseDisplayData = purchaseDisplayData;
        this.commandExecutionProvider = commandExecutionProvider;
    }

    public static Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, PurchaseDisplayData purchaseDisplayData, JsonObject jsonObject) {
        String command = null;

        if (jsonObject.has("command")) {
            command = jsonObject.get("command").getAsString();
        }

        return new CommandPurchase(command, purchaseDisplayData, shoppingCartRebornPlugin.getCommandExecutionProvider());
    }

    @Override
    public Purchase setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean give(User user) {
        return commandExecutionProvider.execute(user, command);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "command");

        JsonObject purchaseData = new JsonObject();
        purchaseData.addProperty("command", command);

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());

        return jsonObject;
    }

}
