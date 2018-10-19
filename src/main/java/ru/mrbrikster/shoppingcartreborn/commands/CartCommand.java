package ru.mrbrikster.shoppingcartreborn.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.mrbrikster.shoppingcartreborn.cart.Cart;
import ru.mrbrikster.shoppingcartreborn.cart.Purchase;
import ru.mrbrikster.shoppingcartreborn.config.Configuration;

import java.util.Set;

public class CartCommand extends AbstractCommand {

    public CartCommand() {
        super("cart", "shopcart", "shoppingcart");
    }

    @Override
    public void handle(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Configuration.getMessages().get("only-player"));
            return;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "all":
                    Cart cart = Cart.of((Player) sender);
                    Set<Purchase> purchaseSet = cart.getPurchases();

                    if (purchaseSet.isEmpty()) {
                        sender.sendMessage(Configuration.getMessages().get("no-purchases"));
                    } else {
                        purchaseSet.forEach(purchase -> purchase.give((Player) sender));
                        sender.sendMessage(Configuration.getMessages().get("cart-all"));
                    }
                    break;
                case "put":
                case "load":

                    break;
                case "open":

                    break;
                default: sender.sendMessage(Configuration.getMessages().get("command-usage"));
            }
        } else {
            sender.sendMessage(Configuration.getMessages().get("command-usage"));
        }
    }

}
