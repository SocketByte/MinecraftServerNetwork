package org.mcservernetwork.proxy.io;

import net.md_5.bungee.config.Configuration;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.NetworkLogger;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.proxy.Proxy;

import java.util.Map;

public class ConfigReader {

    public static void readSectors() {
        Configuration configuration = Proxy.getConfiguration();

        Configuration sectors = configuration.getSection("sectors");
        for (String name : sectors.getKeys()) {
            Configuration sectorConf = sectors.getSection(name);

            Sector sector = new Sector(name);
            sector.setMinX(sectorConf.getDouble("min.x"));
            sector.setMinZ(sectorConf.getDouble("min.z"));
            sector.setMaxX(sectorConf.getDouble("max.x"));
            sector.setMaxZ(sectorConf.getDouble("max.z"));

            NetworkAPI.Internal.addSector(sector);
        }
        Proxy.getNetworkLogger().log("Loaded " + NetworkAPI.Sectors.getSectors().size() + " sectors.",
                NetworkLogger.LogSeverity.INFO);
    }

}
