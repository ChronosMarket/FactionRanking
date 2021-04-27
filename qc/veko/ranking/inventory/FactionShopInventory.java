package qc.veko.ranking.inventory;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.utils.AddonUtils;
import qc.veko.ranking.utils.ItemBuilder;
import qc.veko.ranking.utils.PointsUtils;

import java.util.*;

public class FactionShopInventory {

    private List<Integer> glassInGui() {
        return Lists.newArrayList(0, 1, 2, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 42, 43, 44);
    }

    public Inventory getPrincipalMenu(Faction faction) {
        if (AddonUtils.getFactionBoughtAddonList(faction).contains("nothing")) {
            return getBoughtNothingMenu(faction);
        } else {
            return getBoughtNormalMenu(faction);
        }
    }

    private Inventory getBoughtNothingMenu(Faction faction) {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop de faction : " + faction.getTag());
        putYellowGlass(inv);

        ItemBuilder painting = new ItemBuilder(Material.PAINTING).setName("§6Vous n'avez rien acheter comme attout a faction").setLore("§eCliquer ici pour ouvrir le shop de faction");

        String owner = faction.getFPlayerAdmin().getName();
        ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setName("§6" + faction.getTag()).setLore("",
                "§e---------------------------", " ", "§6Chef de faction : §e" + owner, " ",
                "§e---------------------------").setSkullOwner(owner);

        inv.setItem(22, painting.toItemStack());
        inv.setItem(4, head.toItemStack());

        return inv;
    }

    private Inventory getBoughtNormalMenu(Faction faction) {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop de faction : " + faction.getTag());
        String owner = faction.getFPlayerAdmin().getName();
        ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setName("§6" + faction.getTag()).setLore("",
                "§e---------------------------", " ", "§6Chef de faction : §e" + owner, " ",
                "§e---------------------------").setSkullOwner(owner);
        inv.setItem(4, head.toItemStack());
        putYellowGlass(inv);
        return inv;
    }

    private void putYellowGlass(Inventory inv) {
        ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        for (int i = 0; i < 45; ++i) {
            if (glassInGui().contains(i))
                inv.setItem(i, yellow);
        }
    }

    public Inventory getShopSelection(int page, Faction faction) {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop de faction : " + faction.getTag());
        putYellowGlass(inv);

        String owner = faction.getFPlayerAdmin().getName();
        ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setName("§6" + faction.getTag()).setLore("",
                "§e---------------------------", " ", "§6Chef de faction : §e" + owner, " ",
                "§e---------------------------").setSkullOwner(owner);

        ItemBuilder next = new ItemBuilder(Material.PAPER).setName("§6Page : " + (page+1));
        ItemBuilder before = new ItemBuilder(Material.PAPER).setName("§6Page : " + (page-1));
        ItemBuilder addon = new ItemBuilder(Material.ITEM_FRAME);
        ItemBuilder soon = new ItemBuilder(Material.SIGN);
        ItemBuilder rank = new ItemBuilder(Material.NETHER_STAR).setName(PointsUtils.getRankNamePerLevel(page));
        inv.setItem(13, rank.toItemStack());
        if (page != 1)
            inv.setItem(28, before.toItemStack());
        if (page != 5)
            inv.setItem(34, next.toItemStack());
        inv.setItem(4, head.toItemStack());
        if (getAddonPerLevel(page).length == 1) {
            String[] attout = getAddonPerLevel(page);
            addon.setName(attout[0]);
            inv.setItem(22, addon.toItemStack());
            return inv;
        }
        for (int i = 21 ; i <24; ++i) {
            String[] attout = getAddonPerLevel(page);
            try {
                addon.setName(attout[i-21]);
                inv.setItem(i, addon.toItemStack());
            } catch (Exception e) {
                soon.setName("Soon ...");
                inv.setItem(i, soon.toItemStack());
            }
        }

        return inv;
    }

    public Inventory getShopBuyMenu(String addon, Faction faction) {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop de faction : " + faction.getTag());
        putYellowGlass(inv);

        return inv;

    }

    private String[] getAddonPerLevel(int level) {
        switch (level){
            case 1:
                return new String[] {"fchest9"};
            case 2:
                return new String[] {"drop", "powerboost"};
            case 3:
                return new String[] {"speed", "fchest18"};
            case 4:
                return new String[] {"fly", ""};
            case 5:
                return new String[] {"force","fchest27"};
            default:
                return new String[] {"nothing"};
        }
    }

}
