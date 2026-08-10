package com.badlyac.zombieking.entity;

import com.badlyac.zombieking.entity.ai.ZombieKingSummonGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZombieKingEntity extends Zombie {

    public static final int MIN_SOLDIERS = 7;
    public static final int MAX_SOLDIERS = 10;
    public static final int MAX_BOMBERS = 2;

    private final List<UUID> summonedSoldiers = new ArrayList<>();
    private int summonedBombers = 0;
    private boolean hasSummonedArmy = false;

    public ZombieKingEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new ZombieKingSummonGoal(this));
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    public List<UUID> getSummonedSoldiers() {
        return summonedSoldiers;
    }

    public boolean hasSummonedArmy() {
        return hasSummonedArmy;
    }

    public void setHasSummonedArmy(boolean hasSummonedArmy) {
        this.hasSummonedArmy = hasSummonedArmy;
    }

    public int getSummonedBombers() {
        return summonedBombers;
    }

    public void incrementSummonedBombers() {
        this.summonedBombers++;
    }
}
