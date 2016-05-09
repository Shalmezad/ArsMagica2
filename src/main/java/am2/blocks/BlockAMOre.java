package am2.blocks;

import am2.blocks.core.BlockVariant;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockAMOre extends BlockOre implements BlockVariant{

	public static final PropertyEnum<EnumOreType> ORE_TYPE = PropertyEnum.<EnumOreType>create("ore_type", EnumOreType.class);

	@SideOnly(Side.CLIENT)
	private String[] textures;

	public static final int NUM_TYPES = 2;//10;

	public BlockAMOre(){
		super();
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0f);
		this.setResistance(3.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ORE_TYPE, EnumOreType.VINTEUM_ORE));
		this.setUnlocalizedName("arsmagica2:ores");
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List){
		for (int i = 0; i < NUM_TYPES; ++i){
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();

		switch (meta)
		{
		case 0:
			iblockstate = iblockstate.withProperty(ORE_TYPE, EnumOreType.VINTEUM_ORE);
			break;
		default:
			iblockstate = iblockstate.withProperty(ORE_TYPE, EnumOreType.CHIMERITE_ORE);
		}

		return iblockstate;
	}


	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		switch (state.getValue(ORE_TYPE))
		{
		case VINTEUM_ORE:
			i = 0;
			break;
		default:
			i = 0;
		}

		return i;
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {ORE_TYPE});
	}

	@Override
	public int numVariants(){
		return NUM_TYPES;
	}


	public enum EnumOreType implements IStringSerializable
	{
		VINTEUM_ORE("vinteum_ore"),
		CHIMERITE_ORE("chimerite_ore");

		private final String name;

		private EnumOreType(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}


		public String getName()
		{
			return this.name;
		}
	}
}
