/*
 * MCSTUDIO (c) 2019.
 * https://vk.com/mcstudio
 * https://mcstudio.su
 */

package ru.mrbrikster.shoppingcartreborn.database;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PlayerData {

    String name;
    String password;
    String ip;
    @NonFinal String email;
    Timestamp session;
    String server;

}
