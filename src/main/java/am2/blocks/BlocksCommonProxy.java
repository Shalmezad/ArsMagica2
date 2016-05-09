package am2.blocks;

import am2.AMCore;
import am2.AMCreativeTab;
import am2.api.math.AMVector3;
import am2.blocks.core.BlockVariant;
import am2.items.OreItem;
import am2.utility.RecipeUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlocksCommonProxy{
	//--------------------------------------------------------------
	// Blocks
	//--------------------------------------------------------------
	public static Block essenceRefiner;
	public static Block blockMageTorch;
	public static Block essenceConduit;
	public static Block obelisk;
	public static Block blackAurem;
	public static Block celestialPrism;
	public static Block calefactor;
	public static Block astralBarrier;
	public static Block seerStone;
	public static BlockAMOre AMOres;
	public static Block blockLectern;
	public static Block blockArcaneReconstructor;
	public static Block manaBattery;
	public static Block magicWall;
	public static Block occulus;
	public static BlockWitchwoodLog witchwoodLog;
	public static WitchwoodPlanks witchwoodPlanks;
	public static WitchwoodSlabs witchwoodSingleSlab;
	public static WitchwoodSlabs witchwoodDoubleSlab;
	public static WitchwoodStairs witchwoodStairs;

	//--------------------------------------------------------------
	// End Blocks
	//---------------------------------------------------------------

	public static int blockRenderID;
	public static int commonBlockRenderID;
	public static AMCreativeTab blockTab;

	private static ArrayList<Block> arsMagicaBlocksList;

	public static HashMap<Integer, ArrayList<AMVector3>> KeystonePortalLocations;

	public BlocksCommonProxy(){
		KeystonePortalLocations = new HashMap<Integer, ArrayList<AMVector3>>();
		if (blockTab == null)
			blockTab = new AMCreativeTab("Ars Magica Blocks");

		if (arsMagicaBlocksList == null)
			arsMagicaBlocksList = new ArrayList<Block>();
	}

	public List<Block> getArsMagicaBlocks(){
		return arsMagicaBlocksList;
	}

	public void registerKeystonePortal(BlockPos pos, int dimension){
		AMVector3 location = new AMVector3(pos.getX(), pos.getY(), pos.getZ());
		if (!KeystonePortalLocations.containsKey(dimension))
			KeystonePortalLocations.put(dimension, new ArrayList<AMVector3>());

		ArrayList<AMVector3> dimensionList = KeystonePortalLocations.get(dimension);

		if (!dimensionList.contains(location))
			dimensionList.add(location);
	}

	public void removeKeystonePortal(BlockPos pos, int dimension){
		AMVector3 location = new AMVector3(pos.getX(), pos.getY(), pos.getZ());
		if (KeystonePortalLocations.containsKey(dimension)){
			ArrayList<AMVector3> dimensionList = KeystonePortalLocations.get(dimension);

			if (dimensionList.contains(location))
				dimensionList.remove(location);
		}
	}

	public AMVector3 getNextKeystonePortalLocation(World world, BlockPos pos, boolean multidimensional, long key){
		AMVector3 current = new AMVector3(pos.getX(), pos.getY(), pos.getZ());
		if (!multidimensional){
			AMVector3 next = getNextKeystoneLocationInWorld(world, pos, key);
			if (next == null)
				next = current;
			return next;
		}else{
			return current;
		}
	}

	public AMVector3 getNextKeystoneLocationInWorld(World world, BlockPos pos, long key){
		AMVector3 location = new AMVector3(pos.getX(), pos.getY(), pos.getZ());
		ArrayList<AMVector3> dimensionList = KeystonePortalLocations.get(world.provider.getDimensionId());
		if (dimensionList == null || dimensionList.size() < 1){
			return null;
		}

		int index = dimensionList.indexOf(location);
		index++;
		if (index >= dimensionList.size()) index = 0;
		AMVector3 newLocation = dimensionList.get(index);

		while (!newLocation.equals(location)){
			TileEntity te = world.getTileEntity(pos);
			index++;
			if (index >= dimensionList.size()) index = 0;
			newLocation = dimensionList.get(index);
		}

		return location;
	}

	public void resetKnownPortalLocations(){
		KeystonePortalLocations.clear();
	}

	public void InstantiateBlocks(){
		AMOres = (BlockAMOre)new BlockAMOre().setRegistryName("arsmagica2:ores").setCreativeTab(blockTab);
		witchwoodLog = (BlockWitchwoodLog)new BlockWitchwoodLog().setRegistryName("arsmagica2:witchwoodlog").setCreativeTab(blockTab);
		witchwoodPlanks = (WitchwoodPlanks)new WitchwoodPlanks().setRegistryName("arsmagica2:planksWitchwood").setCreativeTab(blockTab);
		witchwoodSingleSlab = (WitchwoodSlabs)new WitchwoodSlabs(false).setRegistryName("arsmagica2:witchwoodSingleSlab").setCreativeTab(blockTab);
		witchwoodDoubleSlab = (WitchwoodSlabs)new WitchwoodSlabs(true).setRegistryName("arsmagica2:witchwoodDoubleSlab");
		blockTab.setIconItemIndex(Items.ender_eye);
	}

	public void setupSpellConstraints(){
		//Dig dug = ((Dig)SkillManager.instance.getSkill("Dig"));
		//dug.addDisallowedBlock(invisibleUtility.getUnlocalizedName());
	}

	public void InitRecipes(){

		List recipes = CraftingManager.getInstance().getRecipeList();

		//essence refiner
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(essenceRefiner, 1),
				new Object[]
						{
								"PDP", "OAO", "PPP",
								Character.valueOf('P'), "plankWood",
								Character.valueOf('O'), Blocks.obsidian,
								Character.valueOf('A'), "arcaneAsh",
								Character.valueOf('D'), "gemDiamond"
						}));

		//essence conduit
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(essenceConduit, 1), new Object[]{
				" C ", " S ", "SSS",
				Character.valueOf('S'), "stone",
				Character.valueOf('C'), "gemChimerite"
		}));

		//Calefactor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(calefactor, 1), new Object[]{
				"L L",
				"SRS",
				"SVS",
				Character.valueOf('L'), new ItemStack(Items.dye, 1, 4), //lapis
				Character.valueOf('S'), "stone",
				Character.valueOf('R'), "dustRedstone",
				Character.valueOf('V'), "dustVinteum"
		}));

		//astral barrier
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(astralBarrier, 1), new Object[]{
				"WVW", "E E", "WVW",
				//Character.valueOf('P'), new ItemStack(ItemsCommonProxy.essence, 1, ItemsCommonProxy.essence.META_ENDER),
				Character.valueOf('W'), Blocks.cobblestone_wall,
				Character.valueOf('E'), Items.ender_eye,
				Character.valueOf('V'), "dustVinteum"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(seerStone, 1), new Object[]{
				" E ", "SRS",
				Character.valueOf('S'), "stone", //stone wall
				Character.valueOf('E'), Items.ender_eye,
				Character.valueOf('R'), "dustRedstone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockLectern), new Object[]{
				"SSS", " P ",
				Character.valueOf('S'), "slabWood",
				Character.valueOf('P'), "plankWood"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(occulus), new Object[]{
				"SPS", " S ", "CVC",
				Character.valueOf('S'), Blocks.stonebrick,
				Character.valueOf('C'), Items.coal,
				Character.valueOf('P'), "blockGlassColorless",
				Character.valueOf('V'), "gemBlueTopaz"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(manaBattery), new Object[]{
				"IVI",
				"VAV",
				"IVI",
				Character.valueOf('I'), "gemChimerite",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('A'), "arcaneAsh"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magicWall, 16, 0), new Object[]{
				"VSV",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stone"
		}));

		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), new Object[]{
				"SS",
				"SS",
				Character.valueOf('S'), Blocks.stonebrick
		});
		GameRegistry.addRecipe(new ItemStack(witchwoodPlanks, 4), new Object[]{
				"W",
				Character.valueOf('W'), witchwoodLog
		});

		RecipeUtilities.addShapedRecipeFirst(recipes, new ItemStack(witchwoodSingleSlab, 6), new Object[]{
				"WWW",
				Character.valueOf('W'), witchwoodPlanks
		});

		RecipeUtilities.addShapedRecipeFirst(recipes, new ItemStack(witchwoodStairs, 4), new Object[]{
				"  W",
				" WW",
				"WWW",
				Character.valueOf('W'), witchwoodPlanks
		});

		//Obelisk
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(obelisk), new Object[]{
				"VSV",
				"SCS",
				"VSV",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stone",
				Character.valueOf('C'), new ItemStack(Blocks.stonebrick, 1, 3)
		}));
	}

	private void addMetaSmeltingRecipe(Block input, int meta, ItemStack output){
		ItemStack stack = new ItemStack(input);
		stack.setItemDamage(meta);
		GameRegistry.addSmelting(stack, output, 0);
	}

	private void createStorageBlockRecipe(ItemStack storageBlock, ItemStack storageItem){
		GameRegistry.addRecipe(storageBlock, new Object[]{
				"III",
				"III",
				"III",
				Character.valueOf('I'), new ItemStack(storageItem.getItem(), 1, storageItem.getItemDamage())
		});

		GameRegistry.addShapelessRecipe(new ItemStack(storageItem.getItem(), 9, storageItem.getItemDamage()), storageBlock);
	}

	public void RegisterBlocks(){
		registerBlock(AMOres, OreItem.class, "vinteumOre");
		registerBlock(witchwoodLog,ItemBlock.class, "WitchwoodLog");
		registerBlock(witchwoodPlanks, "planksWitchwood");
		registerMultiTextureBlock(witchwoodSingleSlab, "witchwoodSingleSlab", new ItemSlab(witchwoodSingleSlab, witchwoodSingleSlab, witchwoodDoubleSlab));
		registerMultiTextureBlock(witchwoodDoubleSlab, "witchwoodDoubleSlab", new ItemSlab(witchwoodDoubleSlab, witchwoodSingleSlab, witchwoodDoubleSlab));
		arsMagicaBlocksList.add(witchwoodSingleSlab);
		arsMagicaBlocksList.add(witchwoodDoubleSlab);

		OreDictionary.registerOre("logWood", witchwoodLog);
		//OreDictionary.registerOre("stairWood", witchwoodStairs);
		OreDictionary.registerOre("plankWood", witchwoodPlanks);
		OreDictionary.registerOre("slabWood", witchwoodSingleSlab);
	}

	private <T extends ItemBlock> void registerMultiTextureBlock(Block block, String unlocalizedName, T item){
		arsMagicaBlocksList.add(block);
		try{
			GameData data = ReflectionHelper.getPrivateValue(GameData.class, null, "mainData");
			Method registerBlock = ReflectionHelper.findMethod(GameData.class, data, new String[]{"registerBlock"}, Block.class, String.class);
			Method registerItem = ReflectionHelper.findMethod(GameData.class, data, new String[]{"registerItem"}, Item.class, String.class);
			//register block first
			registerBlock.invoke(data, block, unlocalizedName);
			//register item second
			registerItem.invoke(data, item, unlocalizedName);
		}catch (Throwable t){
			t.printStackTrace();
		}
	}

	private void registerBlock(Block block, String name){
		arsMagicaBlocksList.add(block);
		GameRegistry.registerBlock(block, name);
	}

	private void registerBlock(Block block, Class placerClass, String name){
		arsMagicaBlocksList.add(block);
		GameRegistry.registerBlock(block, placerClass, name);
	}

	public void RegisterTileEntities(){
	}

	public void registerModels(){
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for(Block block : this.getArsMagicaBlocks())
		{
			Item item = Item.getItemFromBlock(block);
			if(block instanceof BlockVariant)
			{
				//This has variants. Register each one.
				for(int i=0; i< ((BlockVariant)block).numVariants(); i++)
				{
					ModelResourceLocation resourceLocation = new ModelResourceLocation(block.getUnlocalizedName() + "." + i, "inventory");
					mesher.register(item, i, resourceLocation);
				}
			}
			else
			{
				//This doesn't have variants. Just register with meta 0
				ModelResourceLocation resourceLocation = new ModelResourceLocation(block.getUnlocalizedName(), "inventory");
				mesher.register(item, 0, resourceLocation);
			}
		}
	}
}
