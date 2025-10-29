package org.ghost.modetutorial.customitem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ghost.modetutorial.Modetutorial;
import org.ghost.modetutorial.customitem.grenade.GrenadeEntity;


public class ModEntities {
    public static final String MODID = Modetutorial.MODID;

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE =
            ENTITIES.register("grenade",
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(10)
                            .updateInterval(10)
                            .build("grenade"));
}