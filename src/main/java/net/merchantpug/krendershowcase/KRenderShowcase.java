package net.merchantpug.krendershowcase;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.merchantpug.krendershowcase.data.CharacterData;
import net.merchantpug.krendershowcase.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KRenderShowcase implements ModInitializer {
	public static final String MOD_ID = "krendershowcase";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<Registry<CharacterData>> CHARACTER = ResourceKey.createRegistryKey(asResource("character"));

	@Override
	public void onInitialize() {
		ShowcaseBlocks.registerAll();
		ShowcaseBlockEntityTypes.registerAll();
		ShowcaseDataComponents.registerAll();
		ShowcaseItems.registerAll();
		ShowcaseCreativeModeTabs.registerAll();

		DynamicRegistries.registerSynced(CHARACTER, CharacterData.DIRECT_CODEC);
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}