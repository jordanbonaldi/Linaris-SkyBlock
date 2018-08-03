package net.neferett.linaris.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.skyblock.events.players.PlayerManagers;

public class EnderChest extends PlayerManagers implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!(arg0 instanceof Player))
			return false;
		if (this.getPlayer((Player) arg0).getRank().getVipLevel() < 3) {
			arg0.sendMessage("§cIl faut être §aHéros §cpour executer cette commande !");
			return true;
		}
		if (arg1.getLabel().equalsIgnoreCase("ec") || arg1.getLabel().equalsIgnoreCase("enderchest")) {
			final Player p = (Player) arg0;
			p.openInventory(p.getEnderChest());
		}
		return false;
	}

}
