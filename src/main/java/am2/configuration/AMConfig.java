package am2.configuration;

import am2.LogHelper;
import am2.api.math.AMVector2;
//import am2.particles.AMParticle;
//import am2.particles.ParticleController;
import net.minecraftforge.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class AMConfig extends Configuration{

	private final String KEY_PlayerSpellsDamageTerrain = "Player_Spells_Destroy_Terrain";
	private final String KEY_NPCSpellsDamageTerrain = "NPC_Spells_Destroy_Terrain";
	private final String KEY_RetroactiveWorldGen = "RetroactiveWorldGen";

	private final String KEY_SecondarySkillTreeTierCap = "SecondarySkillTreeTierCap";
	private final String KEY_DigBreaksTEs = "DigBreaksTileEntities";
	private final String KEY_DisplayManaInInventory = "DisplayManaInInventory";
	private final String KEY_ManaCap = "Mana_Cap";
	private final String KEY_mageSpawnRate = "MageSpawnRate";
	private final String KEY_waterElementalSpawnRate = "WaterElementalSpawnRate";
	private final String KEY_hecateSpawnRate = "HecateSpawnRate";
	private final String KEY_dryadSpawnRate = "DryadSpawnRate";
	private final String KEY_manaElementalSpawnRate = "ManaElementalSpawnRate";
	private final String KEY_manaCreeperSpawnRate = "ManaCreeperSpawnRate";
	private final String KEY_darklingSpawnRate = "DarklingSpawnRate";
	private final String KEY_earthElementalSpawnRate = "EarthElementalSpawnRate";
	private final String KEY_fireElementalSpawnRate = "FireElementalSpawnRate";
	private final String KEY_flickerSpawnRate = "FlickerSpawnRate";

	private final String KEY_DamageMultiplier = "DamageMultiplier";

	private final String KEY_UseSpecialRenderers = "Use_Special_Renderers";
	private final String KEY_FrictionCoefficient = "FrictionCoefficient";

	private final String KEY_MageVillagerProfessionID = "mage_villager_profession_id";

	private final String KEY_DigDisabledBlocks = "dig_blacklist";
	private final String KEY_WorldgenBlacklist = "worldgen_blacklist";

	private final String KEY_DisarmAffectsPlayers = "disarm_affects_players";

	private final String KEY_WitchwoodForestBiomeID = "WitchwoodForestBiomeID";
	private final String KEY_WitchwoodForestRarity = "Witchwood_Forest_Biome_Rarity";

	private final String KEY_ForgeSmeltsVillagers = "ForgeSmeltsVillagers";
	private final String KEY_EverstoneRepairRate = "EverstoneRepairRate";

	private final String KEY_witchwoodLeavesFall = "WitchwoodLeafParticles";
	private final String KEY_CandlesAreRovingLights = "CandlesAreRovingLights";
	private final String KEY_Appropriation_Block_Blacklist = "Appropriation_Block_Blacklist";
	private final String KEY_Appropriation_Mob_Blacklist = "Appropriation_Mob_Blacklist";

	private final String KEY_AllowVersionChecks = "Allow_Version_Checks";
	private final String KEY_AllowCompendiumUpdates = "Allow_Compendium_Updates";
	private final String KEY_MeteorMinSpawnLevel = "Meteor_Spawn_Min_Level";
	private final String KEY_HazardousGateways = "Hazardous_Gateways";
	private final String KEY_CanDryadsDespawn = "Can_Dryads_Despawn";

	private final String KEY_ArmorXPInfusionFactor = "Armor_XP_Infusion_Factor";

	private final String KEY_SavePowerOnWorldSave = "PND_File_WSave";

	private final String KEY_EnableWitchwoodForest = "Enable_Witchwood_Forests";

	private final String KEY_allowCreativeTargets = "Allow_Creative_Targets";

	/**
	 * GUI Config
	 **/
	private final String KEY_ManaHudPositionX = "ManaHudPositionX";
	private final String KEY_BurnoutHudPositionX = "BurnoutHudPositionX";
	private final String KEY_BuffsPositivePositionX = "BuffsPositivePositionX";
	private final String KEY_BuffsNegativePositionX = "BuffsNegativePositionX";
	private final String KEY_LevelPositionX = "LevelPositionX";
	private final String KEY_AffinityPositionX = "AffinityPositionX";
	private final String KEY_ArmorPositionHeadX = "ArmorPositionHeadX";
	private final String KEY_ArmorPositionChestX = "ArmorPositionChestX";
	private final String KEY_ArmorPositionLegsX = "ArmorPositionLegsX";
	private final String KEY_ArmorPositionBootsX = "ArmorPositionBootsX";

	private final String KEY_ManaHudPositionY = "ManaHudPositionY";
	private final String KEY_BurnoutHudPositionY = "BurnoutHudPositionY";
	private final String KEY_BuffsPositivePositionY = "BuffsPositivePositionY";
	private final String KEY_BuffsNegativePositionY = "BuffsNegativePositionY";
	private final String KEY_LevelPositionY = "LevelPositionY";
	private final String KEY_AffinityPositionY = "AffinityPositionY";
	private final String KEY_ArmorPositionHeadY = "ArmorPositionHeadY";
	private final String KEY_ArmorPositionChestY = "ArmorPositionChestY";
	private final String KEY_ArmorPositionLegsY = "ArmorPositionLegsY";
	private final String KEY_ArmorPositionBootsY = "ArmorPositionBootsY";
	private final String KEY_XPBarPositionX = "XPBarPositionX";
	private final String KEY_XPBarPositionY = "XPBarPositionY";
	private final String KEY_ContingencyPositionX = "ContingencyPositionX";
	private final String KEY_ContingencyPositionY = "ContingencyPositionY";
	private final String KEY_ManaNumericPositionX = "ManaNumericX";
	private final String KEY_ManaNumericPositionY = "ManaNumericY";
	private final String KEY_BurnoutNumericPositionX = "BurnoutNumericX";
	private final String KEY_BurnoutNumericPositionY = "BurnoutNumericY";
	private final String KEY_XPNumericPositionX = "XPNumericX";
	private final String KEY_XPNumericPositionY = "XPNumericY";
	private final String KEY_SpellBookPositionX = "SpellBookX";
	private final String KEY_SpellBookPositionY = "SpellBookY";
	private final String KEY_EnderAffinityAbilityCooldown = "EnderAffinityAbilityCD";

	private final String KEY_StagedCompendium = "Staged Compendium";

	private final String KEY_ShowHudMinimally = "ShowHudMinimally";
	private final String KEY_ShowArmorUI = "ShowArmorUI";
	private final String KEY_moonstoneMeteorsDestroyTerrain = "MoonstoneMeteorDestroyTerrain";

	private final String KEY_ShowBuffs = "ShowBuffTimers";
	private final String KEY_ShowNumerics = "ShowNumericValues";
	private final String KEY_ShowXPAlways = "ShowXPAlways";
	private final String KEY_ShowHUDBars = "ShowHUDBars";
	private final String KEY_ColourblindMode = "ColourblindMode";
	/**
	 * End GUI Config
	 **/

	private static final String CATEGORY_BETA = "beta";
	private static final String CATEGORY_MOBS = "mobs";
	private static final String CATEGORY_ENCHANTMENTS = "enchantments";
	private static final String CATEGORY_UI = "guis";

	private boolean RetroWorldGen;

	private int[] worldgenBlacklist;
	private boolean enableWitchwoodForest;

	private Class[] appropriationMobBlacklist;

	public AMConfig(File file){
		super(file);
		load();
		addCustomCategoryComment(CATEGORY_BETA, "This applies to those who have beta auras unlocked only");
		addCustomCategoryComment(CATEGORY_ENCHANTMENTS, "Allows control over various enchantments in the mod.");
		addCustomCategoryComment(CATEGORY_MOBS, "Spawn control for different AM mobs.");
	}

	public void init(){

		boolean def = !Loader.isModLoaded("NotEnoughItems");

		Property retroWorldGenProp = get(CATEGORY_GENERAL, KEY_RetroactiveWorldGen, false, "Set this to true to enable retroactive worldgen for Ars Magica structures and ores.  *WARNING* This may break your save!  Do a backup first!  Note: This will automatically turn off after running the game once.");
		RetroWorldGen = retroWorldGenProp.getBoolean(false);

		if (RetroWorldGen){
			retroWorldGenProp.set(false);
		}

		enableWitchwoodForest = get(CATEGORY_GENERAL, KEY_EnableWitchwoodForest, true, "Disable this if you prefer the witchwood forest to not generate").getBoolean(true);

		String digBlacklistString = get(CATEGORY_GENERAL, KEY_DigDisabledBlocks, "", "Comma-separated list of block IDs that dig cannot break.  If a block is flagged as unbreackable in code, Dig will already be unable to break it.  There is no need to set it here (eg, bedrock, etc.).  Dig also makes use of Forge block harvest checks.  This is mainly for fine-tuning.").getString();

		String worldgenBlackList = get(CATEGORY_GENERAL, KEY_WorldgenBlacklist, "-27,-28,-29", "Comma-separated list of dimension IDs that AM should *not* do worldgen in.").getString();
		String[] split = worldgenBlackList.split(",");
		worldgenBlacklist = new int[split.length];
		int count = 0;
		for (String s : split){
			if (s.equals("")) continue;
			try{
				worldgenBlacklist[count] = Integer.parseInt(s.trim());
			}catch (Throwable t){
				LogHelper.info("Malformed item in worldgen blacklist (%s).  Skipping.", s);
				t.printStackTrace();
				worldgenBlacklist[count] = -1;
			}finally{
				count++;
			}
		}

		String apBlockBL = get(CATEGORY_GENERAL, KEY_Appropriation_Block_Blacklist, "", "Comma-separated list of block IDs that appropriation cannot pick up.").getString();

		String apEntBL = get(CATEGORY_GENERAL, KEY_Appropriation_Mob_Blacklist, "", "Comma-separated list of *fully qualified* Entity class names that appropriation cannot pick up - example, am2.entities.EntityDryad.  They are case sensitive.").getString();
		split = apEntBL.split(",");
		appropriationMobBlacklist = new Class[split.length];
		count = 0;
		for (String s : split){
			if (s.equals("")) continue;
			try{
				appropriationMobBlacklist[count] = Class.forName(s);
			}catch (Throwable t){
				LogHelper.info("Malformed item in appropriation entity blacklist (%s).  Skipping.", s);
				t.printStackTrace();
				appropriationMobBlacklist[count] = null;
			}finally{
				count++;
			}
		}

		initDirectProperties();

		save();
	}


	//====================================================================================
	// Getters - Cached
	//====================================================================================


	public boolean retroactiveWorldgen(){
		return RetroWorldGen;
	}

	public boolean getEnableWitchwoodForest(){
		return this.enableWitchwoodForest;
	}

	//====================================================================================
	// Getters - Aura
	//====================================================================================

	//====================================================================================
	// Getters - Direct
	//====================================================================================
	//ping the direct properties once so that they show up in config
	public void initDirectProperties(){
		get(CATEGORY_MOBS, KEY_hecateSpawnRate, 2);
		get(CATEGORY_MOBS, KEY_mageSpawnRate, 1);
		get(CATEGORY_MOBS, KEY_waterElementalSpawnRate, 3);
		get(CATEGORY_MOBS, KEY_manaElementalSpawnRate, 2);
		get(CATEGORY_MOBS, KEY_dryadSpawnRate, 5);
		get(CATEGORY_MOBS, KEY_manaCreeperSpawnRate, 3);
		get(CATEGORY_MOBS, KEY_darklingSpawnRate, 5);
		get(CATEGORY_MOBS, KEY_earthElementalSpawnRate, 2);
		get(CATEGORY_MOBS, KEY_fireElementalSpawnRate, 2);
		get(CATEGORY_MOBS, KEY_flickerSpawnRate, 2);
	}


}
