package imashleigh.woodengolems.client.renders;


import imashleigh.woodengolems.client.layers.WoodenGolemHeadLayer;
import imashleigh.woodengolems.client.models.WoodenGolemModel;
import imashleigh.woodengolems.entities.WoodenGolem;
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
public class WoodenGolemRender extends MobRenderer<WoodenGolem, WoodenGolemModel> 
{
	   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("woodengolems:textures/entity/iron_golem.png");

	   public WoodenGolemRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new WoodenGolemModel(), 0F);
		    this.addLayer(new HeldItemLayer<>(this));
		    this.addLayer(new ArrowLayer<>(this));
		    this.addLayer(new WoodenGolemHeadLayer(this));
		    this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
		    this.addLayer(new ElytraLayer<>(this));
	   }

	   protected ResourceLocation getEntityTexture(WoodenGolem entity) {
	      return IRON_GOLEM_TEXTURES;
	   }
	   
	   
	   
	   
		public static class RenderFactory implements IRenderFactory<WoodenGolem>
		{

			@Override
			public EntityRenderer<? super WoodenGolem> createRenderFor(EntityRendererManager manager) 
			{

				return new WoodenGolemRender(manager);
			}
			
			
		}
		


	  

}
