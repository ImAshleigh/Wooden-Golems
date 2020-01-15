package imashleigh.woodengolems.client.models;

import imashleigh.woodengolems.entities.DiamondSwordWoodenGolem;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DiamondSwordWoodenGolemModel extends SkeletonModel<DiamondSwordWoodenGolem> {
	
	public DiamondSwordWoodenGolemModel()
	{
		super();
	}
	
	   public RendererModel func_205070_a() {
		      return this.bipedHead;
		   }
	
	
}
