package net.merchantcalico.krendershowcase.registry;

import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.block.entity.CharacterBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ShowcaseBlockEntityTypes {
    public static final BlockEntityType<CharacterBlockEntity> CHARACTER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, KRenderShowcase.asResource("character"),
            BlockEntityType.Builder.of(CharacterBlockEntity::new, ShowcaseBlocks.CHARACTER).build(null));

    public static void registerAll() {}
}
