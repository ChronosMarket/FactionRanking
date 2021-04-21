package qc.veko.ranking.commands;

import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.util.TL;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.PointsUtils;
import qc.veko.ranking.utils.Utils;

public class FactionInvestCommand extends FCommand {

    public FactionInvestCommand() {
        this.aliases.add("invest");
        this.aliases.add("investissement");
        this.requiredArgs.add("money");
    }

    @Override
    public void perform(CommandContext commandContext) {
        String money = commandContext.args.get(0);
        if (!FactionRanking.getInstance().setupEconomy())
            return;
        if (!Utils.isInteger(money)) {
            commandContext.player.sendMessage("§6" + money + " §en'est pas un chiffre");
            return;
        }
        if (FactionRanking.getInstance().getEconomy().getBalance(commandContext.player) < Utils.convertStringToInteger(money)) {
            commandContext.player.sendMessage("§6Vous n'avez pas assez d'argent pour investir §e" + money + "$");
            return;
        }
        PointsUtils.addFactionMoney(commandContext.faction, Utils.convertStringToInteger(money));
        commandContext.player.sendMessage("§6Vous avez investie §e" + money + "$ §6 dans votre faction");
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
