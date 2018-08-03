package net.neferett.linaris.skyblock.commands.home;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.api.ranks.RankAPI;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.Main;
import net.neferett.linaris.skyblock.handlers.DataReader;

public class sethome extends CommandHandler {

	public sethome() {
		super("sethome", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (arg2.size() != 2) {
			arg0.sendMessage("§cUtilisation: /sethome <name>");
			return;
		} else {
			if (arg0.getWorld().getName().equals("world")) {
				arg0.sendMessage("§cIl est interdit de faire un home au spawn !");
				return;
			}
			final DataReader rd = Main.getInstanceMain().getDatas().get(arg0.getName().toLowerCase());
			final int i = this.getHomesPerRank(arg0);
			final List<String> homes = rd.getHomes();
			if (homes != null && homes.size() == i) {
				arg0.sendMessage("§cVous avez atteint votre nombre maximale de home qui est de §e" + i + "homes §c!");
				return;
			} else {
				arg0.sendMessage("§7Home §e" + arg2.get(1) + "§7 ajouté !");
				rd.addHome(arg0.getLocation(), arg2.get(1));
			}
		}
	}

	int getHomesPerRank(final Players p) {
		final RankAPI r = p.getPlayerData().getRank();
		return r.getVipLevel() == 0 ? 1 : r.getVipLevel() >= 1 && r.getVipLevel() <= 2 ? 5
				: r.getVipLevel() == 3 ? 10 : r.getVipLevel() >= 4 ? 15 : 1;
	}

	@Override
	public void onError(final Players arg0) {}

}
