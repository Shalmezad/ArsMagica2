package thehippomaster.AnimationExample;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = "AnimationExample", name = "Animation Example", version = "1.0.0")
public class AnimationExample {
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		EntityRegistry.registerGlobalEntityID(EntityTest.class, "EntityTest", 106, 0, 0);
		
		proxy.registerRenderers();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
	
	@Mod.Instance("AnimationExample")
	public static AnimationExample instance;
	@SidedProxy(clientSide="thehippomaster.AnimationExample.client.ClientProxy", serverSide="thehippomaster.AnimationExample.CommonProxy")
	public static CommonProxy proxy;
}
