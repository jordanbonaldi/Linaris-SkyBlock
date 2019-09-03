package net.neferett.linaris.skyblock.commands;

import java.util.HashMap;
import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;

public class Feed extends CommandHandler {

	public static HashMap<String, Boolean> waiting = new HashMap<>();

	public Feed() {
		super("feed", p -> p.getRank().getVipLevel() >= 2, "nourriture", "bouf");
		this.setErrorMsg("§cIl faut être gradé pour faire cette commande!");
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		arg0.setFoodLevel(20);
		arg0.sendMessage("§aVous avez été rassasié");
	}

	@Override
	public void onError(final Players arg0) {
		arg0.DisplayErrorMessage();
	}

}
