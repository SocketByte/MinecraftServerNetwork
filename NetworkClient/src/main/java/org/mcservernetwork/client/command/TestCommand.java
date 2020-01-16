package org.mcservernetwork.client.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcservernetwork.commons.io.DeepSerializationFormat;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;

        DeepSerializationFormat.WrappedObject<Player> wrappedPlayer =
                DeepSerializationFormat.wrap(Player.class, player);

        System.out.println(wrappedPlayer.toString());
        return true;
    }
}
