package net.merchantcalico.krendershowcase.registry;

import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ShowcaseDataComponents {
    public static final DataComponentType<BlockState> BLOCK_STATE = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, KRenderShowcase.asResource("block_state"),
            DataComponentType.<BlockState>builder()
                    .persistent(BlockState.CODEC)
                    .networkSynchronized(ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY))
                    .build());

    public static void registerAll() {}
}
