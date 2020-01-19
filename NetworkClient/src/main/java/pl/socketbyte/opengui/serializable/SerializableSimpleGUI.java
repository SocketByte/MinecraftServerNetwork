package pl.socketbyte.opengui.serializable;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import pl.socketbyte.opengui.SimpleGUI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SerializableSimpleGUI extends SimpleGUI implements Serializable {
    public SerializableSimpleGUI(SerializableGUI gui) {
        super(gui);
    }

    public SerializableSimpleGUI(Map<String, Object> data) {
        super((SerializableGUI) data.get("gui"));

        getGuiSettings().setCanDrag((Boolean) data.get("canDrag"));
        getGuiSettings().setCanEnterItems((Boolean) data.get("canEnterItems"));

        List<String> enterableMaterials = (List<String>) data.get("enterableItems");
        for (String mat : enterableMaterials)
            getGuiSettings().addEnterableItem(Material.matchMaterial(mat));
    }

    public String getActionFor(int slot) {
        return ((SerializableGUI) getGui()).getActionFor(slot);
    }

    public boolean hasAction(int slot) {
        return ((SerializableGUI) getGui()).hasAction(slot);
    }

    @Override
    public void register() {
        ConfigurationSerialization.registerClass(this.getClass());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("canDrag", getGuiSettings().isCanDrag());
        data.put("canEnterItems", getGuiSettings().isCanEnterItems());
        List<String> enterableItems = new ArrayList<>();
        for (ItemStack itemStack : getGuiSettings().getEnterableItems())
            enterableItems.add(itemStack.getType().name());
        data.put("enterableItems", enterableItems);
        data.put("gui", ((SerializableGUI) getGui()).serialize());
        return data;
    }
}
