package me.wuzzyxy.anviloverwriter;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class Rewrites {

    private Map<String, Map<Enchantment, Integer>> rewritesMap = new HashMap<>();

    public void LoadFromConfig(FileConfiguration config) {
        Map<String, Map<Enchantment, Integer>> rewritesMap = new HashMap<>();

        ConfigurationSection rewritesSection = config.getConfigurationSection("rewrites");
        if (rewritesSection == null) {
            return; // No rewrites found in the config
        }

        for (String itemKey : rewritesSection.getKeys(false)) {
            ConfigurationSection itemSection = rewritesSection.getConfigurationSection(itemKey);
            if (itemSection == null) {
                continue; // Skip invalid item section
            }

            Map<Enchantment, Integer> enchantmentsMap = new HashMap<>();
            for (String enchantmentKey : itemSection.getKeys(false)) {
                Enchantment enchantment = Enchantment.getByName(enchantmentKey);
                if (enchantment == null) {
                    System.out.println("Invalid enchantment: " + enchantmentKey);
                    continue; // Skip invalid enchantment
                }

                int level = itemSection.getInt(enchantmentKey);
                enchantmentsMap.put(enchantment, level);
            }

            rewritesMap.put(itemKey, enchantmentsMap);
        }

        this.rewritesMap = rewritesMap;
    }

    public Map<String, Map<Enchantment, Integer>> getRewritesMap() {
        return rewritesMap;
    }
}
