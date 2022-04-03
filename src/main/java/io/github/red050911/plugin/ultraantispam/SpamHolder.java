package io.github.red050911.plugin.ultraantispam;

import java.util.*;

public class SpamHolder {

    private final UltraAntiSpam plugin;
    private final Map<UUID, List<String>> map;

    public SpamHolder(UltraAntiSpam plugin) {
        this.plugin = plugin;
        this.map = new HashMap<>();
    }

    public void cacheMessage(String msg, UUID player) {
        List<String> msgList = map.computeIfAbsent(player, key -> new ArrayList<>());
        int sensitivity = plugin.getConfig().getInt("config.sensitivity");
        if(msgList.size() > sensitivity) garbageCollect();
        if(msgList.size() == sensitivity) msgList.remove(0);
        msgList.add(msg);
    }

    public boolean isMessageSpam(String msg, UUID player) {
        List<String> msgList = map.get(player);
        if (msgList == null) return false;
        int sensitivity = plugin.getConfig().getInt("config.sensitivity");
        boolean isSpam = false;
        if (msgList.size() > sensitivity) {
            int maxIndex = sensitivity - 1;
            int index = 0;
            for (String pastMsg : msgList) {
                if (index <= maxIndex) {
                    String pastMsgRecalc = plugin.getConfig().getBoolean("config.fixes.case") ? pastMsg.toLowerCase(Locale.ROOT) : pastMsg;
                    String msgRecalc = plugin.getConfig().getBoolean("config.fixes.case") ? msg.toLowerCase(Locale.ROOT) : msg;
                    if (msgRecalc.equals(pastMsgRecalc)) {
                        isSpam = true;
                    }
                    if (plugin.getConfig().getBoolean("config.fixes.lettersAdded") && msgRecalc.startsWith(pastMsgRecalc)) {
                        isSpam = true;
                    }
                    if (plugin.getConfig().getBoolean("config.fixes.lettersAdded") && msgRecalc.endsWith(pastMsgRecalc)) {
                        isSpam = true;
                    }
                }
                index++;
            }
        } else {
            for (String pastMsg : msgList) {
                String pastMsgRecalc = plugin.getConfig().getBoolean("config.fixes.case") ? pastMsg.toLowerCase(Locale.ROOT) : pastMsg;
                String msgRecalc = plugin.getConfig().getBoolean("config.fixes.case") ? msg.toLowerCase(Locale.ROOT) : msg;
                if (msgRecalc.equals(pastMsgRecalc)) {
                    isSpam = true;
                }
                if (plugin.getConfig().getBoolean("config.fixes.lettersAdded") && msg.startsWith(pastMsg)) {
                    isSpam = true;
                }
                if (plugin.getConfig().getBoolean("config.fixes.lettersAdded") && msg.endsWith(pastMsg)) {
                    isSpam = true;
                }
            }
        }
        return isSpam;
    }

    public void garbageCollect() {
        int sensitivity = plugin.getConfig().getInt("config.sensitivity");
        plugin.getLogger().info("Spam holder cleanup starting with sensitivity " + sensitivity);
        int maxIndex = sensitivity - 1;
        int unnecessaryStrings = 0;
        for (List<String> list : map.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (i > maxIndex) {
                    list.remove(i);
                    i--;
                    unnecessaryStrings++;
                }
            }
        }
        plugin.getLogger().info("Spam holder cleanup complete! " + unnecessaryStrings + (unnecessaryStrings == 1 ? " unnecessary string was" : " unnecessary strings were") + " removed.");
    }

}
