package com.beefyandtheducks.betterkeepinventory.mixin;

import com.beefyandtheducks.betterkeepinventory.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {

    @Redirect(method = "getBaseExperienceReward", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private <T> T redirectGetBaseExperienceReward(GameRules instance, GameRule<T> gameRule) {
        Player self = (Player)(Object)this;
        return Util.overrideKeepInventory(self, instance, gameRule);
    }

    @Redirect(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private <T> T redirectDropEquipment(GameRules instance, GameRule<T> gameRule) {
        Player self = (Player)(Object)this;
        return Util.overrideKeepInventory(self, instance, gameRule);
    }

}
