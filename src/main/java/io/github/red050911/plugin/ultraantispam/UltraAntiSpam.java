package io.github.red050911.plugin.ultraantispam;

import org.bukkit.plugin.java.JavaPlugin;

public final class UltraAntiSpam extends JavaPlugin {

    public SpamHolder holder;
    private UltraAntiSpamListener listener;

    @Override
    public void onEnable() {
        getLogger().info("Launching UltraAntiSpam...");
        getServer().getLogger().info("\n          _       _________ _______  _______    _______  _       __________________   _______  _______  _______  _______ \n" +
                "|\\     /|( \\      \\__   __/(  ____ )(  ___  )  (  ___  )( (    /|\\__   __/\\__   __/  (  ____ \\(  ____ )(  ___  )(       )\n" +
                "| )   ( || (         ) (   | (    )|| (   ) |  | (   ) ||  \\  ( |   ) (      ) (     | (    \\/| (    )|| (   ) || () () |\n" +
                "| |   | || |         | |   | (____)|| (___) |  | (___) ||   \\ | |   | |      | |     | (_____ | (____)|| (___) || || || |\n" +
                "| |   | || |         | |   |     __)|  ___  |  |  ___  || (\\ \\) |   | |      | |     (_____  )|  _____)|  ___  || |(_)| |\n" +
                "| |   | || |         | |   | (\\ (   | (   ) |  | (   ) || | \\   |   | |      | |           ) || (      | (   ) || |   | |\n" +
                "| (___) || (____/\\   | |   | ) \\ \\__| )   ( |  | )   ( || )  \\  |   | |   ___) (___  /\\____) || )      | )   ( || )   ( |\n" +
                "(_______)(_______/   )_(   |/   \\__/|/     \\|  |/     \\||/    )_)   )_(   \\_______/  \\_______)|/       |/     \\||/     \\|\n" +
                "                                                                                                                         \n");
        long start = System.currentTimeMillis();
        saveDefaultConfig();
        holder = new SpamHolder(this);
        listener = new UltraAntiSpamListener(this);
        getLogger().info("Launch complete in " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    @Override
    public void onDisable() {
        holder = null;
        listener = null;
    }

}
