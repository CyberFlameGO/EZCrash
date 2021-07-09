package me.notom3ga.ezcrash;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class EZCrash extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("crash")).setExecutor(new CrashCommand(this));
    }
}
