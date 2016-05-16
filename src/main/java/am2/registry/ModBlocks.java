package am2.registry;

import am2.AMCore;
import am2.blocks.BlockWitchwoodLog;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks{

	public static Block witchwoodLog;

	public static void createBlocks()
	{
		witchwoodLog = new BlockWitchwoodLog();
		GameRegistry.registerBlock(witchwoodLog,"WitchwoodLog");
	}

	public static void registerBlockModels()
	{
		registerBlockModel(witchwoodLog);
	}

	private static void registerBlockModel(Block block)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		RenderItem renderItem = minecraft.getRenderItem();
		ItemModelMesher itemModelMesher = renderItem.getItemModelMesher();

		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation modelResourceLocation;
		modelResourceLocation = new ModelResourceLocation(AMCore.MOD_ID + ":" + block.getUnlocalizedName().substring(5), "inventory");
		itemModelMesher.register(item, 0, modelResourceLocation);
	}

}
