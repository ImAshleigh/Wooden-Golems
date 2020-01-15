package imashleigh.woodengolems.client.renders;

import imashleigh.woodengolems.client.layers.WoodSwordWoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.WoodSwordWoodenGolemModel;
import imashleigh.woodengolems.entities.WoodSwordWoodenGolem;
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
public class WoodSwordWoodenGolemRender extends MobRenderer<WoodSwordWoodenGolem, WoodSwordWoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/iron_golem.png");

	   public WoodSwordWoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new WoodSwordWoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new WoodSwordWoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
	   }

	   protected ResourceLocation getEntityTexture(WoodSwordWoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<WoodSwordWoodenGolem>
		{

			@Override
			public EntityRenderer<? super WoodSwordWoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new WoodSwordWoodenGolemRender(manager);
			}
			
			
		}
		
		
		
		

	  

}