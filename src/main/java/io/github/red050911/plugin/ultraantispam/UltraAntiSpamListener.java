package io.github.red050911.plugin.ultraantispam;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class UltraAntiSpamListener implements Listener {

    private final UltraAntiSpam plugin;

    public UltraAntiSpamListener(UltraAntiSpam plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(this.plugin.holder.isMessageSpam(event.getMessage(), event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            String msgToSend = this.plugin.getConfig().getString("config.msg");
            if(msgToSend == null) msgToSend = "color.cPlease do not spam!";
            msgToSend = msgToSend.replace("color.", "§");
            event.getPlayer().sendMessage(msgToSend);
        } else {
            this.plugin.holder.cacheMessage(event.getMessage(), event.getPlayer().getUniqueId());
        }
    }

}
