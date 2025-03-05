package net.merchantpug.krendershowcase.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.merchantpug.krendershowcase.KRenderShowcase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ShowcaseCreativeModeTabs {
    public static final CreativeModeTab CREATIVE_MODE_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, KRenderShowcase.asResource("main"),
            FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + KRenderShowcase.MOD_ID + ".main"))
                    .displayItems((params, output) -> {
                        output.accept(ShowcaseItems.CHARACTER);
                        output.accept(ShowcaseItems.DISCO_FOOR);
                    })
                    .icon(() -> new ItemStack(ShowcaseItems.CHARACTER))
                    .build()
    );

    public static void registerAll() {}
}
