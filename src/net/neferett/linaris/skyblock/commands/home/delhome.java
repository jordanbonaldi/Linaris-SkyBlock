package net.neferett.linaris.skyblock.commands.home;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.Main;
import net.neferett.linaris.skyblock.handlers.DataReader;

public class delhome extends CommandHandler {

	public delhome() {
		super("delhome", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (arg2.size() != 2) {
			arg0.sendMessage("§cUtilisation: /delhome <name>");
			return;
		} else {
			final DataReader rd = Main.getInstanceMain().getDatas().get(arg0.getName().toLowerCase());
			final List<String> homes = rd.getHomes();

			if (homes == null || !homes.contains(arg2.get(1))) {
				arg0.sendMessage("§cLe home §e" + arg2.get(1) + "§c n'existe pas !");
				return;
			} else {
				rd.removeHome(arg2.get(1));
				arg0.sendMessage("§cHome §e" + arg2.get(1) + "§c supprimé !");
			}
		}
	}

	@Override
	public void onError(final Players arg0) {}

}
