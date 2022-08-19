package ru.mrbrikster.shoppingcartreborn.spigot.utils;

import org.bukkit.Material;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Materials {

    private static Method materialGetMethod;

    static {
        try {
            Materials.materialGetMethod = Materials.class.getMethod("getMaterial");
        } catch (NoSuchMethodException ignored) {}
    }

    public static Material fromMinecraftId(String minecraftId) {
        Material material;
        if (isInt(minecraftId)) {
            try {
                material = (Material) materialGetMethod.invoke(null, Integer.parseInt(minecraftId));
            } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                throw new IllegalArgumentException("ID materials are not supported on 1.13 and newer", e);
            }
        } else {
            material = Material.matchMaterial(minecraftId);
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
