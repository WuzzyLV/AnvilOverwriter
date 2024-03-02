package me.wuzzyxy.anviloverwriter.events;

import me.wuzzyxy.anviloverwriter.Rewrites;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AnvilEvent implements Listener {

    private final Rewrites rewrites;

    public AnvilEvent(Rewrites rewrites) {
        this.rewrites = rewrites;
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.isCancelled()){
            return;
        }
        Inventory inv = e.getInventory();

        if(!(inv instanceof AnvilInventory)) {
            return;
        }

        InventoryView view = e.getView();
        int rawSlot = e.getRawSlot();

        if (rawSlot == view.convertSlot(rawSlot)) {
            if (rawSlot != 2) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            Map<String, Map<Enchantment, Integer>> rewriteMap = rewrites.getRewritesMap();

            for (Map.Entry<String, Map<Enchantment, Integer>> entry : rewriteMap.entrySet()) {
                String itemName = entry.getKey();
                Map<Enchantment, Integer> enchantmentsMap = entry.getValue();

                // Check if the result item matches the rewritten item
                if (item.getType().name().equals(itemName)) {
                    boolean matches = true;
                    for (Map.Entry<Enchantment, Integer> enchantmentEntry : enchantmentsMap.entrySet()) {
                        Enchantment enchantment = enchantmentEntry.getKey();
                        int level = enchantmentEntry.getValue();

                        if (!item.containsEnchantment(enchantment)) {
                            matches = false;
                            continue;
                        }
                        if (item.getEnchantmentLevel(enchantment) != level) {
                            item.addUnsafeEnchantment(enchantment, level);
                        }
                    }
                }
            }
        }
    }
}
