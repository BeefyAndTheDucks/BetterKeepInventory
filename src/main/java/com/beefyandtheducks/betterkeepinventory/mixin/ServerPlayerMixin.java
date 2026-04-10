package com.beefyandtheducks.betterkeepinventory.mixin;

import com.beefyandtheducks.betterkeepinventory.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Redirect(method = "restoreFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private <T> T replace(GameRules instance, GameRule<T> gameRule) {
        Player self = (ServerPlayer)(Object)this;
        return Util.overrideKeepInventory(self, instance, gameRule);
    }

}
