package ru.mrbrikster.shoppingcartreborn.objects;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.mrbrikster.shoppingcartreborn.serialization.Serializable;

import java.util.List;
import java.util.Map;

@Builder
public class SerializableItem implements Serializable {

    @Getter @Setter @NonNull private String minecraftId;
    @Getter @Setter private int dataValue;
    @Getter @Setter private int count;
    @Getter @Setter private String name;
    @Getter @Setter private List<String> lore;
    @Getter @Setter private Map<String, Integer> enchants;

    @Override
    public JsonObject serialize() {
        return null;
    }

}
