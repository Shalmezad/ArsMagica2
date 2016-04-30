package am2.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockMagicWall extends AMBlock{

	public BlockMagicWall(){
		super(Material.glass);
		this.setHardness(2.0f);
		this.setResistance(2.0f);
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
	public int quantityDropped(Random par1Random){
		return 0;
	}
}