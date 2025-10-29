package org.ghost.modetutorial.customitem.grenade.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.ghost.modetutorial.Modetutorial;
import org.ghost.modetutorial.customitem.grenade.GrenadeEntity;
import org.ghost.modetutorial.customitem.grenade.client.model.GrenadeModel;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Modetutorial.MODID, "item/texture");

    private final GrenadeModel model;

    public GrenadeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new GrenadeModel(ctx.bakeLayer(GrenadeModel.LAYER_LOCATION));
    }

    @Override
    public void render(GrenadeEntity entity, float yaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        var vc = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.root().render(poseStack, vc, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GrenadeEntity entity) {
        return TEXTURE;
    }
}
