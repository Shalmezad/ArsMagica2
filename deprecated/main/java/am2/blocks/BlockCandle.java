package am2.blocks;

import am2.blocks.tileentities.TileEntityCandle;
import am2.items.ItemsCommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCandle extends AMSpecialRenderBlockContainer{

	protected BlockCandle(){
		super(Material.wood);
		setHardness(1.0f);
		setResistance(1.0f);
		setBlockBounds(0.35f, 0f, 0.35f, 0.65f, 0.45f, 0.65f);
	}

    @Override
    public int getLightValue() {
        return 14;
    }

    @Override
	public int getRenderType(){
		return BlocksCommonProxy.blockRenderID;
	}

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack(ItemsCommonProxy.wardingCandle));
        return drops;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ItemsCommonProxy.wardingCandle);
    }

    @Override
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntityCandle();
	}

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.65, pos.getZ() + 0.5, 0, 0, 0);
    }
}