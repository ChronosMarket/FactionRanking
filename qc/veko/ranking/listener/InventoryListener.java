package qc.veko.ranking.listener;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.inventory.FactionShopInventory;
import qc.veko.ranking.inventory.RankingInventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClickInformationInv(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (!getCondition(current))
            return;
        if (e.getInventory().getName().contains("Information de faction : "))
            e.setCancelled(true);
    }

    @EventHandler
    public void onClickRankingInv(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (!getCondition(current))
            return;
        if (!e.getInventory().getName().contains("Classement Faction"))
            return;
        e.setCancelled(true);
        RankingInventory rankingInventory = new RankingInventory();
        if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Suivant")) {
            int page = Integer.parseInt(e.getInventory().getName().replace("Classement Faction Page : " , ""));
            Faction faction = FPlayers.getInstance().getByPlayer((Player)e.getWhoClicked()).getFaction();
            Inventory inv = rankingInventory.getInventory(faction, page+1);
            e.getWhoClicked().openInventory(inv);
        }
        if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Precedent")) {
            int page = Integer.parseInt(e.getInventory().getName().replace("Classement Faction Page : " , ""));
            Faction faction = FPlayers.getInstance().getByPlayer((Player)e.getWhoClicked()).getFaction();
            Inventory inv = rankingInventory.getInventory(faction, page-1);
            e.getWhoClicked().openInventory(inv);
        }

    }

    @EventHandler
    public void onClickShopInv(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (!getCondition(current))
            return;
        if (e.getInventory().getName().contains("Shop de faction")) {
            e.setCancelled(true);
            Faction faction = Factions.getInstance().getByTag(e.getInventory().getName().replace("Shop de faction : ", ""));
            FactionShopInventory factionShopInventory = new FactionShopInventory();
            if (!current.getItemMeta().hasDisplayName())
                return;
            switch (current.getType()){
                case PAPER:
                    int next = Integer.parseInt(current.getItemMeta().getDisplayName().replace("§6Page : ", ""));
                    e.getWhoClicked().openInventory(factionShopInventory.getShopSelection(next, faction));
                    break;
                case ITEM_FRAME:

                    break;
                case PAINTING:
                    e.getWhoClicked().openInventory(factionShopInventory.getShopSelection(1, faction));
                    break;
            }
        }
    }

    private boolean getCondition(ItemStack item) {
        if (item == null)
            return false;
        return item.getType() != Material.AIR;
    }

}
