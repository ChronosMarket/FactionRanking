package qc.veko.ranking.commands;

import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.CommandVisibility;
import com.massivecraft.factions.zcore.util.TL;

public class FactionShopCommand extends FCommand {

    public FactionShopCommand() {
        this.aliases.add("shop");
        this.visibility = CommandVisibility.VISIBLE;
    }

    @Override
    public void perform(CommandContext commandContext) {

    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
