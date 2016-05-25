package am2.registry;

import am2.AMCore;
import am2.blocks.BlockAMOre;
import am2.blocks.BlockWitchwoodLog;
import am2.blocks.core.BlockVariant;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks{

	public static Block witchwoodLog;
	public static Block amOre;

	public static void createBlocks()
	{
		witchwoodLog = new BlockWitchwoodLog();
		GameRegistry.registerBlock(witchwoodLog);
		amOre = new BlockAMOre();
		GameRegistry.registerBlock(amOre);
	}

	public static void registerBlockModels()
	{
		registerBlockModel(witchwoodLog);
		registerBlockModel(amOre);
	}

	private static void registerBlockModel(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation modelResourceLocation;
		modelResourceLocation = new ModelResourceLocation(block.getRegistryName(), "inventory");
		if(block instanceof BlockVariant)
		{
			for(int i=0; i<((BlockVariant)block).numVariants(); i++)
			{
				ModelLoader.setCustomModelResourceLocation(item, i, modelResourceLocation);
			}
		}
		else
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
		}
	}

}
