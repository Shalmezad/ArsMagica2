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
		blocks.setupSpellConstraints();

	}

	public void preinit(){
		AMCore.config.init();
		blocks = new BlocksCommonProxy();
		blocks.InstantiateBlocks();
		blocks.RegisterBlocks();
		blocks.RegisterTileEntities();
		blocks.InitRecipes();
	}

	public void init(){
	}

	public void flashManaBar(){
	}

	public void blackoutArmorPiece(EntityPlayerMP player, int slot, int cooldown){
		//serverTickHandler.blackoutArmorPiece(player, slot, cooldown);
	}

	public Entity getEntityByID(World world, int ID){
		Entity ent = null;
		for (Object o : world.loadedEntityList){
			if (o instanceof EntityLivingBase){
				ent = (EntityLivingBase)o;
				if (ent.getEntityId() == ID){
					return ent;
				}
			}
		}
		return null;
	}

	public WorldServer[] getWorldServers(){
		return FMLServerHandler.instance().getServer().worldServers;
	}

	public EntityLivingBase getEntityByID(int entityID){
		Entity ent = null;
		for (WorldServer ws : getWorldServers()){
			ent = ws.getEntityByID(entityID);
			if (ent != null){
				if (!(ent instanceof EntityLivingBase)) return null;
				else break;
			}
		}
		return (EntityLivingBase)ent;
	}

	public EntityPlayer getLocalPlayer(){
		return null;
	}

	public int getArmorRenderIndex(String prefix){
		return 0;
	}

	public void openSkillTreeUI(World world, EntityPlayer player){
	}

	public boolean setMouseDWheel(int dwheel){
		return false;
	}

	public void renderGameOverlay(){
	}

	/* LOCALIZATION */
	public void addName(Object obj, String s){
	}

	public void addLocalization(String s1, String string){
	}

	public String getItemStackDisplayName(ItemStack newStack){
		return "";
	}

	public String getCurrentLanguage(){
		return null;
	}

	public void sendLocalMovementData(EntityLivingBase ent){

	}

	public void setCompendiumSaveBase(String compendiumBase){

	}

	public void addDeferredPotionEffect(EntityLivingBase ent, PotionEffect pe){
		if (!deferredPotionEffects.containsKey(ent))
			deferredPotionEffects.put(ent, new ArrayList<PotionEffect>());

		ArrayList<PotionEffect> effects = deferredPotionEffects.get(ent);
		effects.add(pe);
	}

	public void addDeferredDimensionTransfer(EntityLivingBase ent, int dimension){
		deferredDimensionTransfers.put(ent, dimension);
	}

	public HashMap<EntityLivingBase, ArrayList<PotionEffect>> getDeferredPotionEffects(){
		return (HashMap<EntityLivingBase, ArrayList<PotionEffect>>)deferredPotionEffects.clone();
	}

	public void clearDeferredDimensionTransfers(){
		deferredDimensionTransfers.clear();
	}

	public HashMap<EntityLivingBase, Integer> getDeferredDimensionTransfers(){
		return (HashMap<EntityLivingBase, Integer>)deferredDimensionTransfers.clone();
	}

	public void clearDeferredPotionEffects(){
		deferredPotionEffects.clear();
	}

	public boolean isClientPlayer(EntityLivingBase ent){
		return false;
	}

	public void setTrackedPowerCompound(NBTTagCompound compound){
	}

	public void setTrackedLocation(AMVector3 location){
	}

	public boolean hasTrackedLocationSynced(){
		return false;
	}

	/**
	 * Proxied compendium unlocks.  Do not call directly - use the CompendiumUnlockHandler instead.
	 */
	public void unlockCompendiumEntry(String id){

	}

	/**
	 * Proxied compendium unlocks.  Do not call directly - use the CompendiumUnlockHandler instead.
	 */
	public void unlockCompendiumCategory(String id){

	}

	public void drawPowerOnBlockHighlight(EntityPlayer player, MovingObjectPosition target, float partialTicks){

	}

	public void incrementFlickerCount(){
		this.totalFlickerCount++;
	}

	public void decrementFlickerCount(){
		this.totalFlickerCount--;
		if (this.totalFlickerCount < 0)
			this.totalFlickerCount = 0;
	}

	public int getTotalFlickerCount(){
		return this.totalFlickerCount;
	}

	public void addDigParticle(World worldObj, int xCoord, int yCoord, int zCoord, Block block, int meta){
	}
}
