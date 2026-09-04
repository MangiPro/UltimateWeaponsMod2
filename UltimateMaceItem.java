package com.ultimateweapons.item;

import com.ultimateweapons.ability.StunManager;
import com.ultimateweapons.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;

/**
 * Ultimate Mace: alle 10 Treffer wird der Gegner fuer 2 Sekunden betaeubt
 * (kann sich nicht bewegen oder umsehen) und ein Blitz schlaegt an seiner
 * Position ein.
 */
public class UltimateMaceItem extends MaceItem {

	private static final int HITS_UNTIL_STUN = 10;
	private static final int STUN_TICKS = 40; // 2 Sekunden

	public UltimateMaceItem(Item.Properties settings) {
		super(settings);
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.hurtEnemy(stack, target, attacker);

		int hits = stack.getOrDefault(ModDataComponents.MACE_HIT_COUNTER, 0) + 1;

		if (hits >= HITS_UNTIL_STUN) {
			hits = 0;

			if (attacker.level() instanceof ServerLevel serverLevel) {
				// Betaeubung: Bewegungsgeschwindigkeit = 0 + volles Einfrieren via StunManager.
				target.addEffect(new MobEffectInstance(ModEffects.stunHolder(), STUN_TICKS, 0, false, true, true));
				StunManager.stun(target, STUN_TICKS);

				// Blitzeinschlag an der Position des Gegners.
				LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
				bolt.moveTo(target.getX(), target.getY(), target.getZ());
				bolt.setVisualOnly(false);
				serverLevel.addFreshEntity(bolt);
			}
		}

		stack.set(ModDataComponents.MACE_HIT_COUNTER, hits);
	}
}
