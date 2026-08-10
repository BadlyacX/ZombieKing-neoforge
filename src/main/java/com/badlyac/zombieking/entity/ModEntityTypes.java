package com.badlyac.zombieking.entity;

import com.badlyac.zombieking.ZombieKing;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ZombieKing.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ZombieKingEntity>> ZOMBIE_KING =
            ENTITY_TYPES.register("zombie_king", key -> EntityType.Builder.of(ZombieKingEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(key.getPath()));

    public static final DeferredHolder<EntityType<?>, EntityType<ArmoredZombieEntity>> ARMORED_ZOMBIE =
            ENTITY_TYPES.register("armored_zombie", key -> EntityType.Builder.of(ArmoredZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(key.getPath()));

    public static final DeferredHolder<EntityType<?>, EntityType<CrazyBomberEntity>> CRAZY_BOMBER =
            ENTITY_TYPES.register("crazy_bomber", key -> EntityType.Builder.of(CrazyBomberEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(key.getPath()));

    private ModEntityTypes() {
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ZOMBIE_KING.get(), Zombie.createAttributes().build());
        event.put(ARMORED_ZOMBIE.get(), Zombie.createAttributes().build());
        event.put(CRAZY_BOMBER.get(), Zombie.createAttributes().build());
    }
}
