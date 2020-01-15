package imashleigh.woodengolems.goals;

import java.util.EnumSet;

import imashleigh.woodengolems.entities.CustomGolem;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class GolemOwnerHurtByTargetGoal extends TargetGoal {
	   private final CustomGolem tameable;
	   private LivingEntity attacker;
	   private int timestamp;

	   public GolemOwnerHurtByTargetGoal(CustomGolem theDefendingTameableIn) {
	      super(theDefendingTameableIn, false);
	      this.tameable = theDefendingTameableIn;
	      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
	   }

	   /**
	    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	    * method as well.
	    */
	   public boolean shouldExecute() {
	      if (this.tameable.isTamed() && !this.tameable.isSitting()) {
	         LivingEntity livingentity = this.tameable.getOwner();
	         if (livingentity == null) {
	            return false;
	         } else {
	            this.attacker = livingentity.getRevengeTarget();
	            int i = livingentity.getRevengeTimer();
	            return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingentity);
	         }
	      } else {
	         return false;
	      }
	   }

	   /**
	    * Execute a one shot task or start executing a continuous task
	    */
	   public void startExecuting() {
	      this.goalOwner.setAttackTarget(this.attacker);
	      LivingEntity livingentity = this.tameable.getOwner();
	      if (livingentity != null) {
	         this.timestamp = livingentity.getRevengeTimer();
	      }

	      super.startExecuting();
	   }
	}