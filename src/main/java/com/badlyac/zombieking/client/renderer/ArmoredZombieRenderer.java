package com.badlyac.zombieking.client.renderer;

import com.badlyac.zombieking.entity.ArmoredZombieEntity;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 盔甲兵沿用原版殭屍模型與貼圖，鑽石盔甲與劍會透過原版的盔甲、手持物件渲染層自動顯示，
 * 不需要額外的模型或貼圖資源。
 */
public class ArmoredZombieRenderer extends AbstractZombieRenderer<ArmoredZombieEntity, ZombieModel<ArmoredZombieEntity>> {

    public ArmoredZombieRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))
        );
    }
}
