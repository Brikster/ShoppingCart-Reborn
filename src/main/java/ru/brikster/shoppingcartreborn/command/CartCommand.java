package ru.brikster.shoppingcartreborn.command;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.ShoppingcartRebornPlugin;
import ru.brikster.shoppingcartreborn.config.GeneralConfig;
import ru.brikster.shoppingcartreborn.storage.repository.DeliveryRepository;
import ru.brikster.shoppingcartreborn.storage.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static ru.brikster.shoppingcartreborn.util.AdventureUtil.sendMessage;

@Slf4j
@RequiredArgsConstructor
public final class CartCommand implements CommandExecutor {

    private final static Component ADD_SUBCOMMAND_USAGE =
            text("Использование: /{label} add <игрок> <ID_шаблона>", RED);

    private final GeneralConfig config;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("shoppingcartreborn.command.cart")) {
            sendMessage(sender, text("You don't have permission.", RED));
            return true;
        }

        if (args.length == 0) {
            fallbackHelp(sender, label);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        //noinspection SwitchStatementWithTooFewBranches
        switch (subcommand) {
            case "add":
                addSubcommand(sender, label, args);
                break;
            default:
                fallbackHelp(sender, label);
                break;
        }

        return false;
    }

    private void addSubcommand(@NotNull CommandSender sender,
                               @NotNull String label,
                               @NotNull String @NotNull [] args) {
        if (args.length != 3 && args.length != 4) {
            sendMessage(sender, ADD_SUBCOMMAND_USAGE.replaceText(
                    TextReplacementConfig.builder()
                            .matchLiteral("{label}")
                            .replacement(label)
                            .build()));
            return;
        }

        String playerName = args[1];
        String templateName = args[2];

        String server = config.getServerName();
        if (args.length == 4) {
            server = args[3].toLowerCase();
        }

        try {
            Optional<Long> templateId = productRepository.findIdIfExists(templateName);
            if (!templateId.isPresent()) {
                sendMessage(sender, text("Шаблон с таким названием не найден.", RED));
                return;
            }

            deliveryRepository.createDelivery(playerName, templateId.get(), server);

            sendMessage(sender, text("Товар успешно выдан на сервер " + server + ".", GREEN));
        } catch (Throwable t) {
            log.error("Error while creating delivery", t);
            sendMessage(sender, text("Произошла ошибка при выдаче товара, проверьте консоль.", RED));
        }
    }

    private void fallbackHelp(@NotNull CommandSender sender, @NotNull String label) {
        sendMessage(sender,
                text("")
                        .append(text("ShoppingcartReborn", GOLD, BOLD))
                        .append(newline())
                        .append(text("/{label} add <игрок> <название_шаблона> [сервер]", YELLOW))
                        .append(text(" - ", GRAY))
                        .append(text("выдать товар игроку", WHITE))
                        .replaceText(TextReplacementConfig.builder()
                                .matchLiteral("{label}")
                                .replacement(label)
                                .build()));
    }

}
