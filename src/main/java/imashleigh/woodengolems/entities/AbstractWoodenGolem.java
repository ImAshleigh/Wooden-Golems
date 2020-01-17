package imashleigh.woodengolems.entities;

import javax.annotation.Nullable;

import imashleigh.woodengolems.goals.GolemDefendVillageTargetGoal;
import imashleigh.woodengolems.goals.GolemFollowOwnerGoal;
import imashleigh.woodengolems.goals.GolemOwnerHurtByTargetGoal;
import imashleigh.woodengolems.goals.GolemOwnerHurtTargetGoal;
import imashleigh.woodengolems.goals.GolemSitGoal;
import imashleigh.woodengolems.goals.RangedGolemBowAttackGoal;
import imashleigh.woodengolems.lists.Entities;
import imashleigh.woodengolems.lists.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.MoveTowardsVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractWoodenGolem extends CustomGolem implements IRangedAttackMob 
{
	   private final RangedGolemBowAttackGoal<AbstractWoodenGolem> aiArrowAttack = new RangedGolemBowAttackGoal<>(this, 1.0D, 20, 15.0F);
	   public static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.createKey(AbstractWoodenGolem.class, DataSerializers.FLOAT);
	   private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) 
	   {
	      /**
	       * Reset the task's internal state. Called when this task is interrupted by another one
	       */
	      public void resetTask() 
	      {
	         super.resetTask();
	         AbstractWoodenGolem.this.setAggroed(false);
	      }

	      /**
	       * Execute a one shot task or start executing a continuous task
	       */
	      public void startExecuting() 
	      {
	         super.startExecuting();
	         AbstractWoodenGolem.this.setAggroed(true);
	      }
	   };

	   protected AbstractWoodenGolem(EntityType<? extends AbstractWoodenGolem> type, World worldIn) 
	   {
	      super(type, worldIn);
	      this.setCombatTask();
	   }
	   

	   protected void registerGoals() 
	   {
		   this.sitGoal = new GolemSitGoal(this);
		   this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractWoodenGolem.class)).setCallsForHelp());
		   this.goalSelector.addGoal(1, this.sitGoal);
		  this.goalSelector.addGoal(4, new GolemFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
	      this.targetSelector.addGoal(1, new GolemOwnerHurtByTargetGoal(this));
	      this.targetSelector.addGoal(2, new GolemOwnerHurtTargetGoal(this));
		  this.goalSelector.addGoal(0, new SwimGoal(this));
		  this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, CreeperEntity.class, 6.0F, 1.0D, 1.2D));
	      this.goalSelector.addGoal(5, new MoveTowardsVillageGoal(this, 0.6D));
	      this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 0.6D, false, 4, () -> {
	         return false;
	      }));
	      this.targetSelector.addGoal(1, new GolemDefendVillageTargetGoal(this));
	      this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
	      this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_213619_0_) -> {
	          return p_213619_0_ instanceof IMob;
	       }));
	   }

	   protected void registerAttributes() 
	   {
	      super.registerAttributes();
	      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);;
	   }

	   protected void playStepSound(BlockPos pos, BlockState blockIn) 
	   {
	      this.playSound(this.getStepSound(), 0.15F, 1.0F);
	   }

	   protected abstract SoundEvent getStepSound();

	   public CreatureAttribute getCreatureAttribute() 
	   {
	      return CreatureAttribute.UNDEFINED;
	   }

	   /**
	    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	    * use this to react to sunlight and start to burn.
	    */
	   public void livingTick() 
	   {

	      super.livingTick();
	      playHurtEffect();
	   }

	   /**
	    * Handles updating while riding another entity
	    */
	   public void updateRidden() 
	   {
	      super.updateRidden();
	      if (this.getRidingEntity() instanceof CreatureEntity) 
	      {
	         CreatureEntity creatureentity = (CreatureEntity)this.getRidingEntity();
	         this.renderYawOffset = creatureentity.renderYawOffset;
	      }

	   }


	   
	   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
	   {
	      super.setEquipmentBasedOnDifficulty(difficulty);
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
	   }

	   @Nullable
	   public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	      spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	      this.setEquipmentBasedOnDifficulty(difficultyIn);
	      this.setCombatTask();

	      

	      return spawnDataIn;
	   }

	   /**
	    * sets this entity's combat AI.
	    */
	   public void setCombatTask() 
	   {
	      if (this.world != null && !this.world.isRemote) 
	      {
	         this.goalSelector.removeGoal(this.aiAttackOnCollide);
	         this.goalSelector.removeGoal(this.aiArrowAttack);
	         ItemStack itemstack = this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW));
	         if (itemstack.getItem() instanceof net.minecraft.item.BowItem) 
	         {
	            int i = 35;

	            this.aiArrowAttack.setAttackCooldown(i);
	            this.goalSelector.addGoal(3, this.aiArrowAttack);
	         } 
	         else 
	         {
	            this.goalSelector.addGoal(3, this.aiAttackOnCollide);
	         }

	      }
	   }


	   //Skeleton ranged attack
	   public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) 
	   {
	      ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW)));
	      AbstractArrowEntity abstractarrowentity = this.fireArrow(itemstack, distanceFactor);
	      if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
	         abstractarrowentity = ((net.minecraft.item.BowItem)this.getHeldItemMainhand().getItem()).customeArrow(abstractarrowentity);
	      double d0 = target.posX - this.posX;
	      double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - abstractarrowentity.posY;
	      double d2 = target.posZ - this.posZ;
	      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	      abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
	      this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	      this.world.addEntity(abstractarrowentity);
	   }

	   //Skeleton ranged attack
	   protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) 
	   {
	      return ProjectileHelper.fireArrow(this, arrowStack, distanceFactor);
	   }

	   /**
	    * (abstract) Protected helper method to read subclass entity data from NBT.
	    */
	   public void readAdditional(CompoundNBT compound) 
	   {
	      super.readAdditional(compound);
	      this.setCombatTask();
	   }

	   public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) 
	   {
	      super.setItemStackToSlot(slotIn, stack);
	      if (!this.world.isRemote) 
	      {
	         this.setCombatTask();
	      }

	   }

	   protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) 
	   {
	      return 1.74F;
	   }

	   /**
	    * Returns the Y Offset of this entity.
	    */
	   public double getYOffset() 
	   {
	      return -0.6D;
	   }
	   
	   public boolean canDespawn(double distanceToClosestPlayer) 
	   {
		      return false;
	   }
	   
	   protected void registerData() 
	   {
		      super.registerData();
		      this.dataManager.register(DATA_HEALTH_ID, this.getHealth());
	   }
	   
	   public boolean canAttack(EntityType<?> typeIn) 
	   {
		      if (
		    		  typeIn == EntityType.PLAYER 
		    		  || typeIn == Entities.WOODEN_GOLEM_ENTITY 
		    		  || typeIn == Entities.SWORD_WOODEN_GOLEM_ENTITY 
		    		  || typeIn == Entities.WOOD_SWORD_WOODEN_GOLEM_ENTITY
		    		  || typeIn == Entities.IRON_SWORD_WOODEN_GOLEM_ENTITY
		    		  || typeIn == Entities.GOLDEN_SWORD_WOODEN_GOLEM_ENTITY
		    		  || typeIn == Entities.DIAMOND_SWORD_WOODEN_GOLEM_ENTITY
		    		  || typeIn == Entities.SUPPORT_WOODEN_GOLEM
		    		
		    	  ) 
		      {
		         return false;
		      } 
		      else
		      {
		    	  return true;
		      }
		      
		   }
	   
	   public void writeAdditional(CompoundNBT compound) 
	   {
		      super.writeAdditional(compound);
		   }
	   
		   
		   
		   public boolean canBeLeashedTo(PlayerEntity player) 
		   {
			      return !this.getLeashed();
		   }
		   
		   @Override
		   public boolean isPreventingPlayerRest(PlayerEntity playerIn) 
		   {
			      return false;
		   }
		   
		   protected void updateAITasks() 
		   {
			      this.dataManager.set(DATA_HEALTH_ID, this.getHealth());
		   }
		   
		   public boolean processInteract(PlayerEntity player, Hand hand) 
		   {
			      ItemStack itemstack = player.getHeldItem(hand);
			      //EnchantmentHelper.getEnchantments(itemstack);
			      Item item = itemstack.getItem();
			      
		            if (player.isSneaking())
		            {
		            	if(this instanceof SupportWoodenGolem)
		            	{
			            	dropInventoryS();
			            	this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(null));
			            	this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(null));
			            	this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(null));
		            	}
		            	else
		            	{
			            	dropInventory();
			            	this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(null));
			            	this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(null));
			            	this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(null));
			            	this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(null));
		            	}

		            }
			      
			         if (!itemstack.isEmpty()) 
			         {
			            if (item == ItemList.wood_golem_food) 
			            {
			               if (this.dataManager.get(DATA_HEALTH_ID) < 40.0F) 
			               {
			                  if (!player.abilities.isCreativeMode) 
			                  {
			                     itemstack.shrink(1);
			                  }

			                  this.heal(40F);
			                  playEatEffect();
			                  return true;
			               }
			            }
			            
			            
			            if(!player.isSneaking())
			            {
			            	if(this instanceof SupportWoodenGolem)
			            	{
			            		
			            	}
			            	else
			            	{
				            	//Weapons
					            if (item == Items.WOODEN_SWORD) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
					            if (item == Items.STONE_SWORD) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
					            if (item == Items.IRON_SWORD) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
					            if (item == Items.GOLDEN_SWORD) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
					            if (item == Items.DIAMOND_SWORD) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
					            if (item == Items.BOW) 
					            {
					            	swapMainHand(player, itemstack, item);
						        }
			            		
			            	}
				            
				            
				            //Armour          
				            //Leather
				            if (item == Items.LEATHER_CHESTPLATE)
				            {		      
				            	swapChest(player, itemstack, item);
				            }
				            if (item == Items.LEATHER_LEGGINGS)
				            {
				            	swapLeggings(player, itemstack, item);
				            }
				            if (item == Items.LEATHER_BOOTS)
				            {
				            	swapBoots(player, itemstack, item);
				            }
				            //IRON
				            if (item == Items.IRON_CHESTPLATE)
				            {
				            	swapChest(player, itemstack, item);
				            }
				            if (item == Items.IRON_LEGGINGS)
				            {
				            	swapLeggings(player, itemstack, item);
				            }
				            if (item == Items.IRON_BOOTS)
				            {
				            	swapBoots(player, itemstack, item);
				            }
				            
				            //GOLDEN
				            if (item == Items.GOLDEN_CHESTPLATE)
				            {
				            	swapChest(player, itemstack, item);
				            }
				            if (item == Items.GOLDEN_LEGGINGS)
				            {
				            	swapLeggings(player, itemstack, item);
				            }
				            if (item == Items.GOLDEN_BOOTS)
				            {
				            	swapBoots(player, itemstack, item);
				            }
				            
				            //CHAIN
				            if (item == Items.CHAINMAIL_CHESTPLATE)
				            {
				            	swapChest(player, itemstack, item);
				            }
				            if (item == Items.CHAINMAIL_LEGGINGS)
				            {
				            	swapLeggings(player, itemstack, item);
				            }
				            if (item == Items.CHAINMAIL_BOOTS)
				            {
				            	swapBoots(player, itemstack, item);
				            }
				            
				            //DIAMOND
				            if (item == Items.DIAMOND_CHESTPLATE)
				            {
				            	swapChest(player, itemstack, item);
				            }
				            if (item == Items.DIAMOND_LEGGINGS)
				            {
				            	swapLeggings(player, itemstack, item);
				            }
				            if (item == Items.DIAMOND_BOOTS)
				            {
				            	swapBoots(player, itemstack, item);
				            }
			            }
			            
			            		            
			            //Taming
			            if (item == ItemList.control_rod) 
			            {

			                if (!this.world.isRemote) 
			                {
			                	 if (!this.isOwner(player)) 
			                	 {
					                	
					                    this.setTamedBy(player);
					                    this.navigator.clearPath();
					                    this.setAttackTarget((LivingEntity)null);
					                    this.world.setEntityState(this, (byte)7);
					                    this.sitGoal.setSitting(false);
					                    playControlEffect();
			                	 }
			                	 if(this.isOwner(player))
			                	 {
			                		 if(this.isSitting() == true )
			                		 {
			 			                this.sitGoal.setSitting(false);
			 			               playControlEffect();

			                		 }
			                		 else
			                		 {
			 			                this.sitGoal.setSitting(true);
						                this.navigator.clearPath();
						                this.setAttackTarget((LivingEntity)null);	
						                playControlEffect();
			                		 }
			                	 }
			                }
			                	
			                
			                

			                playControlEffect();
			                return true;
			             }

			            
			           		            
			            

			}
			         
			         
			          
			         


			      

			      return super.processInteract(player, hand);
			   }
		   
		   
		   
		   protected void playEatEffect() 
		   {
			      IParticleData iparticledata = ParticleTypes.HEART;
			      for(int i = 0; i < 7; ++i) 
			      {
			         double d0 = this.rand.nextGaussian() * 0.02D;
			         double d1 = this.rand.nextGaussian() * 0.02D;
			         double d2 = this.rand.nextGaussian() * 0.02D;
			         this.world.addParticle(iparticledata, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
			      }

			   }
		   
		   protected void playControlEffect() {
			      IParticleData iparticledata = ParticleTypes.SMOKE;
			      for(int i = 0; i < 7; ++i) {
			         double d0 = this.rand.nextGaussian() * 0.02D;
			         double d1 = this.rand.nextGaussian() * 0.02D;
			         double d2 = this.rand.nextGaussian() * 0.02D;
			         this.world.addParticle(iparticledata, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
			      }

			   }
		   
		   protected void playHurtEffect() 
		   
		   {
			   if(this.dataManager.get(DATA_HEALTH_ID) < 10.0F)
			   {
			      IParticleData iparticledata = ParticleTypes.SMOKE;
			      for(int i = 0; i < 7; ++i) {
			         double d0 = this.rand.nextGaussian() * 0.02D;
			         double d1 = this.rand.nextGaussian() * 0.02D;
			         double d2 = this.rand.nextGaussian() * 0.02D;
			         this.world.addParticle(iparticledata, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
			      }

			   }
		   }
		   
		   protected void dropInventory() 
		   {
			   ItemStack mainHand = getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			   ItemStack chest = getItemStackFromSlot(EquipmentSlotType.CHEST);
			   ItemStack legs = getItemStackFromSlot(EquipmentSlotType.LEGS);
			   ItemStack boots = getItemStackFromSlot(EquipmentSlotType.FEET);
			   this.entityDropItem(mainHand);
			   this.entityDropItem(chest);
			   this.entityDropItem(legs);
			   this.entityDropItem(boots);
			  
		   }
		   
		   public void swapMainHand(PlayerEntity player1, ItemStack itemstack1, IItemProvider item1)
		   {
           		ItemStack dropItem = getItemStackFromSlot(EquipmentSlotType.MAINHAND);
           		this.entityDropItem(dropItem);
           		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
               
           		if (!player1.abilities.isCreativeMode) {
                    itemstack1.shrink(1);
                 }
			   
		   }
		   
		   
		   public void swapChest(PlayerEntity player1, ItemStack itemstack1, IItemProvider item1)
		   {
           		ItemStack dropItem = getItemStackFromSlot(EquipmentSlotType.CHEST);
           		this.entityDropItem(dropItem);
		    
           		this.setItemStackToSlot(EquipmentSlotType.CHEST, itemstack1);
               
           		if (!player1.abilities.isCreativeMode) {
                    itemstack1.shrink(1);
                 }
			   
		   }
		   
		   public void swapLeggings(PlayerEntity player1, ItemStack itemstack1, IItemProvider item1)
		   {
           		ItemStack dropItem = getItemStackFromSlot(EquipmentSlotType.LEGS);
           		this.entityDropItem(dropItem);
		    
           		this.setItemStackToSlot(EquipmentSlotType.LEGS, itemstack1);
               
           		if (!player1.abilities.isCreativeMode) {
                    itemstack1.shrink(1);
                 }
			   
		   }
		   
		   public void swapBoots(PlayerEntity player1, ItemStack itemstack1, IItemProvider item1)
		   {
           		ItemStack dropItem = getItemStackFromSlot(EquipmentSlotType.FEET);
           		this.entityDropItem(dropItem);
		    
           		this.setItemStackToSlot(EquipmentSlotType.FEET, itemstack1);
               
           		if (!player1.abilities.isCreativeMode) {
                    itemstack1.shrink(1);
                 }
			   
		   }
		   
		   protected void dropInventoryS() 
		   {
			   ItemStack chest = getItemStackFromSlot(EquipmentSlotType.CHEST);
			   ItemStack legs = getItemStackFromSlot(EquipmentSlotType.LEGS);
			   ItemStack boots = getItemStackFromSlot(EquipmentSlotType.FEET);
			   this.entityDropItem(chest);
			   this.entityDropItem(legs);
			   this.entityDropItem(boots);
			  
		   }
		   
		   
		   


}
