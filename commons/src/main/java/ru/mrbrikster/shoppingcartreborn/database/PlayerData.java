/*
 * MCSTUDIO (c) 2019.
 * https://vk.com/mcstudio
 * https://mcstudio.su
 */

package ru.mrbrikster.shoppingcartreborn.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
class PlayerData {

    @Getter private String name;
    @Getter private String password;
    @Getter private String ip;
    @Getter @Setter private String email;
    @Getter private Timestamp session;
    @Getter private String server;

}
