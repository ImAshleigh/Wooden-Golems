package imashleigh.woodengolems.client.renders;

import imashleigh.woodengolems.client.layers.GoldSwordWoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.GoldSwordWoodenGolemModel;
import imashleigh.woodengolems.entities.GoldSwordWoodenGolem;
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
public class GoldSwordWoodenGolemRender extends MobRenderer<GoldSwordWoodenGolem, GoldSwordWoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/iron_golem.png");

	   public GoldSwordWoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new GoldSwordWoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new GoldSwordWoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
		    this.addLayer(new ElytraLayer<>(this));
	   }

	   protected ResourceLocation getEntityTexture(GoldSwordWoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<GoldSwordWoodenGolem>
		{

			@Override
			public EntityRenderer<? super GoldSwordWoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new GoldSwordWoodenGolemRender(manager);
			}
			
			
		}
		
		
		
		

	  

}