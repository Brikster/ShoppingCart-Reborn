package ru.mrbrikster.shoppingcartreborn.cart;

import com.google.gson.JsonObject;
import ru.mrbrikster.shoppingcartreborn.serialization.Deserializer;

public class PurchaseDeserializer implements Deserializer<Purchase> {

    @Override
    public Purchase deserialize(JsonObject jsonObject) {
        if (jsonObject.has("type")) {
            switch(jsonObject.get("type").getAsString().toLowerCase()) {
                case "group":
                    String group = null;
                    long time = 0;

                    if (jsonObject.has("group")) {
                        group = jsonObject.get("group").getAsString();
                    }

                    if (jsonObject.has("time")) {
                        time = jsonObject.get("time").getAsLong();
                    }

                    return new PermissionGroupPurchase(group, time);

                case "money":
                    double money = 0;

                    if (jsonObject.has("money")) {
                        money = jsonObject.get("money").getAsInt();
                    }

                    return new MoneyPurchase(money);
            }
        }

        return null;
    }

}
