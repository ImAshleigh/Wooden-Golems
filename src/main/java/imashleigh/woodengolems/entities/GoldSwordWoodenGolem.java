package imashleigh.woodengolems.entities;

import imashleigh.woodengolems.lists.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class GoldSwordWoodenGolem extends AbstractWoodenGolem
{

	@SuppressWarnings("unchecked")
	public GoldSwordWoodenGolem(EntityType<? extends AbstractWoodenGolem> type, World worldIn) 
	{
		super((EntityType<? extends AbstractWoodenGolem>) Entities.GOLDEN_SWORD_WOODEN_GOLEM_ENTITY, worldIn);
	}

	@Override
	protected SoundEvent getStepSound() 
	{
		
		return null;
	}
	
	@Override
	   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
	      super.setEquipmentBasedOnDifficulty(difficulty);
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	   }
	

}

