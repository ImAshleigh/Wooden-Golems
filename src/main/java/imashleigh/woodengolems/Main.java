package imashleigh.woodengolems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import imashleigh.woodengolems.client.renders.RenderRegistry;
import imashleigh.woodengolems.lists.Entities;
import imashleigh.woodengolems.lists.ItemList;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("woodengolems")
public class Main 
{
	public static Main instance;
	public static final String modid = "woodengolems";
	private static final Logger logger = LogManager.getLogger(modid);
	
	public Main()
	{
		instance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		logger.info("Setup method registered");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		RenderRegistry.registerEntityRenders();
		logger.info("Client method registered");
	}
	
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll
			(
					
					ItemList.wood_golem_food = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("wood_golem_food")),
					ItemList.control_rod = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("control_rod"))
			
			);
			
			Entities.registerEntitySpawnEggs(event);
			
			logger.info("Items registered.");
		}
		
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event)
		{
			event.getRegistry().registerAll
			(
					
			);
			
			logger.info("Blocks registered.");
		}
		
		@SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event)
		{
			event.getRegistry().registerAll
			(
					Entities.WOODEN_GOLEM_ENTITY,
					Entities.SWORD_WOODEN_GOLEM_ENTITY,
					Entities.WOOD_SWORD_WOODEN_GOLEM_ENTITY,
					Entities.IRON_SWORD_WOODEN_GOLEM_ENTITY,
					Entities.GOLDEN_SWORD_WOODEN_GOLEM_ENTITY,
					Entities.DIAMOND_SWORD_WOODEN_GOLEM_ENTITY,
					Entities.SUPPORT_WOODEN_GOLEM
			);
			
			Entities.registerEntityWorldSpawns();
			
			
		}
		
		
		
		public static ResourceLocation location(String name)
		{
			return new ResourceLocation(modid, name);
		}
	}
	
	




}
