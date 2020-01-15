package imashleigh.woodengolems.client.models;

import imashleigh.woodengolems.entities.WoodenGolem;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WoodenGolemModel extends SkeletonModel<WoodenGolem> {
	
	public WoodenGolemModel()
	{
		super();
	}
	
	   public RendererModel func_205070_a() {
		      return this.bipedHead;
		   }
	
	
}

