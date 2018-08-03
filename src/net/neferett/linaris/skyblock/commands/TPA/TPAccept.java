package net.neferett.linaris.skyblock.commands.TPA;

import java.util.List;

import org.bukkit.Bukkit;

import net.neferett.linaris.PlayersHandler.PlayerManager;
import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.api.PlayerLocalManager;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.utils.TimeUtils;

public class TPAccept extends CommandHandler {

	public TPAccept() {
		super("tpaccept", p -> PlayerLocalManager.get().getPlayerLocal(p.playername).contains("tpaask"), "tpyes");
		this.setErrorMsg("�cVous n'avez aucune demande de t�l�portation en cours");
	}

	@Override
	public void cmd(final Players p, final String cmd, final List<String> args) {
		if (p.getPlayerLocal().contains("tpaask-cl")
				&& !TimeUtils.CreateTestCoolDown(30).test(p.getPlayerLocal().getLong("tpaask-cl"))) {
			final Players pa = PlayerManager.get().getPlayer(Bukkit.getPlayer(p.getPlayerLocal().get("tpaask")));
			p.sendMessage("�7La demande de t�l�portation a expir�e !");
			pa.getPlayerLocal().remove("tpa");
			pa.getPlayerLocal().remove("tpa-cooldown");
			p.getPlayerLocal().remove("tpaask");
			p.getPlayerLocal().remove("tpaask-cl");
			return;
		} else if (Bukkit.getPlayer(p.getPlayerLocal().get("tpaask")) == null) {
			p.sendMessage("�7Demande annul�e");
			p.sendMessage("�cLe joueur �e" + p.getPlayerLocal().get("tpaask") + "�c s'est d�connect� !");
			return;
		} else {
			final Players pa = PlayerManager.get().getPlayer(Bukkit.getPlayer(p.getPlayerLocal().get("tpaask")));
			p.sendMessage("�7Requ�te accept�e !");
			pa.sendMessage("�e" + p.getName() + "�7 a accept� votre req�ete");
			pa.sendMessage("�cNe bougez pas pendant environ 5 secondes !");
			pa.createTPWithDelay(5, () -> p.getLocation());
			pa.getPlayerLocal().remove("tpa");
			pa.getPlayerLocal().remove("tpa-cooldown");
			p.getPlayerLocal().remove("tpaask");
			p.getPlayerLocal().remove("tpaask-cl");
		}
	}

	@Override
	public void onError(final Players arg0) {
		arg0.DisplayErrorMessage();
	}

}
