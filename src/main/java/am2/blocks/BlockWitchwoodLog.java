package am2.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockWitchwoodLog extends BlockLog{
	public BlockWitchwoodLog(){
		super();
		this.setHardness(3.0f);
		this.setResistance(3.0f);
		this.setHarvestLevel("axe", 2);
		this.setUnlocalizedName("arsmagica2:witchwoodlog");
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random){
		return 1;
	}

	/**
	 * returns a number between 0 and 3
	 * @param par0 The original parameter
	 * @return The parameter limited to [0,3]
	 */
	public static int limitToValidMetadata(int par0){
		return par0 & 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List){
		par3List.add(new ItemStack(this));
	}

    @Override
    public boolean canSustainLeaves(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

	/*
	http://stackoverflow.com/questions/36847653/when-placing-custom-wood-block-game-crashes
	http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2569406-custom-logs-and-trees
	Code based off net.minecraft.block.BlockNewLog
	 */

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();//.withProperty(VARIANT, BlockPlanks.EnumType.byMetadata((meta & 3) + 4));

		switch (meta & 12)
		{
		case 0:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			break;
		case 4:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			break;
		case 8:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			break;
		default:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
	}


	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@SuppressWarnings("incomplete-switch")
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		//i = i | ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMetadata() - 4;

		switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
		{
		case X:
			i |= 4;
			break;
		case Z:
			i |= 8;
			break;
		case NONE:
			i |= 12;
		}

		return i;
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {LOG_AXIS});
	}


}
