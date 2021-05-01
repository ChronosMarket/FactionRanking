package qc.veko.ranking.manager;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.FactionRanking;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FactionChestManager {

    public void action_save(String faction, ItemStack[] content) throws IOException {
        File file = new File("plugins/FactionRanking/chest/" + faction + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.createSection(faction);
        for (int i = 0; i < content.length; i++) {
            if (content[i] != null && content[i].getType() != Material.AIR) {
                config.set(faction + "." + i, content[i]);
            } else {
                config.set(faction + "." + i, null);
            }
        }
        config.save(file);
    }

    public void action_load(String faction) {
        File file = new File("plugins/FactionRanking/chest/" + faction + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            return;
        }
        ItemStack[] contents = new ItemStack[getNumberOfRow(faction)];
        for (int i = 0; i < contents.length; i++) {
            ItemStack x = config.getItemStack(faction + "." + i, contents[i]);
            if (x != null)
                contents[i] = x;
        }
        FactionRanking.getInstance().getFactionChest().put(faction, contents);
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
