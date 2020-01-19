package org.mcservernetwork.client.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.util.inventory.NetworkOverviewPanel;
import org.mcservernetwork.commons.io.DeepSerializationFormat;

public class NetworkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        NetworkOverviewPanel.panel.openInventory((Player) sender);
        return true;
    }
}
