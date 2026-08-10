package com.badlyac.zombieking.entity.ai;

import com.badlyac.zombieking.entity.CrazyBomberEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class BomberChargeAndExplodeGoal extends Goal {

    private static final double DETONATION_RANGE = 2.2;
    private static final double DETONATION_RANGE_SQR = DETONATION_RANGE * DETONATION_RANGE;
    private static final int FUSE_TICKS = 15;
    private static final float EXPLOSION_POWER = 4.0F;

    private final CrazyBomberEntity bomber;
    private int fuseTime;

    public BomberChargeAndExplodeGoal(CrazyBomberEntity bomber) {
        this.bomber = bomber;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = bomber.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        fuseTime = 0;
    }

    @Override
    public void stop() {
        fuseTime = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = bomber.getTarget();
        if (target == null) {
            return;
        }

        bomber.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double distanceSqr = bomber.distanceToSqr(target);
        if (distanceSqr <= DETONATION_RANGE_SQR) {
            bomber.getNavigation().stop();
            fuseTime++;
            if (fuseTime >= FUSE_TICKS) {
                explode();
            }
        } else {
            fuseTime = 0;
            bomber.getNavigation().moveTo(target, 1.3);
        }
    }

    private void explode() {
        Level level = bomber.level();
        if (level instanceof ServerLevel serverLevel && bomber.isAlive()) {
            serverLevel.explode(bomber, bomber.getX(), bomber.getY(), bomber.getZ(), EXPLOSION_POWER, Level.ExplosionInteraction.MOB);
            bomber.discard();
        }
    }
}
