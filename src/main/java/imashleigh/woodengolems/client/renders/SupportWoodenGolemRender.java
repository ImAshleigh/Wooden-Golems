package imashleigh.woodengolems.client.renders;

import imashleigh.woodengolems.client.layers.SupportWoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.SupportWoodenGolemModel;
import imashleigh.woodengolems.entities.SupportWoodenGolem;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SupportWoodenGolemRender extends MobRenderer<SupportWoodenGolem, SupportWoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/support.png");

	   public SupportWoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new SupportWoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new SupportWoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
		    this.addLayer(new ElytraLayer<>(this));
	   }

	   protected ResourceLocation getEntityTexture(SupportWoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<SupportWoodenGolem>
		{

			@Override
			public EntityRenderer<? super SupportWoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new SupportWoodenGolemRender(manager);
			}
			
			
		}
		

		
		
		

	  

}