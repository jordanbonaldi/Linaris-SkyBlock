package net.neferett.linaris.skyblock.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.handlers.ConfigReader;

public class PvPCommand extends CommandHandler {

	public PvPCommand() {
		super("pvp", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		arg0.sendMessage("§cTéléportation dans 5 secondes, ne bougez pas !");
//		arg0.createTPWithDelay(5, () -> ConfigReader.getInstance().getPvPSpawn());
	}

	@Override
	public void onError(final Players arg0) {

	}

}
