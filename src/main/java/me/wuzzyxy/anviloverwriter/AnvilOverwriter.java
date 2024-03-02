package me.wuzzyxy.anviloverwriter;

import me.wuzzyxy.anviloverwriter.events.AnvilEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilOverwriter extends JavaPlugin {
    private final Rewrites rewrites = new Rewrites();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        rewrites.LoadFromConfig(getConfig());

        this.getLogger().info("AnvilOverwriter has been enabled!");

        //register events
        getServer().getPluginManager().registerEvents(new AnvilEvent(rewrites), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
