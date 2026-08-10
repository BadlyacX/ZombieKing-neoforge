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

/**
 * 純視覺效果的渲染層：在殭屍王頭上疊加一個漂浮的金色頭盔圖示，作為「王冠」的外觀標記。
 * <p>
 * 這裡刻意不把任何物品放進頭部裝備欄（{@code EquipmentSlot.HEAD}），
 * 因此殭屍王的護甲值、掉落物等設定完全不受影響，與普通殭屍相同——只有外觀多了王冠。
 */
public class ZombieKingCrownLayer<T extends ZombieKingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {

    private static final ItemStack CROWN_DISPLAY_ITEM = new ItemStack(Items.GOLDEN_HELMET);

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
        this.itemInHandRenderer.renderItem(entity, CROWN_DISPLAY_ITEM, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
