package ru.mrbrikster.shoppingcartreborn.spigot.utils;

import org.bukkit.Material;

public class Materials {

    public static Material fromMinecraftId(String minecraftId) {
        Material material;
        if (isInt(minecraftId)) {
            material = Material.getMaterial(Integer.parseInt(minecraftId));
        } else {
            material = Material.valueOf(minecraftId.toUpperCase());
        }

        return material;
    }

    private static boolean isInt(String minecraftId) {
        try {
            Integer.parseInt(minecraftId);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

}
