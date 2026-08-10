package com.badlyac.zombieking.client.renderer;

import com.badlyac.zombieking.client.renderer.layer.ZombieKingCrownLayer;
import com.badlyac.zombieking.entity.ZombieKingEntity;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ZombieKingRenderer extends AbstractZombieRenderer<ZombieKingEntity, ZombieModel<ZombieKingEntity>> {

    public ZombieKingRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))
        );
        this.addLayer(new ZombieKingCrownLayer<>(this, context.getItemInHandRenderer()));
    }
}
