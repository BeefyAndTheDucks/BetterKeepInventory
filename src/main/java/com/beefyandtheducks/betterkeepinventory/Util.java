package com.beefyandtheducks.betterkeepinventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;

import java.time.Duration;

public class Util {

    public static <T> T overrideKeepInventory(Player self, GameRules instance, GameRule<T> gameRule) {
        if (gameRule == GameRules.KEEP_INVENTORY) {
            if (KeepInventoryPlayersRegistry.checkPlayerHasKeepInventory(self.getUUID()))
                return (T)(Boolean)true;
        }
        return instance.get(gameRule);
    }

    // https://stackoverflow.com/questions/3471397/how-can-i-pretty-print-a-duration-in-java
    public static String humanReadableFormat(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

}
