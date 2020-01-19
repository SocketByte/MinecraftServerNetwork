package pl.socketbyte.opengui.serializable;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.ItemBuilder;

import java.util.*;

@SuppressWarnings("unchecked")
public class SerializableGUI extends GUI implements Serializable {

    private final Map<Integer, String> actions = new HashMap<>();

    public SerializableGUI(String title) {
        super(null, title);
    }

    public SerializableGUI(Map<String, Object> data) {
        super(null, (String) data.get("title"));

        List<ItemSection> itemSections = (List<ItemSection>) data.get("inventory");

        for (ItemSection section : itemSections)
            setItem(section.getSlot(), section.getItemBuilder());
    }

    public String getActionFor(int slot) {
        return actions.get(slot);
    }

    public boolean hasAction(int slot) {
        return actions.containsKey(slot);
    }

    public void addAction(int slot, String action) {
        this.actions.put(slot, action);
    }

    @Override
    public void register() {
        ConfigurationSerialization.registerClass(this.getClass());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", getTitle());
        data.put("actions", actions);

        List<ItemSection> itemSections = new ArrayList<>();
        int slot = 0;
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                continue;
            itemSections.add(new ItemSection(slot, new SerializableItemBuilder(new ItemBuilder(itemStack))));
            slot++;
        }
        List<Map<String, Object>> itemSectionsSerialized = new ArrayList<>();
        for (ItemSection section : itemSections) {
            itemSectionsSerialized.add(section.serialize());
        }

        data.put("inventory", itemSectionsSerialized);
        return data;
    }
}
