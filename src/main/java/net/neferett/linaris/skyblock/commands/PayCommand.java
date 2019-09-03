package net.neferett.linaris.skyblock.commands;

import java.util.List;

import org.bukkit.Bukkit;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;

public class PayCommand extends CommandHandler {

	public PayCommand() {
		super("pay", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (arg2.size() != 3) {
			arg0.sendMessage("§c/pay <joueur> <montant>");
			return;
		}
		if (Bukkit.getPlayer(arg2.get(1)) == null
				|| Bukkit.getPlayer(arg2.get(1)).getName().equalsIgnoreCase(arg0.getName())) {
			arg0.sendMessage("§cLe joueur " + arg2.get(1) + " n'existe pas !");
			return;
		}
		final M_Player p = PlayerManagers.get().getPlayer(arg0.getPlayer());

		final int montant = Integer.parseInt(arg2.get(2));
		if (montant <= 0) {
			p.sendMessage("§cVous ne pouvez pas faire ceci !");
			return;
		}
		if (p.getMoney() - montant >= 0) {
			p.delMoney(montant, false);
			arg0.sendMessage("§7Paiement de §e" + montant + "$ §7envoyé a §e" + arg2.get(1));
		} else {
			arg0.sendMessage("§cIl vous manque §e" + (montant - p.getMoney()) + "$§c pour faire ce paiement !");
			return;
		}

		Bukkit.getPlayer(arg2.get(1)).getPlayer().sendMessage("§7Vous venez de recevoir §e"
				+ Integer.parseInt(arg2.get(2)) + "$§7 de la part de §e" + arg0.getName());
		PlayerManagers.get().getPlayer(Bukkit.getPlayer(arg2.get(1))).addMoney(Integer.parseInt(arg2.get(2)), false);
	}

	@Override
	public void onError(final Players arg0) {}

}
