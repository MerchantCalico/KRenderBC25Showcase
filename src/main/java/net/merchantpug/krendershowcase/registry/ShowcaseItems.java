package net.merchantpug.krendershowcase.registry;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.item.MiniBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class ShowcaseItems {
    public static final BlockItem CHARACTER = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("character"),
            new DoubleHighBlockItem(ShowcaseBlocks.CHARACTER, new Item.Properties()));

    public static final BlockItem DISCO_FOOR = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("disco_floor"),
            new BlockItem(ShowcaseBlocks.DISCO_FLOOR, new Item.Properties()));

    public static final BlockItem SMALL_SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("small_suzanne"), new BlockItem(ShowcaseBlocks.SMALL_SUZANNE, new Item.Properties()));
    public static final BlockItem SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("suzanne"), new BlockItem(ShowcaseBlocks.SUZANNE, new Item.Properties()));
    public static final BlockItem LARGE_SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("large_suzanne"), new BlockItem(ShowcaseBlocks.LARGE_SUZANNE, new Item.Properties()));

    public static final MiniBlockItem MINI_BLOCK = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("mini_block"), new MiniBlockItem((new Item.Properties()
            .stacksTo(1).component(ShowcaseDataComponents.BLOCK_STATE, Blocks.IRON_BLOCK.defaultBlockState()))));

    public static void registerAll() {}
}
