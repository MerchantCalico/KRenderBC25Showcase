package net.merchantpug.krendershowcase.registry;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.block.CharacterBlock;
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
            new CharacterBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD)
                    .isValidSpawn(ShowcaseBlocks::never).isSuffocating(ShowcaseBlocks::never)));
    public static final Block DISCO_FLOOR = Registry.register(BuiltInRegistries.BLOCK, KRenderShowcase.asResource("disco_floor"),
            new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.DRAGON).strength(0.3F).sound(SoundType.GLASS)
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
