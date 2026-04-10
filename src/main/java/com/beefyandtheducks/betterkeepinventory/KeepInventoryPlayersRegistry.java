package com.beefyandtheducks.betterkeepinventory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class KeepInventoryPlayersRegistry {

    private static final Duration COOLDOWN_DURATION = Duration.ofSeconds(10);

    private static class PlayerKeepInventory {
        public LocalDateTime timeOfChange;

        public boolean enabled;

        public PlayerKeepInventory(LocalDateTime timeOfChange, boolean enabled) {
            this.timeOfChange = timeOfChange;
            this.enabled = enabled;
        }
    }

    private static final HashMap<UUID, PlayerKeepInventory> KEEP_INVENTORY_MAP = new HashMap<>();

    public static boolean checkPlayerHasKeepInventory(UUID playerUUID) {
        if (!KEEP_INVENTORY_MAP.containsKey(playerUUID))
            return false;
        return KEEP_INVENTORY_MAP.get(playerUUID).enabled;
    }

    public static class SetKeepInventoryResult { public boolean success; public String error; }
    public static SetKeepInventoryResult setKeepInventory(UUID playerUUID, boolean enabled) {
        SetKeepInventoryResult result = new SetKeepInventoryResult();

        if (!KEEP_INVENTORY_MAP.containsKey(playerUUID)) {
            if (enabled) {
                KEEP_INVENTORY_MAP.put(playerUUID, new PlayerKeepInventory(LocalDateTime.now(), true));
                result.error = "N/A";
                result.success = true;
            } else {
                result.error = "Keep Inventory is already disabled!";
                result.success = false;
            }
            return result;
        }

        PlayerKeepInventory playerKeepInventory = KEEP_INVENTORY_MAP.get(playerUUID);

        if (playerKeepInventory.enabled == enabled) {
            result.error = "Keep Inventory is already %s!".formatted(enabled ? "enabled" : "disabled");
            result.success = false;
            return result;
        }

        Duration timeSinceChanged = Duration.between(playerKeepInventory.timeOfChange, LocalDateTime.now());
        if (timeSinceChanged.compareTo(COOLDOWN_DURATION) >= 0) {
            playerKeepInventory.enabled = enabled;
            playerKeepInventory.timeOfChange = LocalDateTime.now();
            result.error = "N/A";
            result.success = true;
            return result;
        }

        Duration remaining = COOLDOWN_DURATION.minus(timeSinceChanged);
        result.error = "Changing Keep Inventory is currently on cooldown, please wait another %s"
                .formatted(Util.humanReadableFormat(remaining));
        result.success = false;
        return result;
    }

}
