package ru.brikster.shoppingcartreborn;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jdbi.v3.core.Jdbi;
import ru.brikster.shoppingcartreborn.command.CartCommand;
import ru.brikster.shoppingcartreborn.config.GeneralConfig;
import ru.brikster.shoppingcartreborn.listener.PlayerListener;
import ru.brikster.shoppingcartreborn.manager.ShoppingcartManager;
import ru.brikster.shoppingcartreborn.manager.ShoppingcartManagerImpl;
import ru.brikster.shoppingcartreborn.product.ProductFactory;
import ru.brikster.shoppingcartreborn.product.ProductFactoryImpl;
import ru.brikster.shoppingcartreborn.storage.repository.DeliveryRepository;
import ru.brikster.shoppingcartreborn.storage.repository.JdbiFactory;
import ru.brikster.shoppingcartreborn.storage.repository.ProductRepository;
import ru.brikster.shoppingcartreborn.storage.repository.mysql.MysqlDeliveryRepository;
import ru.brikster.shoppingcartreborn.storage.repository.mysql.MysqlJdbiFactory;
import ru.brikster.shoppingcartreborn.storage.repository.mysql.MysqlProductRepository;
import ru.brikster.shoppingcartreborn.storage.repository.postgres.PostgresDeliveryRepository;
import ru.brikster.shoppingcartreborn.storage.repository.postgres.PostgresJdbiFactory;
import ru.brikster.shoppingcartreborn.storage.repository.postgres.PostgresProductRepository;

import lombok.SneakyThrows;

public final class ShoppingcartRebornPlugin extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin instance() {
        return instance;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        ShoppingcartRebornPlugin.instance = ShoppingcartRebornPlugin.this;

        GeneralConfig config = ConfigManager.create(GeneralConfig.class, it -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer());
            it.withBindFile(getDataFolder().toPath().resolve("config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        if (!config.isEnable()) {
            return;
        }

        JdbiFactory jdbiFactory;
        DeliveryRepository deliveryRepository;
        ProductRepository productRepository;

        switch (config.getDatabaseType()) {
            case POSTGRES: {
                jdbiFactory = new PostgresJdbiFactory(
                        config.getCredentials().getHostname(),
                        config.getCredentials().getPort(),
                        config.getCredentials().getDatabase(),
                        config.getCredentials().getUsername(),
                        config.getCredentials().getPassword());
                Jdbi jdbi = jdbiFactory.createJdbi();
                deliveryRepository = new PostgresDeliveryRepository(jdbi);
                productRepository = new PostgresProductRepository(jdbi);
                break;
            }
            case MYSQL: {
                jdbiFactory = new MysqlJdbiFactory(
                        config.getCredentials().getHostname(),
                        config.getCredentials().getPort(),
                        config.getCredentials().getDatabase(),
                        config.getCredentials().getUsername(),
                        config.getCredentials().getPassword());
                Jdbi jdbi = jdbiFactory.createJdbi();
                deliveryRepository = new MysqlDeliveryRepository(jdbi);
                productRepository = new MysqlProductRepository(jdbi);
                break;
            }
            default:
                throw new IllegalArgumentException("Selected database type is not implemented");
        }

        ProductFactory productFactory = new ProductFactoryImpl();
        ShoppingcartManager manager = new ShoppingcartManagerImpl(config, deliveryRepository, productFactory);

        getServer().getPluginManager().registerEvents(new PlayerListener(manager), this);
        getCommand("cart").setExecutor(new CartCommand(config, deliveryRepository, productRepository));
    }

}
