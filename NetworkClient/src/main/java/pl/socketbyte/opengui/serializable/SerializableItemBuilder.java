package pl.socketbyte.opengui.serializable;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.socketbyte.opengui.ColorUtil;
import pl.socketbyte.opengui.ItemBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class SerializableItemBuilder extends ItemBuilder implements ConfigurationSerializable {

    public SerializableItemBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder.getItem());
    }

    public SerializableItemBuilder(Map<String, Object> data) {
        Material material = Material.matchMaterial(data.get("material").toString());
        int amount = (int) data.get("amount");
        short durability = (short) data.get("durability");
        String name = ColorUtil.fixColor(data.get("name").toString());
        List<String> lore = ColorUtil.fixColor((List<String>) data.get("lore"));

        List<String> enchantsList = (List<String>) data.get("enchants");
        Map<Enchantment, Integer> enchants = new HashMap<>();

        for(String enchant : enchantsList) {
            String[] part = enchant.split(":");
            if (part.length < 1)
                continue;

            Enchantment ench = Enchantment.getByName(part[0]);
            if (ench == null)
                continue;

            int level;
            try {
                level = Integer.parseInt(part[1]);
            }
            catch (NumberFormatException ex) {
                continue;
            }
            enchants.put(ench, level);
        }

        setItem(material, amount, durability);
        setEnchantments(enchants);
        setLore(lore);
        setName(name);
    }

    @Override
    public Map<String, Object> serialize() {
        ItemStack item = getItem();
        ItemMeta meta = getMeta();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("material", item.getType().toString());
        data.put("amount", item.getAmount());
        data.put("durability", item.getDurability());
        data.put("name", meta.getDisplayName() == null ? null
                : ColorUtil.fixColor(meta.getDisplayName()));
        data.put("lore", meta.getLore());
        data.put("enchants", meta.getEnchants()
                .keySet()
                .stream()
                .map(enchant -> enchant.getName() + ":" + meta.getEnchantLevel(enchant))
                .collect(Collectors.toList()));
        return data;
    }
}
