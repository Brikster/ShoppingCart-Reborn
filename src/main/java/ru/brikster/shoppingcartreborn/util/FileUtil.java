package ru.brikster.shoppingcartreborn.util;

import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.ShoppingcartRebornPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class FileUtil {

    public static @NotNull String readFileFromResources(@NotNull String fileName) throws IOException {
        URL url = ShoppingcartRebornPlugin.class.getClassLoader().getResource(fileName);
        if (url == null) throw new IOException("Cannot read file from resources");

        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        return reader.lines().collect(Collectors.joining("\n"));
    }

}
