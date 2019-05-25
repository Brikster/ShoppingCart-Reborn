package ru.mrbrikster.shoppingcartreborn.spigot.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.mrbrikster.shoppingcartreborn.cart.Cart;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.types.ItemPurchase;
import ru.mrbrikster.shoppingcartreborn.spigot.ShoppingCartReborn;
import ru.mrbrikster.baseplugin.commands.BukkitCommand;
import ru.mrbrikster.baseplugin.menu.Icon;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.CartMenu;
import ru.mrbrikster.shoppingcartreborn.spigot.config.Messages;
import ru.mrbrikster.shoppingcartreborn.spigot.utils.Materials;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartCommand extends BukkitCommand {

    CartCommand() {
        super("cart", "shopcart", "shoppingcart");
    }

    @Override
    public void handle(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.get("only-player"));
            return;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "all":
                    ShoppingCartReborn.getInstance().getScheduler().schedule(() -> {
                        User user = BukkitUser.of((Player) sender);
                        Cart cart = Cart.of(ShoppingCartReborn.getInstance(), user);
                        List<Purchase> purchases = cart.getPurchases();

                        if (purchases.isEmpty()) {
                            sender.sendMessage(Messages.get("no-purchases"));
                        } else {
                            purchases.forEach(purchase -> {
                                if (cart.removePurchase(purchase.getId())) {
                                    purchase.give(user);
                                }
                            });
                            sender.sendMessage(Messages.get("cart-all"));
                        }
                    }, 0, TimeUnit.SECONDS, true);
                    break;
                case "open":
                    ShoppingCartReborn.getInstance().getScheduler().schedule(() -> {
                        User user = BukkitUser.of((Player) sender);
                        Cart cart = Cart.of(ShoppingCartReborn.getInstance(), user);
                        List<Purchase> purchases = cart.getPurchases();

                        if (purchases.isEmpty()) {
                            sender.sendMessage(Messages.get("no-purchases"));
                        } else {
                            List<Icon> iconList = new ArrayList<>();

                            purchases.forEach(purchase -> {
                                PurchaseDisplayData purchaseDisplayData = purchase.getDisplayData();

                                iconList.add(Icon.builder()
                                        .type(purchase instanceof ItemPurchase
                                                ? Materials.fromMinecraftId(((ItemPurchase) purchase).getSerializableItem().getMinecraftId())
                                                : Material.CHEST)
                                        .name("&6" + purchaseDisplayData.getName())
                                        .lore(purchaseDisplayData.getLore())
                                        .lore("")
                                        .lore(Messages.get("menu-get-purchase"))
                                        .handler((clickIconAction) -> {
                                            ShoppingCartReborn.getInstance().getScheduler().schedule(() -> {
                                                if (cart.removePurchase(purchase.getId())) {
                                                    purchase.give(user);
                                                }

                                                sender.sendMessage(Messages.get("cart-get"));
                                            }, 0, TimeUnit.SECONDS, true);

                                            clickIconAction.close();
                                        })
                                        .build());
                            });

                            new CartMenu(iconList).open((Player) sender);
                        }
                    }, 0, TimeUnit.SECONDS, true);
                    break;
                default:
                    sender.sendMessage(Messages.get("command-usage").replace("%label%", label));
                    break;
            }
        } else {
            sender.sendMessage(Messages.get("command-usage").replace("%label%", label));
        }
    }

}
