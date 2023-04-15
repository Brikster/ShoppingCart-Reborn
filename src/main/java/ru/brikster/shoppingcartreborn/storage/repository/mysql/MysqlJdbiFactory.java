package ru.brikster.shoppingcartreborn.storage.repository.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.storage.entity.Delivery;
import ru.brikster.shoppingcartreborn.storage.repository.JdbiFactory;
import ru.brikster.shoppingcartreborn.util.FileUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.Duration;

public final class MysqlJdbiFactory implements JdbiFactory {

    private final Jdbi jdbi;

    public MysqlJdbiFactory(@NotNull String hostname,
                            int port,
                            @NotNull String database,
                            @NotNull String username,
                            @NotNull String password) throws IOException {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName("ShoppingcartReborn-Pool");
        config.setAutoCommit(false);
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
        config.setIdleTimeout(Duration.ofMinutes(2).toMillis());

        DataSource dataSource = new HikariDataSource(config);

        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());

        jdbi.registerRowMapper(ConstructorMapper.factory(Delivery.class));

        String initQuery = FileUtil.readFileFromResources("mysql.sql");
        jdbi.useHandle(handle -> handle.createScript(initQuery).execute());
    }

    @Override
    public @NotNull Jdbi createJdbi() {
        return jdbi;
    }

}
