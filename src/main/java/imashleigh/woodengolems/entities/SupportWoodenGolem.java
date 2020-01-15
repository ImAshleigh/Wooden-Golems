package imashleigh.woodengolems.entities;

import imashleigh.woodengolems.goals.GolemFollowOwnerGoal;
import imashleigh.woodengolems.goals.GolemNearestAttackableTargetExpiringGoal;
import imashleigh.woodengolems.goals.GolemSitGoal;
import imashleigh.woodengolems.lists.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.MoveTowardsVillageGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class SupportWoodenGolem extends AbstractWoodenGolem
{
	   private GolemNearestAttackableTargetExpiringGoal<AbstractWoodenGolem> golemTarget;
	   private GolemNearestAttackableTargetExpiringGoal<PlayerEntity> playerTarget;
	
	@SuppressWarnings("unchecked")
	public SupportWoodenGolem(EntityType<? extends AbstractWoodenGolem> type, World worldIn) 
	{
		super((EntityType<? extends AbstractWoodenGolem>) Entities.SUPPORT_WOODEN_GOLEM, worldIn);
	}
	
	@Override
	   protected void registerGoals() 
	   {
	      this.golemTarget = new GolemNearestAttackableTargetExpiringGoal<>(this, AbstractWoodenGolem.class, true, (targetEntity) -> {
	          return targetEntity != null && targetEntity.getHealth() < 40.0F;
	       });
	      this.playerTarget = new GolemNearestAttackableTargetExpiringGoal<>(this, PlayerEntity.class, true, (targetEntity) -> {
	          return targetEntity != null && targetEntity.getHealth() < 20.0F;
	       });
	      this.targetSelector.addGoal(2, this.golemTarget);
	      this.targetSelector.addGoal(2, this.playerTarget);
		   this.sitGoal = new GolemSitGoal(this);
		   this.goalSelector.addGoal(1, this.sitGoal);
		   this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
		  this.goalSelector.addGoal(4, new GolemFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
		  this.goalSelector.addGoal(0, new SwimGoal(this));
		  this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, MobEntity.class, 3.0F, 1.0D, 1.2D, (avoidEntity) -> {
	          return avoidEntity != null && avoidEntity instanceof IMob;
	       }));
	      this.goalSelector.addGoal(5, new MoveTowardsVillageGoal(this, 0.6D));
	      this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 0.6D, false, 4, () -> {
	         return false;
	      }));
	      this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
	      this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	   }

	@Override
	protected SoundEvent getStepSound() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	   public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		
		         Vec3d vec3d = target.getMotion();
		         double d0 = target.posX + vec3d.x - this.posX;
		         double d1 = target.posY + (double)target.getEyeHeight() - (double)1.1F - this.posY;
		         double d2 = target.posZ + vec3d.z - this.posZ;
		         float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
		         Potion potion = Potions.HEALING;
		         if (target instanceof AbstractWoodenGolem) {
		            if (target.getHealth() < 40.0F) {
		               potion = Potions.HEALING;
		            } 
		            

		            this.setAttackTarget((LivingEntity)null);

		         PotionEntity potionentity = new PotionEntity(this.world, this);
		         potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
		         potionentity.rotationPitch -= -20.0F;
		         potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
		         this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
		         this.world.addEntity(potionentity);
		      
		   }
			       if (target instanceof PlayerEntity) {
			            if (target.getHealth() < 20.0F) {
			               potion = Potions.HEALING;
			            } 
			            
			            this.setAttackTarget((LivingEntity)null);

				         PotionEntity potionentity = new PotionEntity(this.world, this);
				         potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
				         potionentity.rotationPitch -= -20.0F;
				         potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
				         this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
				         this.world.addEntity(potionentity);
		       }
		         
	}
	
	@Override
	   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
	   {
	      super.setEquipmentBasedOnDifficulty(difficulty);
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.SPLASH_POTION));
	   }
	
	@Override
	   public boolean canAttack(EntityType<?> typeIn) 
	   {

		    	  return true;
		      
		      
		 }
	
	@Override
	   public void setCombatTask() 
	   {

	   }
	
	
	
	
	
	

	   
	   
	   
	

	
	
}
