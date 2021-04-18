package qc.veko.ranking.commands;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.CommandVisibility;
import com.massivecraft.factions.zcore.util.TL;

import org.bukkit.entity.Player;
import qc.veko.ranking.commands.engine.Command;
import qc.veko.ranking.commands.engine.CommandArgs;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.inventory.RankingInventory;
import qc.veko.ranking.rank.RankingSystem;

public class RankingCommand extends FCommand{

	public RankingCommand() {
		this.aliases.add("classement");
		this.visibility = CommandVisibility.VISIBLE;
	}
	
	@Override
	public TL getUsageTranslation() {
		return null;
	}

	@Override
	public void perform(CommandContext arg0) {
		/*for (int i = 1; i < FactionRanking.getInstance().getTopTen().size()+1; ++i) {
			Faction faction = Factions.getInstance().getByTag(FactionRanking.getInstance().getTopTen().get(i));
			arg0.player.getPlayer().sendMessage(i + " : " + faction.getTag() + " §6" + PointsUtils.getFactionTotalPoints(faction));
		}*/
		openInventory(arg0.player.getPlayer());
	}

	@Command(name = "classement", aliases = {"ranking", "topfaction"}, inGameOnly = true)
	public void commandRanking(CommandArgs commandArgs){
		Player player = commandArgs.getPlayer();
		if (commandArgs.getArgs().length == 1 && commandArgs.getArgs()[0].equalsIgnoreCase("reload"))
			if (player.hasPermission("ranking.reload") || player.isOp()) {
				player.sendMessage("reload lancer");
				FactionRanking.getInstance().reloadConfig();
				FactionRanking.getInstance().getConfigManager().loadConfig();
				new RankingSystem(FactionRanking.getInstance().getFactionFileManager());
				return;
			}
		/*for (int i = 1; i < FactionRanking.getInstance().getTopTen().size()+1; ++i) {
			Faction faction = Factions.getInstance().getByTag(FactionRanking.getInstance().getTopTen().get(i));
			player.sendMessage(i + " : " + faction.getTag() + " §6" + PointsUtils.getFactionTotalPoints(faction));
		}*/
		openInventory(player);
	}

	private void openInventory(Player player) {
		Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
		RankingInventory rankingInventory = new RankingInventory();
		player.openInventory(rankingInventory.getInventory(faction));
	}
}
