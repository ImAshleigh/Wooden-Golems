package imashleigh.woodengolems.lists;

import imashleigh.woodengolems.Main;
import imashleigh.woodengolems.Main.RegistryEvents;
import imashleigh.woodengolems.entities.DiamondSwordWoodenGolem;
import imashleigh.woodengolems.entities.GoldSwordWoodenGolem;
import imashleigh.woodengolems.entities.IronSwordWoodenGolem;
import imashleigh.woodengolems.entities.SwordWoodenGolem;
import imashleigh.woodengolems.entities.WoodSwordWoodenGolem;
import imashleigh.woodengolems.entities.WoodenGolem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.RegistryEvent;

public class Entities 
{
	public static EntityType<?> WOODEN_GOLEM_ENTITY = EntityType.Builder.create(WoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":wooden_golem_entity").setRegistryName(RegistryEvents.location("wooden_golem_entity"));
	public static EntityType<?> SWORD_WOODEN_GOLEM_ENTITY = EntityType.Builder.create(SwordWoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":sword_wooden_golem_entity").setRegistryName(RegistryEvents.location("sword_wooden_golem_entity"));
	public static EntityType<?> WOOD_SWORD_WOODEN_GOLEM_ENTITY = EntityType.Builder.create(WoodSwordWoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":wood_sword_wooden_golem_entity").setRegistryName(RegistryEvents.location("wood_sword_wooden_golem_entity"));
	public static EntityType<?> IRON_SWORD_WOODEN_GOLEM_ENTITY = EntityType.Builder.create(IronSwordWoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":iron_sword_wooden_golem_entity").setRegistryName(RegistryEvents.location("iron_sword_wooden_golem_entity"));
	public static EntityType<?> GOLDEN_SWORD_WOODEN_GOLEM_ENTITY = EntityType.Builder.create(GoldSwordWoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":gold_sword_wooden_golem_entity").setRegistryName(RegistryEvents.location("gold_sword_wooden_golem_entity"));
	public static EntityType<?> DIAMOND_SWORD_WOODEN_GOLEM_ENTITY = EntityType.Builder.create(DiamondSwordWoodenGolem::new, EntityClassification.MONSTER).build(Main.modid +":diamond_sword_wooden_golem_entity").setRegistryName(RegistryEvents.location("diamond_sword_wooden_golem_entity"));

	
	public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll
		(
				ItemList.wooden_golem_entity_egg = registerEntitySpawnEgg(WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "wooden_golem_entity_egg"),
				ItemList.sword_wooden_golem_entity_egg = registerEntitySpawnEgg(SWORD_WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "sword_wooden_golem_entity_egg"),
				ItemList.wood_sword_wooden_golem_entity = registerEntitySpawnEgg(WOOD_SWORD_WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "wood_sword_wooden_golem_entity_egg"),
				ItemList.iron_sword_wooden_golem_entity = registerEntitySpawnEgg(IRON_SWORD_WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "iron_sword_wooden_golem_entity_egg"),
				ItemList.gold_sword_wooden_golem_entity_egg = registerEntitySpawnEgg(GOLDEN_SWORD_WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "gold_sword_wooden_golem_entity_egg"),
				ItemList.diamond_sword_wooden_golem_entity_egg = registerEntitySpawnEgg(DIAMOND_SWORD_WOODEN_GOLEM_ENTITY, 0xf7f7f7, 0xcfbc9d, "diamond_sword_wooden_golem_entity_egg")
		);
	}
	
	public static void registerEntityWorldSpawns()
	{
	}
	
	public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
	{
		SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(ItemGroup.MISC));
		item.setRegistryName(RegistryEvents.location(name));
		return item;
		
	}
	
	public static void registerEntityWorldSpawn(EntityType<?> entity, Biome...biomes)
	{
		for(Biome biome : biomes)
		{
			if(biome != null)
			{
				biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(entity, 1, 1, 4));
			}
		}
	}

}
