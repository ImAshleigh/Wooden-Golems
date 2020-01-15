package imashleigh.woodengolems.client.renders;


import imashleigh.woodengolems.entities.DiamondSwordWoodenGolem;
import imashleigh.woodengolems.entities.GoldSwordWoodenGolem;
import imashleigh.woodengolems.entities.IronSwordWoodenGolem;
import imashleigh.woodengolems.entities.SupportWoodenGolem;
import imashleigh.woodengolems.entities.SwordWoodenGolem;
import imashleigh.woodengolems.entities.WoodSwordWoodenGolem;
import imashleigh.woodengolems.entities.WoodenGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry 
{
	
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(WoodenGolem.class, new WoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(SwordWoodenGolem.class, new SwordWoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(WoodSwordWoodenGolem.class, new WoodSwordWoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(IronSwordWoodenGolem.class, new IronSwordWoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(GoldSwordWoodenGolem.class, new GoldSwordWoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(DiamondSwordWoodenGolem.class, new DiamondSwordWoodenGolemRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(SupportWoodenGolem.class, new SupportWoodenGolemRender.RenderFactory());

	}

}
