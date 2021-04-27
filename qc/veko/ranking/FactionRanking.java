package qc.veko.ranking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;
import com.massivecraft.factions.P;

import qc.veko.ranking.commands.*;
import qc.veko.ranking.commands.engine.CommandFramework;
import qc.veko.ranking.listener.BasicsListener;
import qc.veko.ranking.listener.InventoryListener;
import qc.veko.ranking.manager.ConfigManager;
import qc.veko.ranking.manager.FactionFileManager;
import qc.veko.ranking.rank.RankingSystem;

public class FactionRanking extends JavaPlugin{

	@Getter private Map<String,Map<Integer, String>> topTen = Maps.newHashMap();
	@Getter private Map<String, Integer> rankByFaction = Maps.newHashMap();
	@Getter private Map<String, Map<String, Integer>> pointsByFaction = Maps.newHashMap();
	@Getter  private ArrayList<String> listOfFactions = Lists.newArrayList();

	@Getter public static FactionRanking instance;

	@Getter private ConfigManager configManager = new ConfigManager();
	@Getter FactionFileManager factionFileManager = new FactionFileManager();

	@Getter private Economy economy = null;

	@Getter private File factionList = new File("plugins/FactionRanking", "factionList.yml");
	@Getter private FileConfiguration factionListFile = YamlConfiguration.loadConfiguration(factionList);

	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;

		this.saveDefaultConfig();
		getConfigManager().loadConfig();

		final CommandFramework commandFramework = new CommandFramework(this);
		commandFramework.registerCommands(new RankingCommand());
		commandFramework.registerCommands(new RankInformationCommand());

		P.p.cmdBase.addSubCommand(new RankingCommand());
		P.p.cmdBase.addSubCommand(new InformationCommand());
		P.p.cmdBase.addSubCommand(new FactionShopCommand());
		P.p.cmdBase.addSubCommand(new FactionInvestCommand());

		Bukkit.getPluginManager().registerEvents(new BasicsListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

	    Bukkit.getScheduler().runTaskTimer(this, () -> {
			new RankingSystem(getFactionFileManager());
			save();
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§6§m---------------------------------------------------");
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§4Halkia§c>> §6Le classement faction vient d'être mis à jour !");
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§6§m---------------------------------------------------");
			Bukkit.broadcastMessage(" ");
		}, 0, 20 * (60 * 30));
		
	}
	
	public void onDisable() {
		save();
	}
	
	
	private void save() {
		FactionFileManager.getFactionsPoints().forEach((name, map) -> {
			factionFileManager.save(name);
		});
		getFactionListFile().set("factionList", listOfFactions);
		try {
			getFactionListFile().save(factionList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		this.economy = (Economy)rsp.getProvider();
		return (this.economy != null);
	}
	
}
