package am2.proxy;

import am2.*;
import am2.api.math.AMVector3;
import am2.blocks.BlocksCommonProxy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CommonProxy{
	private ArrayList<AMVector3> pendingFlickerLinks;

	public BlocksCommonProxy blocks;
	public static HashMap<String, String> teamHostility;
	public NBTTagCompound cwCopyLoc;

	private HashMap<EntityLivingBase, ArrayList<PotionEffect>> deferredPotionEffects = new HashMap<EntityLivingBase, ArrayList<PotionEffect>>();
	private HashMap<EntityLivingBase, Integer> deferredDimensionTransfers = new HashMap<EntityLivingBase, Integer>();

	private int totalFlickerCount = 0;

	public CommonProxy(){
		teamHostility = new HashMap<String, String>();
		pendingFlickerLinks = new ArrayList<AMVector3>();
		cwCopyLoc = null;
	}

	public void postinit(){
	}

	public void preinit(){
		AMCore.config.init();
		blocks = new BlocksCommonProxy();
		blocks.InstantiateBlocks();
		blocks.RegisterBlocks();
		blocks.RegisterTileEntities();
	}

	public void init(){
	}


	public WorldServer[] getWorldServers(){
		return FMLServerHandler.instance().getServer().worldServers;
	}




	public HashMap<EntityLivingBase, Integer> getDeferredDimensionTransfers(){
		return (HashMap<EntityLivingBase, Integer>)deferredDimensionTransfers.clone();
	}


}
