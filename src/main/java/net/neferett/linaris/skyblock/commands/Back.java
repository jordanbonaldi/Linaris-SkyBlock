package net.neferett.linaris.skyblock.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;
import net.neferett.linaris.skyblock.handlers.ConfigReader;

public class Back extends CommandHandler {

	public Back() {
		super("back", p -> p.getRank().getVipLevel() >= 1);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		final M_Player p = PlayerManagers.get().getPlayer(arg0.getPlayer());

		if (p.getPlayer().getLocation().getY() > ConfigReader.getInstance().getMaxHeight())
			p.tp(p.getBackPos());
		else
			p.getPlayer().sendMessage("§cVous devez être au spawn !");

	}

	@Override
	public void onError(final Players arg0) {
		arg0.sendMessage("§cVous ne pouvez pas faire cela");
	}

}
