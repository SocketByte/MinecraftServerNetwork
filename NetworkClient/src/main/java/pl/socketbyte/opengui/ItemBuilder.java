package pl.socketbyte.opengui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material, int amount) {
        setItem(material, amount);
    }

    public ItemBuilder(Material material) {
        setItem(material, 1);
    }

    public ItemBuilder() {
        setItem(Material.DIRT, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        setItem(itemStack);
    }

    public ItemBuilder(Material material, int amount, int data) {
        setItem(material, amount, (short)data);
    }

    public ItemBuilder(Material material, int amount, short data) {
        setItem(material, amount, data);
    }

    public ItemBuilder setItem(ItemStack itemStack) {
        item = itemStack;
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder setItem(Material material, int amount, short data) {
        item = new ItemStack(material, amount, data);
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder setItem(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(ColorUtil.fixColor(name));
        update();
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(ColorUtil.fixColor(lore));
        update();
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(ColorUtil.fixColor(lore)));
        update();
        return this;
    }

    public ItemBuilder setEnchantments(List<ItemEnchantment> enchantments) {
        for (ItemEnchantment enchantment : enchantments)
            meta.addEnchant(enchantment.getEnchantment(), enchantment.getLevel(), enchantment.isUnsafe());
        update();
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet())
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        update();
        return this;
    }

    public ItemBuilder update() {
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemMeta getMeta() {
        return meta;
    }

}
