package com.badlyac.zombieking.entity.ai;

import com.badlyac.zombieking.entity.ArmoredZombieEntity;
import com.badlyac.zombieking.entity.CrazyBomberEntity;
import com.badlyac.zombieking.entity.ModEntityTypes;
import com.badlyac.zombieking.entity.ZombieKingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.UUID;

public class ZombieKingSummonGoal extends Goal {

    private final ZombieKingEntity king;

    public ZombieKingSummonGoal(ZombieKingEntity king) {
        this.king = king;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        return king.getTarget() != null && king.level() instanceof ServerLevel;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (!(king.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!king.hasSummonedArmy()) {
            summonSoldiers(serverLevel);
            king.setHasSummonedArmy(true);
            return;
        }

        pruneDeadSoldiers(serverLevel);

        if (king.getSummonedSoldiers().isEmpty() && king.getSummonedBombers() < ZombieKingEntity.MAX_BOMBERS) {
            summonBomber(serverLevel);
        }
    }

    private void summonSoldiers(ServerLevel level) {
        int count = ZombieKingEntity.MIN_SOLDIERS
                + king.getRandom().nextInt(ZombieKingEntity.MAX_SOLDIERS - ZombieKingEntity.MIN_SOLDIERS + 1);

        for (int i = 0; i < count; i++) {
            ArmoredZombieEntity soldier = ModEntityTypes.ARMORED_ZOMBIE.get().create(level);
            if (soldier == null) {
                continue;
            }

            Vec3 pos = randomPositionAround(king.position(), 2.0, 4.0);
            soldier.moveTo(pos.x, pos.y, pos.z, king.getYRot(), 0.0F);
            soldier.finalizeSpawn(level, level.getCurrentDifficultyAt(soldier.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
            soldier.equipArmoredGear(level);

            LivingEntity target = king.getTarget();
            if (target != null) {
                soldier.setTarget(target);
            }

            level.addFreshEntity(soldier);
            king.getSummonedSoldiers().add(soldier.getUUID());
        }
    }

    private void summonBomber(ServerLevel level) {
        CrazyBomberEntity bomber = ModEntityTypes.CRAZY_BOMBER.get().create(level);
        if (bomber == null) return;

        Vec3 pos = randomPositionAround(king.position(), 1.5, 3.0);
        bomber.moveTo(pos.x, pos.y, pos.z, king.getYRot(), 0.0F);
        bomber.finalizeSpawn(level, level.getCurrentDifficultyAt(bomber.blockPosition()), MobSpawnType.MOB_SUMMONED, null);

        LivingEntity target = king.getTarget();
        if (target != null) bomber.setTarget(target);

        level.addFreshEntity(bomber);
        king.incrementSummonedBombers();
    }

    private void pruneDeadSoldiers(ServerLevel level) {
        Iterator<UUID> iterator = king.getSummonedSoldiers().iterator();
        while (iterator.hasNext()) {
            UUID id = iterator.next();
            var entity = level.getEntity(id);
            if (!(entity instanceof LivingEntity livingEntity) || !livingEntity.isAlive()) {
                iterator.remove();
            }
        }
    }

    private Vec3 randomPositionAround(Vec3 center, double minRadius, double maxRadius) {
        double angle = king.getRandom().nextDouble() * Math.PI * 2.0;
        double radius = minRadius + king.getRandom().nextDouble() * (maxRadius - minRadius);
        return new Vec3(center.x + Math.cos(angle) * radius, center.y, center.z + Math.sin(angle) * radius);
    }
}
