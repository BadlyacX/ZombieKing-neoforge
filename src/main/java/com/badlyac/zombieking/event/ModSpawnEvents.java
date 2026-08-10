package com.badlyac.zombieking.event;

import com.badlyac.zombieking.entity.ModEntityTypes;
import com.badlyac.zombieking.entity.ZombieKingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

public final class ModSpawnEvents {

    private static final float ZOMBIE_KING_SPAWN_CHANCE = 0.13F;
    private static final double ZOMBIE_KING_NEARBY_RADIUS = 70.0;

    private ModSpawnEvents() {
    }

    @SubscribeEvent
    public static void onZombieFinalizeSpawn(FinalizeSpawnEvent event) {
        if (event.getSpawnType() != MobSpawnType.NATURAL) {
            return;
        }
        if (event.getEntity().getType() != EntityType.ZOMBIE) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        RandomSource random = serverLevel.getRandom();
        if (random.nextFloat() >= ZOMBIE_KING_SPAWN_CHANCE) {
            return;
        }

        if (isZombieKingNearby(serverLevel, event.getX(), event.getY(), event.getZ())) {
            return;
        }

        event.setSpawnCancelled(true);

        ZombieKingEntity king = ModEntityTypes.ZOMBIE_KING.get().create(serverLevel);
        if (king == null) {
            return;
        }

        king.moveTo(event.getX(), event.getY(), event.getZ(), event.getEntity().getYRot(), 0.0F);
        king.finalizeSpawn(serverLevel, event.getDifficulty(), MobSpawnType.NATURAL, null);
        serverLevel.addFreshEntity(king);
    }

    private static boolean isZombieKingNearby(ServerLevel level, double x, double y, double z) {
        AABB area = new AABB(x, y, z, x, y, z).inflate(ZOMBIE_KING_NEARBY_RADIUS);
        if (!level.getEntitiesOfClass(ZombieKingEntity.class, area).isEmpty()) {
            return true;
        }

        for (ServerPlayer player : level.players()) {
            AABB playerArea = player.getBoundingBox().inflate(ZOMBIE_KING_NEARBY_RADIUS);
            if (!level.getEntitiesOfClass(ZombieKingEntity.class, playerArea).isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
