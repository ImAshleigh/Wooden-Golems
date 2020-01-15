package imashleigh.woodengolems.client.models;

import imashleigh.woodengolems.entities.SupportWoodenGolem;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SupportWoodenGolemModel extends SkeletonModel<SupportWoodenGolem> {
	
	public SupportWoodenGolemModel()
	{
		super();
	}
	
	   public RendererModel func_205070_a() {
		      return this.bipedHead;
		   }
	
	
}