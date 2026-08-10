package com.badlyac.zombieking.client.renderer.layer;

import com.badlyac.zombieking.entity.ZombieKingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ZombieKingCrownLayer<T extends ZombieKingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {

    private static final ItemStack CROWN_BLOCK = new ItemStack(Items.GOLD_BLOCK);

    private static final int RING_POINTS = 16;
    private static final float RING_RADIUS = 0.5F;
    private static final float RING_SCALE = 0.23F;
    private static final float SPIKE_RISE = 0.32F;
    private static final float SPIKE_SCALE = 0.21F;

    private final ItemInHandRenderer itemInHandRenderer;

    public ZombieKingCrownLayer(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (entity.isInvisible()) {
            return;
        }

        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        CustomHeadLayer.translateToHead(poseStack, false);

        for (int i = 0; i < RING_POINTS; i++) {
            double angle = Math.PI * 2.0 * i / RING_POINTS;
            float x = (float) (Math.cos(angle) * RING_RADIUS);
            float z = (float) (Math.sin(angle) * RING_RADIUS);

            renderCrownBlock(poseStack, buffer, packedLight, entity, x, 0.5F, z, RING_SCALE);

            if (i % 2 == 0) {
                renderCrownBlock(poseStack, buffer, packedLight, entity, x, SPIKE_RISE + 0.41F, z, SPIKE_SCALE);
            }
        }

        poseStack.popPose();
    }

    private void renderCrownBlock(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            T entity,
            float x,
            float y,
            float z,
            float scale
    ) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        this.itemInHandRenderer.renderItem(entity, CROWN_BLOCK, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
