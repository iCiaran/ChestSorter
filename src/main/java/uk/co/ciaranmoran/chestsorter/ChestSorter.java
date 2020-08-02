package uk.co.ciaranmoran.chestsorter;

import org.bukkit.plugin.java.JavaPlugin;

public class ChestSorter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
    }
}
