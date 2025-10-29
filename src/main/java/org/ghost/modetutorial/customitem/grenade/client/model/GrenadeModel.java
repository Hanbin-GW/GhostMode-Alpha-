package org.ghost.modetutorial.customitem.grenade.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.ghost.modetutorial.Modetutorial;
import org.ghost.modetutorial.customitem.grenade.GrenadeEntity;

public class GrenadeModel extends HierarchicalModel<GrenadeEntity> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Modetutorial.MODID, "grenade"),
                    "main"
            );

    private final ModelPart root;
    private final ModelPart body;

    public GrenadeModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3, -3, -3, 6, 6, 6),
                PartPose.ZERO
        );
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(GrenadeEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // 필요 시 회전 애니메이션
        // body.yRot = ageInTicks * 0.4f;
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
