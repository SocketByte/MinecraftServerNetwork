package pl.socketbyte.opengui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.socketbyte.opengui.event.ElementResponse;

public abstract class GUIExtenderItem implements ElementResponse {

    private final ItemBuilder itemBuilder;
    private boolean pullable;

    public GUIExtenderItem(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public GUIExtenderItem() {
        this.itemBuilder = new ItemBuilder(Material.AIR);
    }

    // You can override this based on a player for example.
    public ItemBuilder getItemBuilder(Player player) {
        return itemBuilder;
    }

    public boolean isPullable() {
        return pullable;
    }

    public void setPullable(boolean pullable) {
        this.pullable = pullable;
    }

}