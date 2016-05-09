package am2;

import am2.configuration.AMConfig;
import am2.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;

import java.io.File;

@Mod(modid = AMCore.MOD_ID, modLanguage = AMCore.MOD_LANGUAGE, name = AMCore.NAME, version = AMCore.VERSION, dependencies = AMCore.DEPENDENCIES)
public class AMCore{

	public static final String MOD_ID = "arsmagica2";
	public static final String MOD_LANGUAGE = "java";
	public static final String NAME = "Ars Magica 2";
	public static final String VERSION = "1.4.0.009";
	public static final String DEPENDENCIES = "";

	@SuppressWarnings("unused") //Handled by FML
	@Instance(value = "arsmagica2")
	public static AMCore instance;

	@SidedProxy(clientSide = "am2.proxy.ClientProxy", serverSide = "am2.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static AMConfig config;


	public AMCore(){
	}

	@SuppressWarnings("unused") //Handled by FML
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		String configBase = event.getSuggestedConfigurationFile().getAbsolutePath();
		configBase = popPathFolder(configBase);

		configBase += File.separatorChar + "AM2" + File.separatorChar;

		config = new AMConfig(new File(configBase + File.separatorChar + "AM2.cfg"));
		proxy.preinit();
	}

	private String popPathFolder(String path){
		int lastIndex = path.lastIndexOf(File.separatorChar);
		if (lastIndex == -1)
			lastIndex = path.length() - 1; //no path separator...strange, but ok.  Use full string.
		return path.substring(0, lastIndex);
	}

	@SuppressWarnings("unused") //Handled by FML
	@EventHandler
	public void init(FMLInitializationEvent event){

		FMLInterModComms.sendMessage("Waila", "register", "am2.interop.WailaSupport.callbackRegister");
        FMLInterModComms.sendMessage("Thaumcraft", "portableHoleBlacklist", "am2:everstone");
		proxy.init();
		initAPI();

		if (AMCore.config.getEnableWitchwoodForest()){
			//BiomeDictionary.registerBiomeType(BiomeWitchwoodForest.instance, Type.FOREST, Type.MAGICAL);
			//BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeEntry(BiomeWitchwoodForest.instance, 6));
		}
	}

	@SuppressWarnings("unused") //Handled by FML
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

		proxy.postinit();

		if (config.retroactiveWorldgen()){
			LogHelper.info("Retroactive Worldgen is enabled");
		}

	}

	@SuppressWarnings("unused") //Handled by FML
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
		for (WorldServer ws : MinecraftServer.getServer().worldServers){
			//PowerNodeCache.instance.saveWorldToFile(ws);
		}
	}

	@SuppressWarnings("unused") //Handled by FML
	@EventHandler
	public void onIMCReceived(FMLInterModComms.IMCEvent event){
		for (IMCMessage msg : event.getMessages()){
			if (msg.key == "dsb"){
				LogHelper.info("Received dimension spawn blacklist IMC!  Processing.");
				String[] split = msg.getStringValue().split("|");
				if (split.length != 2){
					LogHelper.warn("Could not parse dsb IMC - malformed identifiers!  Syntax is 'ClassName|DimensionID', for example:  EntityDryad|22");
					continue;
				}
				try{
					//SpawnBlacklists.addBlacklistedDimensionSpawn(split[0], Integer.parseInt(split[1]));
				}catch (NumberFormatException nex){
					LogHelper.warn("Could not parse dsb IMC - improper dimension ID (not a number)!  Syntax is 'ClassName|DimensionID', for example:  EntityDryad|22");
				}
			}else if (msg.key == "bsb"){
				LogHelper.info("Received biome spawn blacklist IMC!  Processing.");
				String[] split = msg.getStringValue().split("|");
				if (split.length != 2){
					LogHelper.warn("Could not parse bsb IMC - malformed identifiers!  Syntax is 'ClassName|BiomeID', for example:  EntityDryad|22");
					continue;
				}
				try{
					//SpawnBlacklists.addBlacklistedBiomeSpawn(split[0], Integer.parseInt(split[1]));
				}catch (NumberFormatException nex){
					LogHelper.warn("Could not parse bsb IMC - improper biome ID (not a number)!  Syntax is 'ClassName|BiomeID', for example:  EntityDryad|22");
				}
			}else if (msg.key == "dwg"){
				LogHelper.info("Received dimension worldgen blacklist IMC!  Processing.");
				try{
					//SpawnBlacklists.addBlacklistedDimensionForWorldgen(Integer.parseInt(msg.getStringValue()));
				}catch (NumberFormatException nex){
					LogHelper.warn("Could not parse dwg IMC - improper dimension ID (not a number)!  Syntax is 'dimensionID', for example:  2");
				}
			}else if (msg.key == "adb"){
				LogHelper.info("Received dispel blacklist IMC!  Processing.");
				try{
					//BuffList.instance.addDispelExclusion(new ResourceLocation(msg.getStringValue()));
				}catch (NumberFormatException nex){
					LogHelper.warn("Could not parse adb IMC - improper potion ID (not a number)!  Syntax is 'potionID', for example:  10");
				}
			}
		}
	}

	public void initAPI(){
		LogHelper.info("Initializing API Hooks...");
		/*
		ArsMagicaApi.instance.setSpellPartManager(SkillManager.instance);
		ArsMagicaApi.instance.setEnchantmentHelper(new AMEnchantmentHelper());
		ArsMagicaApi.instance.setSkillTreeManager(SkillTreeManager.instance);
		ArsMagicaApi.instance.setKeystoneHelper(KeystoneUtilities.instance);
		ArsMagicaApi.instance.setEntityManager(EntityManager.instance);
		ArsMagicaApi.instance.setObeliskFuelHelper(ObeliskFuelHelper.instance);
		ArsMagicaApi.instance.setFlickerOperatorRegistry(FlickerOperatorRegistry.instance);
		ArsMagicaApi.instance.setInfusionRegistry(ImbuementRegistry.instance);
		ArsMagicaApi.instance.setEssenceRecipeHandler(RecipesEssenceRefiner.essenceRefinement());
		ArsMagicaApi.instance.setColourblindMode(config.colourblindMode());
		ArsMagicaApi.instance.setBuffHelper(BuffList.instance);
		ArsMagicaApi.instance.setSpellUtils(SpellUtils.instance);

		ArsMagicaApi.instance.setAffinityDataID(AffinityData.identifier);
		ArsMagicaApi.instance.setSkillDataID(SkillData.identifier);
		ArsMagicaApi.instance.setExtendedPropertiesID(ExtendedProperties.identifier);
		ArsMagicaApi.instance.setRiftStorageID(RiftStorage.identifier);
		*/
		LogHelper.info("Finished API Initialization");
	}

	public String getVersion(){
		Mod modclass = this.getClass().getAnnotation(Mod.class);
		return modclass.version();
	}
}
