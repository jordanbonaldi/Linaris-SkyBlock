package net.neferett.linaris.skyblock.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;

public class Fly extends CommandHandler {

	public Fly() {
		super("fly", p -> p.getRank().getVipLevel() >= 4);
		this.setErrorMsg("§cIl faut être gradé pour faire cette commande!");
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (arg0.getPlayerLocal().contains("fly") && arg0.getPlayerLocal().getBoolean("fly")) {
			arg0.setFlying(false);
			arg0.setAllowFlight(false);
			arg0.getPlayerLocal().setBoolean("fly", false);
		} else {
			if (arg0.getLocation().getWorld().getName().equals("world")) {
				arg0.sendMessage("§cTu n'as pas le droit de fly au spawn !");
				return;
			}
			arg0.setAllowFlight(true);
			arg0.setFlying(true);
			arg0.getPlayerLocal().setBoolean("fly", true);
		}
	}

	@Override
	public void onError(final Players arg0) {
		arg0.DisplayErrorMessage();
	}

}
