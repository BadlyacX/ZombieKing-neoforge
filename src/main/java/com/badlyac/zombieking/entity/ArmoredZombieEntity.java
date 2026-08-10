package com.badlyac.zombieking.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArmoredZombieEntity extends Zombie {

    public static final float EQUIPMENT_DROP_CHANCE = 0.05F;

    public ArmoredZombieEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public void equipArmoredGear(ServerLevel level) {
        Holder<Enchantment> sharpness = level.registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.SHARPNESS);

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.enchant(sharpness, 5);

        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0.0F);
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);

        if (this.random.nextFloat() >= EQUIPMENT_DROP_CHANCE) {
            return;
        }

        List<ItemStack> equipped = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = this.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                equipped.add(stack);
            }
        }

        if (!equipped.isEmpty()) {
            ItemStack drop = equipped.get(this.random.nextInt(equipped.size())).copy();
            this.spawnAtLocation(drop);
        }
    }
}
