package org.ghost.modetutorial.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ghost.modetutorial.Modetutorial;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Modetutorial.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Modetutorial.MODID);

    public static final RegistryObject<RecipeSerializer<Grenaderecipe>> GROWTH_CHAMBER_SERIALIZER =
            SERIALIZERS.register("growth_chamber", Grenaderecipe.Serializer::new);
    public static final RegistryObject<RecipeType<Grenaderecipe>> GROWTH_CHAMBER_TYPE =
            TYPES.register("growth_chamber", () -> new RecipeType<Grenaderecipe>() {
                @Override
                public String toString() {
                    return "Grenade";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
