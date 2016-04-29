package am2.items;

import am2.entities.EntityAirSled;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemAirSled extends ArsMagicaItem{

	public ItemAirSled(){
		super();
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		par3List.add(StatCollector.translateToLocal("am2.tooltip.air_sled"));
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			EntityAirSled sled = new EntityAirSled(world);
			sled.setPosition(pos.getX() + hitX, pos.getY() + hitY + 0.5, pos.getZ() + hitZ);
			world.spawnEntityInWorld(sled);
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			return true;
		}
		return false;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List){
		par3List.add(ItemsCommonProxy.airSledEnchanted.copy());
	}

}
