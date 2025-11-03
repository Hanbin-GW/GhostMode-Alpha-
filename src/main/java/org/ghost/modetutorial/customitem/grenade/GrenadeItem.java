package org.ghost.modetutorial.customitem.grenade;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ghost.modetutorial.customitem.ModEntities;

public class GrenadeItem extends Item {
    public GrenadeItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // 디버그 로그로 호출 확인
        System.out.println("[GRENADE] use called, side=" + (level.isClientSide ? "client" : "server"));

        // 서버에서만 엔티티 생성
        if (!level.isClientSide) {
            // 엔티티 생성
            GrenadeEntity grenade = new GrenadeEntity(
                    org.ghost.modetutorial.customitem.ModEntities.GRENADE.get(),
                    player,
                    level
            );

            // 던지기 위치 (플레이어 눈높이 기준)
            grenade.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

            // 던지기 방향과 속도 설정
            grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.2F, 0.1F);

            // 월드에 엔티티 추가
            level.addFreshEntity(grenade);

            // 생존모드라면 수류탄 개수 줄이기
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            // 소리 효과
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    net.minecraft.sounds.SoundEvents.SNOWBALL_THROW,
                    net.minecraft.sounds.SoundSource.PLAYERS,
                    0.5F,
                    1.0F
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
