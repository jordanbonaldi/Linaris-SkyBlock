package net.neferett.linaris.skyblock;

import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.API;
import net.neferett.linaris.skyblock.commands.*;
import net.neferett.linaris.skyblock.commands.TPA.TPA;
import net.neferett.linaris.skyblock.commands.TPA.TPAccept;
import net.neferett.linaris.skyblock.commands.TPA.TPAdeny;
import net.neferett.linaris.skyblock.commands.home.delhome;
import net.neferett.linaris.skyblock.commands.home.home;
import net.neferett.linaris.skyblock.commands.home.sethome;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;
import net.neferett.linaris.skyblock.handlers.Classement;
import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.skyblock.handlers.DataReader;
import net.neferett.linaris.skyblock.handlers.ScoreBoard;
import net.neferett.linaris.skyblock.handlers.kits.KitsManager;
import net.neferett.linaris.skyblock.listeners.CancelledEvents;
import net.neferett.linaris.skyblock.listeners.ChatHandling;
import net.neferett.linaris.skyblock.listeners.ChestListener;
import net.neferett.linaris.skyblock.listeners.events.*;
import net.neferett.linaris.skyblock.shop.npc.CustomEntityType;
import net.neferett.linaris.skyblock.shop.npc.NPC;
import net.neferett.linaris.utils.TimeUtils;
import net.neferett.linaris.utils.tasksmanager.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public class Main extends API {

	static Main instanceMain;

	public static Main getInstanceMain() {
		return instanceMain;
	}

	private final HashMap<String, DataReader>	datas	= new HashMap<>();

	private HologramManager					hologramManager;

	PlayerManagers								pm;

	public Main() {
		super(ConfigReader.getInstance().getGameName(), "Default", 100);
		instanceMain = this;
		this.pm = new PlayerManagers();
	}

	@Override
	public void addRanks() {
		// TODO Auto-generated method stub

	}

	public HashMap<String, DataReader> getDatas() {
		return this.datas;
	}

	public HologramManager getHologramManager() {
		return this.hologramManager;
	}

	public PlayerManagers getPlayerManager() {
		return this.pm;
	}

	public void loadHolos() {

	}

	private void loadPredicateProcessors() {
		BukkitAPI.get().addProcessPredicate(e -> {
			final Player p = e.getPlayer();
			if (DamageEvent.time.containsKey(p) && TimeUtils.CreateTestCoolDown(15).test(DamageEvent.time.get(p))) {
				p.sendMessage("§cVous êtes en combat vous ne pouvez pas faire cela !");
				e.setCancelled(true);
				return false;
			}
			return true;
		});
	}

	@Override
	public void onClose() {
		this.closeServer();
		Arrays.asList(NPC.values()).forEach(n -> n.dispawn());
	}

	@Override
	public void onLoading() {
		new KitsManager();
		ConfigReader.getInstance().getKits();
	}

	@Override
	public void onOpen() {
		this.hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();

		this.openServer();
		this.handleWorld();

		this.setScoreBoard(ScoreBoard.class);

		if (ConfigReader.getInstance().getChectClickable())
			this.RegisterAllEvents(new ChestListener());
		this.RegisterAllEvents(new JoinAndLeave(), new CancelledEvents(), new ChatHandling(), new InteractListener(),
				new DeathEvents(), new MoveListener(), new AutoLapis(), new DamageEvent(), new AntiCrop(),
				new EntitySpawnListener());
		this.w.setDifficulty(Difficulty.EASY);
		this.w.getEntities().forEach(e -> {
			if (e instanceof Monster && e.getLocation().getWorld().getName().equals("world"))
				e.remove();
		});
		TaskManager.scheduleSyncRepeatingTask("Classement", () -> {
			if (Bukkit.getOnlinePlayers().size() >= 1)
				Classement.getInstance().SignClassement();
		}, 0, 6 * 20);
		this.loadPredicateProcessors();
		this.setAPIMode(true);
		this.setAnnounce();
		CustomEntityType.registerEntities();
		ConfigReader.getInstance().loadHolos();
		Arrays.asList(NPC.values()).forEach(NPC::spawn);
		this.addHealthNameTag();
	}

	@Override
	public void RegisterCommands() {
		new Feed();
		new TPA();
		new TPAccept();
		new TPAdeny();
//		new PvPCommand();
		new sethome();
		new delhome();
		new home();
		new SpawnCommand();
		new AddItem();
		new PayCommand();
		new Back();
		new Fly();
		new SpawnerCommand();
		this.getCommand("kit").setExecutor(new KitsCommand());
		this.getCommand("kits").setExecutor(new KitsCommand());
		this.getCommand("givekits").setExecutor(new GiveKits());
		this.getCommand("money").setExecutor(new MoneyManagement());
		this.getCommand("level").setExecutor(new LevelCommand());
		this.getCommand("ec").setExecutor(new EnderChest());
		this.getCommand("enderchest").setExecutor(new EnderChest());
		this.getCommand("craft").setExecutor(new CraftCommand());
		this.getCommand("classement").setExecutor(new net.neferett.linaris.skyblock.commands.Classement());
	}

}
