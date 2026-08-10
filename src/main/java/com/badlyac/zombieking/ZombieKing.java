package com.badlyac.zombieking;

import com.badlyac.zombieking.entity.ModEntityTypes;
import com.badlyac.zombieking.event.ModSpawnEvents;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ZombieKing.MODID)
public class ZombieKing {

    public static final String MODID = "zombieking";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ZombieKing(IEventBus modEventBus, ModContainer modContainer) {
        ModEntityTypes.register(modEventBus);

        modEventBus.addListener(ModEntityTypes::registerAttributes);

        NeoForge.EVENT_BUS.register(ModSpawnEvents.class);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            com.badlyac.zombieking.client.ZombieKingClient.init(modEventBus);
        }
    }
}
