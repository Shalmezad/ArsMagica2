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
	public static Block obelisk;
	public static Block calefactor;
	public static BlockAMOre AMOres;
	public static Block magicWall;
	public static Block occulus;
	public static BlockWitchwoodLog witchwoodLog;
	public static WitchwoodPlanks witchwoodPlanks;
	public static WitchwoodSlabs witchwoodSingleSlab;
	public static WitchwoodSlabs witchwoodDoubleSlab;

	//--------------------------------------------------------------
	// End Blocks
	//---------------------------------------------------------------

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

	public void InstantiateBlocks(){
		AMOres = (BlockAMOre)new BlockAMOre().setRegistryName("arsmagica2:ores").setCreativeTab(blockTab);
		witchwoodLog = (BlockWitchwoodLog)new BlockWitchwoodLog().setRegistryName("arsmagica2:witchwoodlog").setCreativeTab(blockTab);
		witchwoodPlanks = (WitchwoodPlanks)new WitchwoodPlanks().setRegistryName("arsmagica2:planksWitchwood").setCreativeTab(blockTab);
		witchwoodSingleSlab = (WitchwoodSlabs)new WitchwoodSlabs(false).setRegistryName("arsmagica2:witchwoodSingleSlab").setCreativeTab(blockTab);
		witchwoodDoubleSlab = (WitchwoodSlabs)new WitchwoodSlabs(true).setRegistryName("arsmagica2:witchwoodDoubleSlab");
		blockTab.setIconItemIndex(Items.ender_eye);
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
