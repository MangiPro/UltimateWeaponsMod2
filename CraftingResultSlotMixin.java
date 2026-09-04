package com.ultimateweapons.mixin;

import com.ultimateweapons.craft.UniqueCraftRegistry;
import com.ultimateweapons.craft.UniqueCraftState;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Sorgt dafuer, dass jedes "Ultimate"-Item nur EIN einziges Mal pro Welt aus
 * dem Werkbank-Ergebnisfeld entnommen werden kann. Beim ersten erfolgreichen
 * Craften wird eine rote Chat-Nachricht an den ganzen Server gesendet.
 */
@Mixin(ResultSlot.class)
public abstract class CraftingResultSlotMixin {

	@Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
	private void ultimateweapons$onTake(Player player, ItemStack stack, CallbackInfo ci) {
		Item item = stack.getItem();
		if (!UniqueCraftRegistry.isUnique(item)) {
			return;
		}

		if (!(player.level() instanceof ServerLevel serverLevel)) {
			return;
		}

		Identifier itemId = UniqueCraftRegistry.idOf(item);
		UniqueCraftState state = UniqueCraftState.get(serverLevel.getServer());

		if (state.isCrafted(itemId)) {
			// Bereits einmal hergestellt: Entnahme aus dem Ergebnisfeld verhindern.
			ci.cancel();
			player.displayClientMessage(
					Component.literal("Dieses Item wurde in dieser Welt bereits einmalig hergestellt!")
							.withStyle(ChatFormatting.RED),
					true);
			return;
		}

		state.markCrafted(itemId);

		Component message = Component.literal(
				player.getName().getString() + " hat " + stack.getHoverName().getString() + " hergestellt!"
		).withStyle(ChatFormatting.RED);

		serverLevel.getServer().getPlayerList().broadcastSystemMessage(message, false);
	}
}
