package qc.veko.ranking.commands;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.util.TL;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.inventory.FactionChestInventory;
import qc.veko.ranking.manager.FactionFileManager;

import java.util.List;

public class FactionChestCommand extends FCommand {

    public FactionChestCommand() {
        this.aliases.add("chest");
    }

    @Override
    public void perform(CommandContext commandContext) {
        Faction faction = commandContext.faction;
        List<String> addon = FactionFileManager.getBoughtFactionAddon().get(faction.getTag());
        if (!containChestAddon(addon)) {
            commandContext.player.sendMessage("You don't have the faction addon to use this command");
            commandContext.player.sendMessage("buy it using /f shop");
            return;
        }
        if (!FactionRanking.getInstance().getFactionUsingChest().contains(faction)) {
            FactionChestInventory factionChestInventory = new FactionChestInventory();
            commandContext.player.openInventory(factionChestInventory.getFactionChest(faction));
            FactionRanking.getInstance().getFactionUsingChest().add(faction);
        } else
            commandContext.player.sendMessage("Somebody in your faction is using the chest right now");

    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }

    private boolean containChestAddon (List<String> addon) {
        if (addon.contains("fchest27"))
            return true;
        if (addon.contains("fchest18"))
            return true;
        return addon.contains("fchest9");
    }
}
