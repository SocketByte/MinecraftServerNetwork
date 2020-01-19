package pl.socketbyte.opengui;

import org.bukkit.enchantments.Enchantment;

public class ItemEnchantment {

    private Enchantment enchantment;
    private int level;
    private boolean unsafe;

    public ItemEnchantment(Enchantment enchantment, int level, boolean unsafe) {
        this.enchantment = enchantment;
        this.level = level;
        this.unsafe = unsafe;
    }

    public ItemEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
        this.unsafe = true;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public void setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUnsafe() {
        return unsafe;
    }

    public void setUnsafe(boolean unsafe) {
        this.unsafe = unsafe;
    }
}
