package net.merchantpug.krendershowcase.registry;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.item.MiniBlockItem;
import net.merchantpug.krendershowcase.item.SuzanneItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class ShowcaseItems {
    public static final BlockItem CHARACTER = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("character"),
            new DoubleHighBlockItem(ShowcaseBlocks.CHARACTER, new Item.Properties()));

    public static final BlockItem DISCO_FLOOR = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("disco_floor"),
            new BlockItem(ShowcaseBlocks.DISCO_FLOOR, new Item.Properties()));

    public static final SuzanneItem SMALL_SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("small_suzanne"),
            new SuzanneItem(ShowcaseBlocks.SMALL_SUZANNE, new Item.Properties()));
    public static final SuzanneItem SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("suzanne"),
            new SuzanneItem(ShowcaseBlocks.SUZANNE, new Item.Properties()));
    public static final SuzanneItem LARGE_SUZANNE = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("large_suzanne"),
            new SuzanneItem(ShowcaseBlocks.LARGE_SUZANNE, new Item.Properties()));
    public static final BlockItem TEAPOT = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("teapot"),
            new BlockItem(ShowcaseBlocks.TEAPOT, new Item.Properties()));

    public static final MiniBlockItem MINI_BLOCK = Registry.register(BuiltInRegistries.ITEM, KRenderShowcase.asResource("mini_block"), new MiniBlockItem((new Item.Properties()
            .stacksTo(1).component(ShowcaseDataComponents.BLOCK_STATE, Blocks.IRON_BLOCK.defaultBlockState()))));

    public static void registerAll() {}
}
