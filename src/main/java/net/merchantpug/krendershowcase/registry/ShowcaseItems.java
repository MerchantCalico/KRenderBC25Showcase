package net.merchantpug.krendershowcase.registry;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ShowcaseItems {
    public static final BlockItem CHARACTER = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("character"),
            new BlockItem(ShowcaseBlocks.CHARACTER, new Item.Properties()));
    public static final BlockItem DISCO_FOOR = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("disco_floor"),
            new BlockItem(ShowcaseBlocks.DISCO_FLOOR, new Item.Properties()));

    public static void registerAll() {}
}
