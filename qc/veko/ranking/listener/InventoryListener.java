package qc.veko.ranking.listener;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.inventory.FactionShopInventory;
import qc.veko.ranking.inventory.RankingInventory;
import qc.veko.ranking.utils.AddonUtils;
import qc.veko.ranking.utils.PointsUtils;
import qc.veko.ranking.utils.ShopUtils;

import java.util.List;

public class InventoryListener implements Listener {

    @EventHandler
    public void onFactionChestClose(InventoryCloseEvent e) {
        if (!e.getInventory().getName().equalsIgnoreCase("Coffre de faction"))
            return;
        Faction faction = FPlayers.getInstance().getByPlayer((Player)e.getPlayer()).getFaction();
        FactionRanking.getInstance().getFactionChest().put(faction.getTag(), e.getInventory().getContents());
        FactionRanking.getInstance().getFactionUsingChest().remove(faction);

    }

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
            if (!current.getItemMeta().hasDisplayName() || current.getItemMeta().hasEnchants())
                return;
            switch (current.getType()){
                case PAPER:
                    int next = Integer.parseInt(current.getItemMeta().getDisplayName().replace("§6Page : ", ""));
                    e.getWhoClicked().openInventory(factionShopInventory.getShopSelection(next, faction));
                    break;
                case ITEM_FRAME:
                    String name = e.getCurrentItem().getItemMeta().getDisplayName();
                    List<String> addonList = AddonUtils.getFactionBoughtAddonList(faction);
                    if (addonList.contains(name)) {
                        e.getWhoClicked().sendMessage("You already have this addon");
                        break;
                    }
                    int factionLevel = PointsUtils.getFactionLevel(faction);
                    int pageLevel = PointsUtils.getLevelByRankName(e.getInventory().getItem(13).getItemMeta().getDisplayName());
                    if (pageLevel > factionLevel) {
                        e.getWhoClicked().sendMessage("Vous ne pouvez pass acheter cela");
                        e.getWhoClicked().sendMessage("Votre faction n'est pas assez haut niveau");
                        break;
                    }
                    e.getWhoClicked().openInventory(factionShopInventory.getShopBuyMenu(faction, e.getCurrentItem().getItemMeta().getDisplayName()));
                    break;
                case PAINTING:
                    e.getWhoClicked().openInventory(factionShopInventory.getShopSelection(1, faction));
                    break;
                case STAINED_GLASS_PANE:
                    if  (e.getCurrentItem().getDurability() == 5) {
                        String addon = e.getInventory().getItem(13).getItemMeta().getDisplayName();
                        e.getWhoClicked().closeInventory();
                        ShopUtils.buyAddon(addon, ShopUtils.getPricePerAddon(addon), (Player) e.getWhoClicked());
                        /*List<String> addonList = AddonUtils.getFactionBoughtAddonList(faction);
                        if (!addonList.contains(addon)) {
                            addonList.add(addon);
                            addonList.remove("nothing");
                        } else
                            e.getWhoClicked().sendMessage("You allready have this addon");*/
                    } else if (e.getCurrentItem().getDurability() == 14)
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
