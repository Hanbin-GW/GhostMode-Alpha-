package org.ghost.modetutorial.customitem.grenade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import org.ghost.modetutorial.customitem.ModItems;

public class GrenadeEntity extends ThrowableProjectile implements ItemSupplier {
    private int fuse = 40;
    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, Level level) {
        super(type, level);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, Level level) {
        super(type, owner, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) { fuse = tag.getInt("Fuse"); }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) { tag.putInt("Fuse", fuse); }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.GRENADE.get());
    }
}
