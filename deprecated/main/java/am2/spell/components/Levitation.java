package am2.spell.components;

import am2.AMCore;
import am2.RitualShapeHelper;
import am2.api.ArsMagicaApi;
import am2.api.blocks.MultiblockStructureDefinition;
import am2.api.spell.component.interfaces.IRitualInteraction;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.api.spell.enums.SpellModifiers;
import am2.blocks.BlocksCommonProxy;
import am2.buffs.BuffEffectLevitation;
import am2.buffs.BuffList;
import am2.items.ItemsCommonProxy;
import am2.particles.AMParticle;
import am2.particles.ParticleOrbitEntity;
import am2.spell.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Levitation implements ISpellComponent, IRitualInteraction{

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing facing, double impactX, double impactY, double impactZ, EntityLivingBase caster) {
        return false;
    }

    @Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		if (target instanceof EntityLivingBase){
			int duration = SpellUtils.instance.getModifiedInt_Mul(BuffList.default_buff_duration, stack, caster, target, world, 0, SpellModifiers.DURATION);
			duration = SpellUtils.instance.modifyDurationBasedOnArmor(caster, duration);
			if (RitualShapeHelper.instance.checkForRitual(this, world, target.getPosition()) != null){
				duration += (3600 * (SpellUtils.instance.countModifiers(SpellModifiers.BUFF_POWER, stack, 0) + 1));
				RitualShapeHelper.instance.consumeRitualReagents(this, world, target.getPosition());
			}

			if (!world.isRemote)
				((EntityLivingBase)target).addPotionEffect(new BuffEffectLevitation(duration, SpellUtils.instance.countModifiers(SpellModifiers.BUFF_POWER, stack, 0)));
			return true;
		}
		return false;
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 80;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return ArsMagicaApi.instance.getBurnoutFromMana(manaCost(caster));
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < 15; ++i){
			AMParticle particle = (AMParticle)AMCore.proxy.particleManager.spawn(world, "ember", x, y, z);
			if (particle != null){
				particle.addRandomOffset(1, 0.5, 1);
				particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.1f + rand.nextFloat() * 0.1f, 1, false).SetTargetDistance(0.4f + rand.nextFloat() * 0.2f));
				particle.setMaxAge(40);
				particle.setParticleScale(0.1f);
				particle.setRGBColorF(0.2f, 0.2f, 0.6f);
				if (colorModifier > -1){
					particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}
	}

	@Override
	public EnumSet<Affinity> getAffinity(){
		return EnumSet.of(Affinity.AIR);
	}

	@Override
	public int getID(){
		return 30;
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				new ItemStack(ItemsCommonProxy.rune, 1, ItemsCommonProxy.rune.META_WHITE),
				BlocksCommonProxy.tarmaRoot
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.05f;
	}

	@Override
	public MultiblockStructureDefinition getRitualShape(){
		return RitualShapeHelper.instance.hourglass;
	}

	@Override
	public ItemStack[] getReagents(){
		return new ItemStack[]{
				new ItemStack(Items.feather),
				new ItemStack(BlocksCommonProxy.tarmaRoot)
		};
	}

	@Override
	public int getReagentSearchRadius(){
		return 3;
	}
}
