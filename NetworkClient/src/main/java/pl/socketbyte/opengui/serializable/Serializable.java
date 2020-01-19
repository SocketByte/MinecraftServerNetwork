package pl.socketbyte.opengui.serializable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface Serializable extends ConfigurationSerializable {

    void register();

}
