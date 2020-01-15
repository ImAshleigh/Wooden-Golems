package imashleigh.woodengolems.entities;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import imashleigh.woodengolems.goals.GolemSitGoal;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

//Custom MobEntity class needed to remove IMob to prevent Iron Golems from attacking Wooden Golems
public abstract class CustomGolem extends CreatureEntity {
	   protected static final DataParameter<Byte> TAMED = EntityDataManager.createKey(TameableEntity.class, DataSerializers.BYTE);
	   protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	   protected GolemSitGoal sitGoal;

   public CustomGolem(EntityType<? extends CustomGolem> type, World worldIn) {
      super(type, worldIn);
      this.setupTamedAI();
      this.experienceValue = 5;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      this.updateArmSwingProgress();
      this.func_213623_ec();
      super.livingTick();
   }

   protected void func_213623_ec() {
      float f = this.getBrightness();
      if (f > 0.5F) {
         this.idleTime += 2;
      }

   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();

   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_HOSTILE_SWIM;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_HOSTILE_SPLASH;
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return this.isInvulnerableTo(source) ? false : super.attackEntityFrom(source, amount);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_HOSTILE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_HOSTILE_DEATH;
   }

   protected SoundEvent getFallSound(int heightIn) {
      return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
   }

   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      return 0.5F - worldIn.getBrightness(pos);
   }

   public static boolean isValidLightLevel(IWorld worldIn, BlockPos pos, Random randomIn) {
      if (worldIn.getLightFor(LightType.SKY, pos) > randomIn.nextInt(32)) {
         return false;
      } else {
         int i = worldIn.getWorld().isThundering() ? worldIn.getNeighborAwareLightSubtracted(pos, 10) : worldIn.getLight(pos);
         return i <= randomIn.nextInt(8);
      }
   }


   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
   }

   /**
    * Entity won't drop items or experience points if this returns false
    */
   protected boolean canDropLoot() {
      return false;
   }

   public boolean isPreventingPlayerRest(PlayerEntity playerIn) {
      return false;
   }

   public ItemStack findAmmo(ItemStack shootable) {
      if (shootable.getItem() instanceof ShootableItem) {
         Predicate<ItemStack> predicate = ((ShootableItem)shootable.getItem()).getAmmoPredicate();
         ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
         return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
      } else {
         return ItemStack.EMPTY;
      }
   }
   
   @Nullable
   public LivingEntity getOwner() {
      try {
         UUID uuid = this.getOwnerId();
         return uuid == null ? null : this.world.getPlayerByUuid(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean canAttack(LivingEntity target) {
      return this.isOwner(target) ? false : super.canAttack(target);
   }

   public boolean isOwner(LivingEntity entityIn) {
      return entityIn == this.getOwner();
   }
   
   @Nullable
   public UUID getOwnerId() {
      return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID)null);
   }

   public void setOwnerId(@Nullable UUID p_184754_1_) {
      this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
   }

   public void setTamedBy(PlayerEntity player) {
      this.setTamed(true);
      this.setOwnerId(player.getUniqueID());
   }
      
      public boolean isTamed() {
          return (this.dataManager.get(TAMED) & 4) != 0;
       }

       public void setTamed(boolean tamed) {
          byte b0 = this.dataManager.get(TAMED);
          if (tamed) {
             this.dataManager.set(TAMED, (byte)(b0 | 4));
          } else {
             this.dataManager.set(TAMED, (byte)(b0 & -5));
          }

          this.setupTamedAI();
       }

       protected void setupTamedAI() {
       }
       
       public void readAdditional(CompoundNBT compound) {
    	      super.readAdditional(compound);
    	      String s;
    	      if (compound.contains("OwnerUUID", 8)) {
    	         s = compound.getString("OwnerUUID");
    	      } else {
    	         String s1 = compound.getString("Owner");
    	         s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
    	      }

    	      if (!s.isEmpty()) {
    	         try {
    	            this.setOwnerId(UUID.fromString(s));
    	            this.setTamed(true);
    	         } catch (Throwable var4) {
    	            this.setTamed(false);
    	         }
    	      }
    	      
    	      if (this.sitGoal != null) {
    	          this.sitGoal.setSitting(compound.getBoolean("Sitting"));
    	       }
    	      }
    	      
    	      protected void registerData() {
    	          super.registerData();
    	          this.dataManager.register(TAMED, (byte)0);
    	          this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    	       }

    	       public void writeAdditional(CompoundNBT compound) {
    	          super.writeAdditional(compound);
    	          if (this.getOwnerId() == null) {
    	             compound.putString("OwnerUUID", "");
    	          } else {
    	             compound.putString("OwnerUUID", this.getOwnerId().toString());
    	          }
    	          
    	          compound.putBoolean("Sitting", this.isSitting());

    	       }
    	       
    	       public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
    	    	      return true;
    	    	   }
    	       
    	       public boolean isSitting() {
    	    	      return (this.dataManager.get(TAMED) & 1) != 0;
    	    	   }

    	    	   public void setSitting(boolean sitting) {
    	    	      byte b0 = this.dataManager.get(TAMED);
    	    	      if (sitting) {
    	    	         this.dataManager.set(TAMED, (byte)(b0 | 1));
    	    	      } else {
    	    	         this.dataManager.set(TAMED, (byte)(b0 & -2));
    	    	      }

    	    	   }

}
