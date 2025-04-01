package net.merchantcalico.krendershowcase.registry;

import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.block.CharacterBlock;
import net.merchantcalico.krendershowcase.block.SuzanneBlock;
import net.merchantcalico.krendershowcase.block.TeapotBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class ShowcaseBlocks {
    public static final Block CHARACTER = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("character"),
            new CharacterBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(0.3F).sound(SoundType.WOOD)
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));
    public static final Block DISCO_FLOOR = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("disco_floor"),
            new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.DRAGON).strength(0.3F).sound(SoundType.GLASS)
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));

    public static final Block TEAPOT = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("teapot"),
            new TeapotBlock(BlockBehaviour.Properties.of().noOcclusion()
                    .isValidSpawn(ShowcaseBlocks::never)));

    public static final Block SMALL_SUZANNE = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("small_suzanne"),
            new SuzanneBlock(BlockBehaviour.Properties.of().noOcclusion()
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));
    public static final Block SUZANNE = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("suzanne"),
            new SuzanneBlock(BlockBehaviour.Properties.of().noOcclusion()
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));
    public static final Block LARGE_SUZANNE = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("large_suzanne"),
            new SuzanneBlock(BlockBehaviour.Properties.of().noOcclusion()
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));

    public static void registerAll() {}

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos,
                                 EntityType<?> entityType) {
        return false;
    }
}
