package imashleigh.woodengolems.client.layers;

import com.mojang.blaze3d.platform.GlStateManager;

import imashleigh.woodengolems.client.models.SupportWoodenGolemModel;
import imashleigh.woodengolems.client.models.WoodenGolemModel;
import imashleigh.woodengolems.entities.SupportWoodenGolem;
import imashleigh.woodengolems.entities.WoodenGolem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SupportWoodenGolemHeadLayer extends LayerRenderer<SupportWoodenGolem, SupportWoodenGolemModel> {
   public SupportWoodenGolemHeadLayer(IEntityRenderer<SupportWoodenGolem, SupportWoodenGolemModel> p_i50922_1_) {
      super(p_i50922_1_);
   }

   public void render(SupportWoodenGolem entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) 
   {
	   
	          GlStateManager.pushMatrix();
	          this.getEntityModel().func_205070_a().postRender(0.0625F);
	          float f = 0.625F;
	          GlStateManager.translatef(0.0F, -0.34375F, 0.0F);
	          GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
	          GlStateManager.scalef(0.625F, -0.625F, -0.625F);
	          Minecraft.getInstance().getFirstPersonRenderer().renderItem(entityIn, new ItemStack(Blocks.CARVED_PUMPKIN), ItemCameraTransforms.TransformType.HEAD);
	          GlStateManager.popMatrix();
	       
      
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}

