package am2.spell.components;

import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.items.ItemsCommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class CreateWater implements ISpellComponent{

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){

		Block block = world.getBlockState(pos).getBlock();

		if (block == Blocks.cauldron){
			world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(3), 2);
			//world.notifyBlockChange(blockx, blocky, blockz, block);
			return true;
		}

		switch (blockFace){
        case EAST:
			pos = pos.east();
			break;
        case NORTH:
			pos = pos.north();
			break;
        case SOUTH:
			pos = pos.south();
			break;
        case WEST:
			pos = pos.west();
			break;
        case DOWN:
			pos = pos.down();
			break;
		case UP:
			pos = pos.up();
			break;
		}

		block = world.getBlockState(pos).getBlock();
		if (world.isAirBlock(pos) || block == Blocks.snow || block == Blocks.water || block == Blocks.flowing_water || block instanceof BlockFlower){
			world.setBlockState(pos, Blocks.water.getDefaultState());
			Blocks.water.onNeighborBlockChange(world, pos, Blocks.water.getDefaultState(), Blocks.air); // TODO something other than this
			return true;
		}
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return false;
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 25;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return 30;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < 15; ++i){
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, x - 0.5 + rand.nextDouble(), y, z - 0.5 + rand.nextDouble(), 0.5 - rand.nextDouble(), 0.1, 0.5 - rand.nextDouble());
		}
	}

	@Override
	public EnumSet<Affinity> getAffinity(){
		return EnumSet.of(Affinity.WATER);
	}

	@Override
	public int getID(){
		return 7;
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				new ItemStack(ItemsCommonProxy.rune, 1, ItemsCommonProxy.rune.META_BLUE),
				Items.water_bucket
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.001f;
	}
}
