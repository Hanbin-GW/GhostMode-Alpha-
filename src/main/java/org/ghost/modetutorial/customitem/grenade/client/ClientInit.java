package org.ghost.modetutorial.customitem.grenade.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ghost.modetutorial.Modetutorial;
import org.ghost.modetutorial.customitem.ModEntities;
import org.ghost.modetutorial.customitem.grenade.client.model.GrenadeModel;

@Mod.EventBusSubscriber(modid = Modetutorial.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    // 렌더러 등록 - **이것이 올바른 위치입니다.**
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ModEntities.GRENADE.get()은 이제 이 시점에 Registry Object가 존재해야 합니다.
        event.registerEntityRenderer(ModEntities.GRENADE.get(), GrenadeRenderer::new);
    }

    // 모델 레이어 등록
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GrenadeModel.LAYER_LOCATION, GrenadeModel::createBodyLayer);
    }
}
