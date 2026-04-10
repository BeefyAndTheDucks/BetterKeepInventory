package com.beefyandtheducks.betterkeepinventory;

import com.beefyandtheducks.betterkeepinventory.commands.KeepInventoryCommand;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BetterKeepInventory implements ModInitializer {
    public static final String MOD_ID = "betterkeepinventory";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing BetterKeepInventory");

        KeepInventoryCommand.register();
    }
}
