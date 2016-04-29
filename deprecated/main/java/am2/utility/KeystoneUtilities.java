package am2.utility;

import am2.api.blocks.IKeystoneLockable;
import am2.api.items.IKeystoneHelper;
import am2.api.items.KeystoneAccessType;
import am2.items.ItemKeystone;
import am2.items.ItemRune;
import am2.items.ItemsCommonProxy;
import am2.playerextensions.ExtendedProperties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class KeystoneUtilities implements IKeystoneHelper{
  
	// constants to pass to the canPlayerAccess() function to say what you're trying to do
	// please use these by reference instead of hard-coded numbers
	public static final int MODE_NONE = 0;
	public static final int MODE_USE = 1;
	public static final int MODE_BREAK = 2;

	public static final KeystoneUtilities instance = new KeystoneUtilities();

	public static boolean HandleKeystoneRecovery(EntityPlayer player, IKeystoneLockable lock){
		if (ExtendedProperties.For(player).isRecoveringKeystone){
			if (KeystoneUtilities.instance.getKeyFromRunes(lock.getRunesInKey()) != 0){
				String combo = "";
				for (ItemStack rune : lock.getRunesInKey()){
					if (rune == null)
						combo += "empty ";
					else
						combo += rune.getDisplayName() + " ";
				}
				player.addChatMessage(new ChatComponentText(combo));
			}else{
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("am2.tooltip.noKeyPresent")));
			}
			ExtendedProperties.For(player).isRecoveringKeystone = false;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ArrayList<Long> GetKeysInInvenory(EntityLivingBase ent){
		ArrayList<Long> toReturn = new ArrayList<Long>();
		toReturn.add((long)0); //any inventory has the "0", or "unlocked" key

		if (ent instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer)ent;
			for (ItemStack is : p.inventory.mainInventory){
				if (is == null || !(is.getItem() instanceof ItemKeystone)) continue;

				ItemKeystone keystone = (ItemKeystone)is.getItem();
				long key = keystone.getKey(is);
				if (!toReturn.contains(key)){
					toReturn.add(key);
				}
			}
		}
		return toReturn;
	}

	@Override
	public long getKeyFromRunes(ItemStack[] runes){
		long key = 0;

		int index = 0;
		for (ItemStack stack : runes){
			if (stack == null || stack.getItem() != ItemsCommonProxy.rune) continue;
			long keyIndex = ((ItemRune)stack.getItem()).getKeyIndex(stack);
			key |= (keyIndex << (index * 16));
			index += 1;
		}

		return key;
	}


	@Override
	public boolean canPlayerAccess(IKeystoneLockable inventory, EntityPlayer player, KeystoneAccessType accessMode){
		ItemStack[] runes = inventory.getRunesInKey();
		long key = getKeyFromRunes(runes);

		if (key == 0) //no key combo set?  No lock!  Access granted!
			return true;

		if (inventory.keystoneMustBeHeld()){
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ItemsCommonProxy.keystone){
				return ((ItemKeystone)player.getCurrentEquippedItem().getItem()).getKey(player.getCurrentEquippedItem()) == key;
			}
		}else if (inventory.keystoneMustBeInActionBar()){
			for (int i = 0; i < 9; ++i){
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack == null || stack.getItem() != ItemsCommonProxy.keystone) continue;
				if (((ItemKeystone)stack.getItem()).getKey(stack) == key){
					return true;
				}
			}
		}else{
			for (int i = 0; i < player.inventory.mainInventory.length; ++i){
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack == null || stack.getItem() != ItemsCommonProxy.keystone) continue;
				if (((ItemKeystone)stack.getItem()).getKey(stack) == key){
					return true;
				}
			}
		}

		if (accessMode == KeystoneAccessType.USE){
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("am2.tooltip.wrongKeystoneUse")));
		}
		else if (accessMode == KeystoneAccessType.BREAK){
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("am2.tooltip.wrongKeystoneBreak")));
		}
		return false;
	}

}
