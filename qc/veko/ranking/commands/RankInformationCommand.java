package qc.veko.ranking.commands;

import org.bukkit.entity.Player;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.commands.engine.Command;
import qc.veko.ranking.commands.engine.CommandArgs;
import qc.veko.ranking.manager.ConfigManager;

import java.util.Map;

public class RankInformationCommand {

    @Command(name = "rank", aliases = {"rankinfo"}, inGameOnly = true)
    public void commandRanking(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        ConfigManager config = FactionRanking.getInstance().getConfigManager();
        player.sendMessage(getNamePerLevel(config.getLevelOne()) + " §e: §6" + getPointsPerLevel(config.getLevelOne()));
        player.sendMessage(getNamePerLevel(config.getLevelTwo()) + " §e: §6" + getPointsPerLevel(config.getLevelTwo()));
        player.sendMessage(getNamePerLevel(config.getLevelThree()) + " §e: §6" + getPointsPerLevel(config.getLevelThree()));
        player.sendMessage(getNamePerLevel(config.getLevelFour()) + " §e: §6" + getPointsPerLevel(config.getLevelFour()));
        player.sendMessage(getNamePerLevel(config.getLevelFive()) + " §e: §6" + getPointsPerLevel(config.getLevelFive()));
    }

    private String getNamePerLevel(Map<String, Integer> level){
        return level.keySet().stream().findFirst().get();
    }
    private int getPointsPerLevel(Map<String, Integer> level){
        return level.values().stream().findFirst().get();
    }

}
