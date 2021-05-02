package qc.veko.ranking.inventory;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.utils.AddonUtils;
import qc.veko.ranking.utils.ItemBuilder;
import qc.veko.ranking.utils.PointsUtils;
import qc.veko.ranking.utils.ShopUtils;

import java.util.*;

public class FactionShopInventory {

    private List<Integer> glassInGui() {
        return Lists.newArrayList(0, 1, 2, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 42, 43, 44);
    }

    public Inventory getPrincipalMenu(Faction faction) {
        if (AddonUtils.getFactionBoughtAddonList(faction).contains("nothing"))
            return getBoughtNothingMenu(faction);
        else
            return getBoughtNormalMenu(faction);
    }

    @Getter
    private HashMap<Integer, Integer> placementOfItemPerNumber = new HashMap<Integer, Integer>(){{
        put(1, 13);
        put(2, 20);
        put(3, 21);
        put(4, 22);
        put(5, 23);
        put(6, 24);
        put(7, 30);
        put(8, 31);
        put(9, 32);
    }};

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
        ItemBuilder addonItem = new ItemBuilder(Material.ITEM_FRAME).addUnsafeEnchantment(Enchantment.LURE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).setLore("Déjà Acheter");
        ItemBuilder painting = new ItemBuilder(Material.PAINTING).setName("§6Cliquer ici pour ouvrir le shop de faction");
        inv.setItem(4, head.toItemStack());
        inv.setItem(40, painting.toItemStack());
        putYellowGlass(inv);
        int calc = 1;
        for (String addon : AddonUtils.getFactionBoughtAddonList(faction)) {
            addonItem.setName(addon);
            inv.setItem(getPlacementOfItemPerNumber().get(calc), addonItem.toItemStack());
            calc++;
        }
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

        List<String> attout = new LinkedList<String>();
        attout = ShopUtils.getAddonPerLevel(page);

        for (int i = 21 ; i <24; ++i) {
            try {
                String name = attout.get(i-21);
                addon.setName(name).setLore("§6" + ShopUtils.getPricePerAddon(name) + "$");
                inv.setItem(i, addon.toItemStack());
            } catch (Exception e) {
                soon.setName("Soon ...");
                inv.setItem(i, soon.toItemStack());
            }
        }

        return inv;
    }

    public Inventory getShopBuyMenu(Faction faction, String addon) {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop de faction : " + faction.getTag());
        putYellowGlass(inv);
        String owner = faction.getFPlayerAdmin().getName();
        ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setName("§6" + faction.getTag()).setLore("",
                "§e---------------------------", " ", "§6Chef de faction : §e" + owner, " ",
                "§e---------------------------").setSkullOwner(owner);
        ItemBuilder buy = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)5).setName("§6Acheter");
        ItemBuilder decline = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14).setName("§6Retour");
        ItemBuilder itemFrame = new ItemBuilder(Material.ITEM_FRAME).setName(addon).addUnsafeEnchantment(Enchantment.LURE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).setLore("§6" + ShopUtils.getPricePerAddon(addon) + "$");
        ItemBuilder painting = new ItemBuilder(Material.PAINTING).setName("§6Cliquer ici pour ouvrir le shop de faction");

        inv.setItem(4, head.toItemStack());
        inv.setItem(23, buy.toItemStack());
        inv.setItem(21, decline.toItemStack());
        inv.setItem(13, itemFrame.toItemStack());
        inv.setItem(40, painting.toItemStack());

        return inv;

    }

}
