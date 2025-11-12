package org.ghost.modetutorial.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record Grenaderecipe(Ingredient inputItem, ItemStack output) implements Recipe<GrenadeRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }
    @Override
    public boolean matches(GrenadeRecipeInput grenadeRecipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(grenadeRecipeInput.getItem(0));    }

    @Override
    public ItemStack assemble(GrenadeRecipeInput grenadeRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true
                ;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<Grenaderecipe> {
        public static final MapCodec<Grenaderecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(Grenaderecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(Grenaderecipe::output)
        ).apply(inst, Grenaderecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Grenaderecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, Grenaderecipe::inputItem,
                        ItemStack.STREAM_CODEC, Grenaderecipe::output,
                        Grenaderecipe::new);

        @Override
        public MapCodec<Grenaderecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, Grenaderecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
