/*
 * MCSTUDIO (c) 2019.
 * https://vk.com/mcstudio
 * https://mcstudio.su
 */

package ru.mrbrikster.shoppingcartreborn.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data(staticConstructor = "of")
public class DatabaseCredentials {

    String address;
    int port;
    String username;
    String password;
    String database;
    String purchasesTable;
    String templatesTable;
    boolean useSSL;

}