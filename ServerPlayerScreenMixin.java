package com.ultimateweapons.mixin;

import com.ultimateweapons.ability.StunManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

/**
 * Verhindert, dass ein betaeubter Spieler waehrend der Betaeubung Menues
 * (z.B. Werkbank, Truhen) oeffnet.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerScreenMixin {

	@Inject(method = "openMenu", at = @At("HEAD"), cancellable = true)
	private void ultimateweapons$blockMenuWhileStunned(MenuProvider menuProvider, CallbackInfoReturnable<OptionalInt> cir) {
		ServerPlayer self = (ServerPlayer) (Object) this;
		if (StunManager.isStunned(self)) {
			cir.setReturnValue(OptionalInt.empty());
		}
	}
}
