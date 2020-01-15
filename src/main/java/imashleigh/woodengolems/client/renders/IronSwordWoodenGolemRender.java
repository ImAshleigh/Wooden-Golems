package imashleigh.woodengolems.client.renders;

import imashleigh.woodengolems.client.layers.IronSwordWoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.IronSwordWoodenGolemModel;
import imashleigh.woodengolems.entities.IronSwordWoodenGolem;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class IronSwordWoodenGolemRender extends MobRenderer<IronSwordWoodenGolem, IronSwordWoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/iron_golem.png");

	   public IronSwordWoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new IronSwordWoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new IronSwordWoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
	   }

	   protected ResourceLocation getEntityTexture(IronSwordWoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<IronSwordWoodenGolem>
		{

			@Override
			public EntityRenderer<? super IronSwordWoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new IronSwordWoodenGolemRender(manager);
			}
			
			
		}

		
		
		

	  

}