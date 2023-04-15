package ru.brikster.shoppingcartreborn.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import lombok.Getter;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class GeneralConfig extends OkaeriConfig {

    @Comment("Позволяет полностью отключить плагин")
    boolean enable = false;

    @Comment
    @Comment("Название текущего сервера. Позволяет держать товары для разных серверов в одной таблице")
    String serverName = "global";

    @Comment
    @Comment("Один из доступных типов СУБД: POSTGRES или MYSQL")
    private DatabaseType databaseType = DatabaseType.POSTGRES;

    @Comment
    @Comment("Данные для подключения к БД")
    private DatabaseCredentialsConfig credentials = new DatabaseCredentialsConfig();

    public enum DatabaseType {
        POSTGRES,
        MYSQL
    }

    @Getter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class DatabaseCredentialsConfig extends OkaeriConfig {

        private String hostname = "localhost";
        private int port = 5432;
        private String database = "app";
        private String username = "app";
        private String password = "12345q";

    }

}
