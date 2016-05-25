package am2.proxy;

import am2.*;
import am2.api.math.AMVector3;
import am2.registry.ModBlocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProxy{

	private ArrayList<AMVector3> pendingFlickerLinks;

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
		ModBlocks.createBlocks();

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
