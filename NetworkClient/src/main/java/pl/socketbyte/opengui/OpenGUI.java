package pl.socketbyte.opengui;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public enum OpenGUI implements Listener {
    INSTANCE;

    private Plugin instance;

    private Map<Integer, GUIExtender> guiMap = new HashMap<>();

    public Map<Integer, GUIExtender> getGUIs() {
        return guiMap;
    }

    public GUIExtender getGUI(int id) {
        return guiMap.get(id);
    }

    public void register(JavaPlugin instance) {
        PluginManager pm = Bukkit.getPluginManager();
        Plugin plugin = null;
        for (Plugin bukkitPlugin : pm.getPlugins()) {
            if (bukkitPlugin.equals(instance)) {
                plugin = bukkitPlugin;
                break;
            }
        }
        this.instance = plugin;
    }

    public Plugin getInstance() {
        return instance;
    }

    public Map<Integer, GUIExtender> getGuiMap() {
        return guiMap;
    }

}
