package com.beefyandtheducks.betterkeepinventory.commands;

import com.beefyandtheducks.betterkeepinventory.KeepInventoryPlayersRegistry;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class KeepInventoryCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) ->
                dispatcher.register(Commands.literal("keep_inventory")
                        .requires(CommandSourceStack::isPlayer)
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(context -> {
                            boolean enabled = BoolArgumentType.getBool(context, "enabled");
                            KeepInventoryPlayersRegistry.SetKeepInventoryResult result = KeepInventoryPlayersRegistry.setKeepInventory(context.getSource().getPlayerOrException().getUUID(), enabled);
                            if (result.success) {
                                context.getSource().sendSuccess(() -> Component.literal("Successfully %s Keep Inventory!".formatted(enabled ? "enabled" : "disabled")), true);
                                return 1;
                            }
                            context.getSource().sendFailure(Component.literal(result.error));
                            return -1;
        }))));
    }

}
