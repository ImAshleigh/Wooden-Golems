package imashleigh.woodengolems.entities;

import imashleigh.woodengolems.lists.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class WoodenGolem extends AbstractWoodenGolem
{



	@SuppressWarnings("unchecked")
	public WoodenGolem(EntityType<? extends AbstractWoodenGolem> type, World worldIn) 
	{
		super((EntityType<? extends AbstractWoodenGolem>) Entities.WOODEN_GOLEM_ENTITY, worldIn);
	}

	@Override
	protected SoundEvent getStepSound() 
	{
		return null;
	}
	
	


}
