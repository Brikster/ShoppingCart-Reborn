/*
 * MCSTUDIO (c) 2019.
 * https://vk.com/mcstudio
 * https://mcstudio.su
 */

package ru.mrbrikster.shoppingcartreborn.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DatabaseCredentials {

    @Getter private String address;
    @Getter private int port;
    @Getter private String username;
    @Getter private String password;
    @Getter private String database;
    @Getter private String purchasesTable;
    @Getter private String templatesTable;
    @Getter private boolean useSSL;

}