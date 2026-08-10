package com.badlyac.zombieking.client;

import com.badlyac.zombieking.client.renderer.ArmoredZombieRenderer;
import com.badlyac.zombieking.client.renderer.CrazyBomberRenderer;
import com.badlyac.zombieking.client.renderer.ZombieKingRenderer;
import com.badlyac.zombieking.entity.ModEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class ZombieKingClient {

    private ZombieKingClient() {
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ZombieKingClient::registerRenderers);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.ZOMBIE_KING.get(), ZombieKingRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ARMORED_ZOMBIE.get(), ArmoredZombieRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRAZY_BOMBER.get(), CrazyBomberRenderer::new);
    }
}
