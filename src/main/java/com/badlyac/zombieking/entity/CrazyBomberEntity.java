package com.badlyac.zombieking.entity;

import com.badlyac.zombieking.entity.ai.BomberChargeAndExplodeGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CrazyBomberEntity extends Zombie {

    public CrazyBomberEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
        this.setBaby(true);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.TNT));
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BomberChargeAndExplodeGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
