package net.merchantpug.krendershowcase.registry;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.block.entity.CharacterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ShowcaseBlockEntityTypes {
    public static final BlockEntityType<CharacterBlockEntity> CHARACTER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, KRenderShowcase.asResource("character"),
            BlockEntityType.Builder.of(CharacterBlockEntity::new, ShowcaseBlocks.CHARACTER).build(null));

    public static void registerAll() {}
}
