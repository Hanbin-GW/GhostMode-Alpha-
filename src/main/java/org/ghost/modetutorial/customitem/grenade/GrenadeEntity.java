package org.ghost.modetutorial.customitem.grenade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.ghost.modetutorial.customitem.ModItems;

public class GrenadeEntity extends ThrowableProjectile implements ItemSupplier {
    private int fuse = 40;
    private boolean exploded;   // 중복 방지

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
        // 투사체 렌더(ThrownItemRenderer)가 사용할 아이템 모델/텍스처
        return new ItemStack(ModItems.GRENADE.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!exploded && --fuse <= 0) {
                explode();
            }
        }
    }
    @Override
    protected void onHit(HitResult hit) {
        super.onHit(hit);
        if (!level().isClientSide && !exploded) {
            explode(); // 접촉 즉시 폭발
        }
    }
    private void explode() {
        if (exploded) return;
        exploded = true;

        // 폭발: (원인자, x,y,z, 파워, ExplosionInteraction)
        // ExplosionInteraction.BLOCK = 블록 파괴 허용
        // ExplosionInteraction.NONE  = 블록 파괴 없음 (데미지만)
        float power = 3.0F;
        Level.ExplosionInteraction interaction = Level.ExplosionInteraction.BLOCK;

        // 여기서 owner를 원인자로 넘겨서 누가 터트렸는지 기록 가능
        Level lvl = level();
        lvl.explode(getOwner(), getX(), getY(), getZ(), power, interaction);

        // 소리/파티클을 따로 추가하고 싶으면 여기에 추가
        // lvl.playSound(null, getX(), getY(), getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1f, 1f);

        // 제거
        discard();
    }

//    private void explode() {
//        exploded = true; // 중복 방지
//        // 폭발: (원인자, x,y,z, 위력, 블록 파괴 여부)
//        level().explode(getOwner(), getX(), getY(), getZ(),
//                3.0F, Level.ExplosionInteraction.BLOCK); // BLOCK: 블록 파괴 / NONE: 파괴 안 함
//
//        // 원하면 소리/파티클 추가
//        // level().playSound(...), level().addParticle(...)
//
//        discard(); // 엔티티 제거
//    }
}
