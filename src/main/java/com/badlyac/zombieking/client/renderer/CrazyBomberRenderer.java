package com.badlyac.zombieking.client.renderer;

import com.badlyac.zombieking.entity.CrazyBomberEntity;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 瘋狂炸彈客沿用原版殭屍模型與貼圖。頭上的 TNT 是放在頭部裝備欄的一般物品（而非盔甲），
 * 會由 {@code HumanoidMobRenderer} 內建的 {@code CustomHeadLayer} 自動渲染成漂浮於頭頂的物品，
 * 不需要額外的模型或貼圖資源。
 */
public class CrazyBomberRenderer extends AbstractZombieRenderer<CrazyBomberEntity, ZombieModel<CrazyBomberEntity>> {

    public CrazyBomberRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))
        );
    }
}
