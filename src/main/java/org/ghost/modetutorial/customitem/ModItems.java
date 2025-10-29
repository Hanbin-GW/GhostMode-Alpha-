package org.ghost.modetutorial.customitem;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ghost.modetutorial.Modetutorial;
import org.ghost.modetutorial.customitem.grenade.GrenadeItem;

public class ModItems {
    public static final String MODID = Modetutorial.MODID;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> GRENADE =
            ITEMS.register("grenade", () -> new GrenadeItem(new Item.Properties().stacksTo(16)));
}
