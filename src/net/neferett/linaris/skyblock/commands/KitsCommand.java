package net.neferett.linaris.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.skyblock.handlers.kits.GuiKits;
import net.neferett.linaris.skyblock.utils.GuiManager;

public class KitsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!(arg0 instanceof Player))
			return false;
		if (arg1.getLabel().equalsIgnoreCase("kits") || arg1.getLabel().equalsIgnoreCase("kit")) {
			final Player p = (Player) arg0;
			GuiManager.openGui(new GuiKits(p));
		}
		return false;
	}

}
