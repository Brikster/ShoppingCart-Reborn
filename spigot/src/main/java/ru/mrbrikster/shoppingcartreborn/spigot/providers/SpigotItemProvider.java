package ru.mrbrikster.shoppingcartreborn.spigot.providers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.objects.Item;
import ru.mrbrikster.shoppingcartreborn.providers.ItemProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;
import ru.mrbrikster.shoppingcartreborn.spigot.utils.Materials;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SpigotItemProvider implements ItemProvider {

    @Override
    public boolean give(User user, Item item) {
        String minecraftId = item.getMinecraftId();

        Material material = Materials.fromMinecraftId(minecraftId);
        ItemStack itemStack = new ItemStack(material, item.getCount());

        if (item.getDataValue() != 0) {
            itemStack.setDurability((short) item.getDataValue());
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (item.getName() != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName()));
        }

        if (item.getLore() != null) {
            itemMeta.setLore(item.getLore().stream().map(loreString -> ChatColor.translateAlternateColorCodes('&', loreString)).collect(Collectors.toList()));
        }

        itemStack.setItemMeta(itemMeta);

        Player player = ((BukkitUser) user).getAsPlayer();
        ShoppingCartReborn.getInstance().getScheduler().schedule(() ->
                player.getInventory().addItem(itemStack).forEach((i, inventoryItemStack) ->
                        player.getWorld().dropItem(player.getLocation(), inventoryItemStack)),
                0, TimeUnit.SECONDS);

        return true;
    }

}
