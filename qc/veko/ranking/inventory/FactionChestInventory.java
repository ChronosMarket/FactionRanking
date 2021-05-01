package qc.veko.ranking.inventory;

import com.massivecraft.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.manager.FactionFileManager;

import java.util.List;

public class FactionChestInventory {

    public Inventory getFactionChest(Faction faction) {
        Inventory inv = Bukkit.createInventory(null, getNumberOfRow(faction.getTag()), "Coffre de faction");
        if (FactionRanking.getInstance().getFactionChest().containsKey(faction.getTag()))
            inv.setContents(FactionRanking.getInstance().getFactionChest().get(faction.getTag()));
        else {
            FactionRanking.getInstance().getFactionChestManager().action_load(faction.getTag());
            inv.setContents(FactionRanking.getInstance().getFactionChest().get(faction.getTag()));
        }
        return inv;
    }

    private int getNumberOfRow(String faction) {
        List<String> addon = FactionFileManager.getBoughtFactionAddon().get(faction);
        if (addon.contains("fchest27"))
            return 27;
        if (addon.contains("fchest18"))
            return 18;
        return 9;
    }
}
