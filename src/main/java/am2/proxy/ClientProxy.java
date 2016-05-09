package am2.proxy;

import am2.AMCore;
import am2.api.math.AMVector3;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ClientProxy extends CommonProxy{
	public ClientProxy(){
	}

	@Override
	public void preinit(){
		super.preinit();
	}


	@Override
	public void init(){
		super.init();
		blocks.registerModels();
	}

	@Override
	public void flashManaBar(){
		//AMGuiHelper.instance.flashManaBar();
	}

	@Override
	public void blackoutArmorPiece(EntityPlayerMP entity, int index, int duration){
	}

	@Override
	public Entity getEntityByID(World world, int ID){
		return world.getEntityByID(ID);
	}

	@Override
	public EntityLivingBase getEntityByID(int entityID){
		Entity e = Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
		if (e instanceof EntityLivingBase) return (EntityLivingBase)e;
		return null;
	}

	@Override
	public EntityPlayer getLocalPlayer(){
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public WorldServer[] getWorldServers(){
		return FMLClientHandler.instance().getServer().worldServers;
	}

	@Override
	public void openSkillTreeUI(World world, EntityPlayer player){
		if (world.isRemote){
		}
	}

	@Override
	public boolean setMouseDWheel(int dwheel){
		if (dwheel == 0) return false;

		ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
		if (stack == null) return false;

		boolean store = checkForTKMove(stack);
		return false;
	}

	private boolean checkForTKMove(ItemStack stack){
		return false;
	}

	@Override
	public void renderGameOverlay(){
	}

	/* LOCALIZATION */
	@Override
	public String getCurrentLanguage(){
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}

	@Override
	public void addName(Object obj, String s){
		LanguageRegistry.addName(obj, s);
	}

	@Override
	public void addLocalization(String s1, String string){
		LanguageRegistry.instance().addStringLocalization(s1, string);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack){
		if (stack.getItem() == null)
			return "";

		return stack.getItem().getItemStackDisplayName(stack);
	}

	@Override
	public boolean isClientPlayer(EntityLivingBase ent){
		return ent instanceof AbstractClientPlayer;
	}

	/**
	 * Proxied compendium unlocks.  Do not call directly - use the CompendiumUnlockHandler instead.
	 */
	@Override
	public void unlockCompendiumEntry(String id){
	}

	/**
	 * Proxied compendium unlocks.  Do not call directly - use the CompendiumUnlockHandler instead.
	 */
	@Override
	public void unlockCompendiumCategory(String id){
	}

	public void addDeferredTargetSet(EntityLiving ent, EntityLivingBase target){
		//clientTickHandler.addDeferredTarget(ent, target);
	}
}

