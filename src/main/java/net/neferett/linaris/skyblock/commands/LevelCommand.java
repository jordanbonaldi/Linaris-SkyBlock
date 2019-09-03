package net.neferett.linaris.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;

public class LevelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!(arg0 instanceof Player))
			return false;
		if (arg1.getLabel().equalsIgnoreCase("level")) {
			final M_Player p = PlayerManagers.get().getPlayer((Player) arg0);
			if (arg3.length != 1) {
				p.sendMessage("§7Vous êtes au level§f: §e" + p.getLevel());
				p.sendMessage("");
				p.sendMessage("§7Kills§f: §e" + p.getKills());
				p.sendMessage("§7Morts§f: §e" + p.getDeaths());
				p.sendMessage("");
				p.sendMessage("§7Ratio§f: §a" + p.getRatio());
				p.sendMessage("");
				p.sendMessage("§7Il vous manque §e" + p.getScore() + " Kills§7 pour passer au level suivant !");
			}
		}
		return false;
	}

}
