package am2.buffs;

import am2.utility.KeyValuePair;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;

import java.util.UUID;

public class BuffStatModifiers{
	public static final BuffStatModifiers instance = new BuffStatModifiers();

	public void applyStatModifiersBasedOnBuffs(EntityLivingBase entity){
		//entangled
		applyOrRemoveModifiersForBuff(entity, BuffList.entangled.id, new KeyValuePair(SharedMonsterAttributes.movementSpeed, entangled));
		//frost slow
		applyOrRemoveScalingModifiersForBuff(entity, BuffList.frostSlowed.id, SharedMonsterAttributes.movementSpeed, frostSlow_Diminished, frostSlow_Normal, frostSlow_Augmented);
		//fury
		applyOrRemoveModifiersForBuff(entity, BuffList.fury.id, new KeyValuePair(SharedMonsterAttributes.movementSpeed, furyMoveMod), new KeyValuePair(SharedMonsterAttributes.attackDamage, furyDmgMod));
		//haste
		applyOrRemoveScalingModifiersForBuff(entity, BuffList.haste.id, SharedMonsterAttributes.movementSpeed, hasteSpeedBoost_Diminished, hasteSpeedBoost_Normal, hasteSpeedBoost_Augmented);
	}

	private void applyOrRemoveModifiersForBuff(EntityLivingBase entity, int buffID, KeyValuePair<IAttribute, AttributeModifier>... modifiers){
		if (entity.isPotionActive(buffID)){
			applyAllModifiers(entity, modifiers);
		}else{
			clearAllModifiers(entity, modifiers);
		}
	}

	private void applyOrRemoveScalingModifiersForBuff(EntityLivingBase entity, int potionID, IAttribute attribute, AttributeModifier... modifiers){
		IAttributeInstance inst = entity.getEntityAttribute(attribute);
		if (inst == null) {
			return;
		}
		AttributeModifier currentModifier = inst.getModifier(modifiers[0].getID());
		if (entity.isPotionActive(potionID)){
			int magnitude = entity.getActivePotionEffect(Potion.potionTypes[potionID]).getAmplifier();
			AttributeModifier modifier = modifiers[Math.min(magnitude, modifiers.length - 1)];
			if (currentModifier != modifier) {
				if (currentModifier != null) {
					inst.removeModifier(currentModifier);
				}
				inst.applyModifier(modifier);
			}
		}else{
			if (currentModifier != null) {
				inst.removeModifier(currentModifier);
			}
		}
	}

	private void applyAllModifiers(EntityLivingBase entity, KeyValuePair<IAttribute, AttributeModifier>... modifiers){
		for (KeyValuePair<IAttribute, AttributeModifier> entry : modifiers){
			IAttributeInstance inst = entity.getEntityAttribute(entry.getKey());
			if (inst == null)
				continue;
			AttributeModifier currentModifier = inst.getModifier(entry.getValue().getID());
			if (currentModifier != entry.getValue()){
				if (currentModifier != null) {
					inst.removeModifier(currentModifier);
				}
				inst.applyModifier(entry.getValue());
			}
		}
	}

	private void clearAllModifiers(EntityLivingBase entity, KeyValuePair<IAttribute, AttributeModifier>... modifiers){
		for (KeyValuePair<IAttribute, AttributeModifier> entry : modifiers){
			IAttributeInstance inst = entity.getEntityAttribute(entry.getKey());
			if (inst == null)
				continue;
			AttributeModifier currentModifier = inst.getModifier(entry.getValue().getID());
			if (currentModifier == entry.getValue()) {
				inst.removeModifier(currentModifier);
			}
		}
	}

	//*  Fury  *//
	private static final UUID furyMoveID = UUID.fromString("03B0A79B-9769-43AE-BFE3-830D993D4A69");
	private static final AttributeModifier furyMoveMod = (new AttributeModifier(furyMoveID, "Fury (Movement)", 2, 2));

	private static final UUID furyDmgID = UUID.fromString("03B0A79B-9769-43AE-BFE3-830D993D4A70");
	private static final AttributeModifier furyDmgMod = (new AttributeModifier(furyDmgID, "Fury (Damage)", 5, 2));

	//*  Entangle  *//
	private static final UUID entangledID = UUID.fromString("F5047292-E5F9-4EB1-986E-9A5DFE832203");
	private static final AttributeModifier entangled = (new AttributeModifier(entangledID, "Entangled", -10, 2));

	//*  Haste *//
	private static final UUID hasteID = UUID.fromString("CA4D2B5D-D509-49C0-9B2C-C0A338883AB1");
	private static final AttributeModifier hasteSpeedBoost_Diminished = (new AttributeModifier(hasteID, "Haste Speed Boost (Diminished)", 0.3D, 2));
	private static final AttributeModifier hasteSpeedBoost_Normal = (new AttributeModifier(hasteID, "Haste Speed Boost (Normal)", 0.6D, 2));
	private static final AttributeModifier hasteSpeedBoost_Augmented = (new AttributeModifier(hasteID, "Haste Speed Boost (Augmented)", 1.2D, 2));

	//*  Frost Slow *//
	private static final UUID frostSlowID = UUID.fromString("03B0A79B-9569-43AE-BFE3-820D993D4A64");
	private static final AttributeModifier frostSlow_Diminished = (new AttributeModifier(frostSlowID, "Frost Slow (Diminished)", -0.2, 2));
	private static final AttributeModifier frostSlow_Normal = (new AttributeModifier(frostSlowID, "Frost Slow (Normal)", -0.5, 2));
	private static final AttributeModifier frostSlow_Augmented = (new AttributeModifier(frostSlowID, "Frost Slow (Augmented)", -0.8, 2));
}
