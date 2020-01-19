package pl.socketbyte.opengui.serializable;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemSection implements Serializable {

    private int slot;
    private SerializableItemBuilder itemBuilder;

    public ItemSection() {

    }

    public ItemSection(int slot, SerializableItemBuilder builder) {
        this.slot = slot;
        this.itemBuilder = builder;
    }

    public ItemSection(Map<String, Object> data) {
        this.slot = (int) data.get("slot");
        this.itemBuilder = (SerializableItemBuilder) data.get("item");
    }

    @Override
    public void register() {
        ConfigurationSerialization.registerClass(this.getClass());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("slot", slot);
        data.put("item", itemBuilder.serialize());
        return data;
    }

    public int getSlot() {
        return slot;
    }

    public SerializableItemBuilder getItemBuilder() {
        return itemBuilder;
    }
}
