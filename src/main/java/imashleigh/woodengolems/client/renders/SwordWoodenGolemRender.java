package imashleigh.woodengolems.client.renders;

import imashleigh.woodengolems.client.layers.SwordWoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.SwordWoodenGolemModel;
import imashleigh.woodengolems.entities.SwordWoodenGolem;
import imashleigh.woodengolems.entities.WoodenGolem;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
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
public class SwordWoodenGolemRender extends MobRenderer<SwordWoodenGolem, SwordWoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/iron_golem.png");

	   public SwordWoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new SwordWoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new SwordWoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
	   }

	   protected ResourceLocation getEntityTexture(SwordWoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<SwordWoodenGolem>
		{

			@Override
			public EntityRenderer<? super SwordWoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new SwordWoodenGolemRender(manager);
			}
			
			
		}
		

		
		
		

	  

}