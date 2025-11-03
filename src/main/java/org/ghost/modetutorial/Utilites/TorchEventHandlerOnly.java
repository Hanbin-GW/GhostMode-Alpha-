package org.ghost.modetutorial.Utilites;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ghost.modetutorial.Modetutorial;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = Modetutorial.MODID)
public class TorchEventHandlerOnly {

    private static final Map<UUID, BlockPos> LAST_POS = new ConcurrentHashMap<>();

    private static boolean isTorch(Item i) {
        return i == Items.TORCH || i == Items.SOUL_TORCH; // 필요시 REDSTONE_TORCH 추가
    }
    private static boolean holdingTorch(Player p) {
        return isTorch(p.getMainHandItem().getItem()) || isTorch(p.getOffhandItem().getItem());
    }

    // 놓을 후보 위치: 눈높이 근처 → 머리 위
    private static BlockPos[] candidatePositions(Player p) {
        BlockPos eye = BlockPos.containing(p.getX(), p.getEyeY() + 0.2, p.getZ());
        BlockPos above = p.blockPosition().above(2);
        return new BlockPos[]{ eye, above };
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;

        Player p = e.player;
        Level level = p.level();
        if (level.isClientSide()) return; // 서버에서만

        UUID id = p.getUUID();
        BlockPos prev = LAST_POS.get(id);

        if (holdingTorch(p)) {
            BlockPos target = null;

            // 공기거나 이미 LIGHT인 곳만 후보로 사용
            for (BlockPos pos : candidatePositions(p)) {
                if (!level.isInWorldBounds(pos)) continue;
                var state = level.getBlockState(pos);
                if (state.isAir() || state.is(Blocks.LIGHT)) {
                    target = pos;
                    break;
                }
            }

            // 이전 위치 정리(이동 시 꼬리 방지)
            if (prev != null && (target == null || !prev.equals(target))) {
                if (level.getBlockState(prev).is(Blocks.LIGHT)) {
                    level.setBlock(prev, Blocks.AIR.defaultBlockState(), 3);
                }
            }

            if (target != null) {
                BlockState desired = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15);
                BlockState cur = level.getBlockState(target);
                if (!cur.is(Blocks.LIGHT) || cur.getValue(LightBlock.LEVEL) != 15) {
                    level.setBlock(target, desired, 3);
                }
                LAST_POS.put(id, target);
            } else {
                // 주변이 막혀 있으면 유지하지 않음
                LAST_POS.put(id, null);
            }
        } else {
            // 횃불을 내려놓으면 제거
            if (prev != null && level.getBlockState(prev).is(Blocks.LIGHT)) {
                level.setBlock(prev, Blocks.AIR.defaultBlockState(), 3);
            }
            LAST_POS.remove(id);
        }
    }
}
