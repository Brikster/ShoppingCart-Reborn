package ru.mrbrikster.shoppingcartreborn.objects;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.mrbrikster.shoppingcartreborn.serialization.Serializable;

import java.util.List;

@Builder
public class SerializableItem implements Serializable {

    @Getter @Setter @NonNull public String minecraftId;
    @Getter @Setter public int dataValue;
    @Getter @Setter public int count;
    @Getter @Setter public String name;
    @Getter @Setter public List<String> lore;

    @Override
    public JsonObject serialize() {
        return null;
    }

}
