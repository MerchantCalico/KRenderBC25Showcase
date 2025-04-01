package net.merchantcalico.krendershowcase.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantcalico.krendershowcase.block.SuzanneBlock;
import net.merchantcalico.krendershowcase.client.KRenderShowcaseClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public class Mixin_CustomHeadLayer {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private <T extends LivingEntity> void krendershowcase$transformSuzanne(PoseStack poseStack,
                                                                           MultiBufferSource buffer,
                                                                           int packedLight,
                                                                           T livingEntity,
                                                                           float limbSwing,
                                                                           float limbSwingAmount,
                                                                           float partialTicks,
                                                                           float ageInTicks,
                                                                           float netHeadYaw,
                                                                           float headPitch,
                                                                           CallbackInfo ci,
                                                                           @Local ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof SuzanneBlock)
            KRenderShowcaseClient.transformSuzanneModelOnHead(stack.getItem(), poseStack);
    }
}
