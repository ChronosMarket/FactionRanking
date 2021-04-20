package qc.veko.ranking.commands;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.CommandVisibility;
import com.massivecraft.factions.zcore.util.TL;
import qc.veko.ranking.inventory.FactionShopInventory;

public class FactionShopCommand extends FCommand {

    public FactionShopCommand() {
        this.aliases.add("shop");
        this.visibility = CommandVisibility.VISIBLE;
    }

    @Override
    public void perform(CommandContext commandContext) {
        FactionShopInventory factionShopInventory = new FactionShopInventory();
        Faction faction = FPlayers.getInstance().getByPlayer(commandContext.player).getFaction();
        commandContext.player.openInventory(factionShopInventory.getPrincipalMenu(faction));
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
