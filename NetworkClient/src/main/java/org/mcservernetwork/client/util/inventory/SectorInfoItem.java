package org.mcservernetwork.client.util.inventory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.mcservernetwork.client.ClientStatusHandler;
import org.mcservernetwork.client.util.PlayerUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.client.util.StringUtils;
import org.mcservernetwork.client.util.TimeUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.PacketStatus;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import pl.socketbyte.opengui.GUIExtenderItem;
import pl.socketbyte.opengui.ItemBuilder;

public class SectorInfoItem extends GUIExtenderItem {

    private String sectorName;

    public SectorInfoItem(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public ItemBuilder getItemBuilder(Player player) {
        PacketStatus status = ClientStatusHandler.get(this.sectorName);
        long duration = ClientStatusHandler.getDurationSinceLastPacket(this.sectorName);
        String name = StringUtils.capitalizeFirstLetter(this.sectorName);
        if (status == null) {
            return new ItemBuilder(Material.RED_CONCRETE)
                    .setName("&4&l" + name)
                    .setLore("&cThis sector is unavailable.",
                            "&cLast seen: &7" + TimeUtils.parse(duration));
        } else return new ItemBuilder(Material.GREEN_CONCRETE)
                .setName("&2&l" + name)
                .setLore("&aPlayers online: &7" + status.players,
                        "&aTPS: &7" + status.tps,
                        "", "&aClick to teleport!");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            PacketTransfer transfer = new PacketTransfer();
            transfer.uniqueId = event.getWhoClicked().getUniqueId().toString();
            transfer.targetSectorName = this.sectorName;
            transfer.info = PlayerUtils.wrap((Player) event.getWhoClicked());
            Location center = SectorLocationUtils.getCenter(
                    event.getWhoClicked().getWorld(),
                    (int) transfer.info.y,
                    NetworkAPI.Sectors.getSector(this.sectorName));
            transfer.info.x = center.getX();
            transfer.info.z = center.getZ();

            NetworkAPI.Net.publish(Channel.TRANSFER_REQUEST, transfer);
        }
    }
}
