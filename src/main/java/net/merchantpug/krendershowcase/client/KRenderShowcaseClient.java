package net.merchantpug.krendershowcase.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kneelawk.krender.model.guard.api.ModelGuards;
import com.kneelawk.krender.model.loading.api.ModelBakeryPlugin;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.client.model.CharacterModelData;
import net.merchantpug.krendershowcase.client.model.unbaked.MiniBlockUnbakedModel;
import net.merchantpug.krendershowcase.client.model.unbaked.CharacterUnbakedModel;
import net.merchantpug.krendershowcase.client.model.unbaked.DiscoFloorUnbakedModel;
import net.merchantpug.krendershowcase.data.CharacterData;
import net.merchantpug.krendershowcase.registry.ShowcaseBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class KRenderShowcaseClient implements ClientModInitializer {
	public static final ResourceLocation CHARACTER_LOADER_ID = KRenderShowcase.asResource("character");

	@Override
	public void onInitializeClient() {
		ModelBakeryPlugin.register(ctx -> {
			ctx.addLowLevelModel(KRenderShowcase.asResource("block/disco_floor"), new DiscoFloorUnbakedModel());
			ctx.addLowLevelModel(KRenderShowcase.asResource("item/disco_floor"), new DiscoFloorUnbakedModel());
			ctx.addLowLevelModel(KRenderShowcase.asResource("item/mini_block"), new MiniBlockUnbakedModel());
		});

		ModelBakeryPlugin.registerPreparable((resourceManager, executor) -> CompletableFuture.supplyAsync(() -> {
			Map<ResourceKey<CharacterData>, CharacterModelData<ResourceLocation>> models = new Object2ObjectLinkedOpenHashMap<>();

			ModelGuards guards = ModelGuards.load(resourceManager);
			Map<ResourceLocation, Resource> resources = guards.getModels(resourceManager, CHARACTER_LOADER_ID, ".json");

			for (Map.Entry<ResourceLocation, Resource> resource : resources.entrySet()) {
				try (BufferedReader reader = resource.getValue().openAsReader()) {
					JsonElement json = JsonParser.parseReader(reader);
					var pair = CharacterUnbakedModel.FILE_CODEC.decode(JsonOps.INSTANCE, json).getOrThrow().getFirst();
					models.put(pair.getFirst(), pair.getSecond());
				} catch (IOException ex) {
					KRenderShowcase.LOG.warn("Failed to load character model '{}' found in pack '{}'", resource.getKey(), resource.getValue().sourcePackId(), ex);
				}
			}

			return new CharacterUnbakedModel(models);
		}), (map, context) ->
				context.addLowLevelModel(KRenderShowcase.asResource("block/character"), map));

		BlockRenderLayerMap.INSTANCE.putBlock(ShowcaseBlocks.CHARACTER, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ShowcaseBlocks.SUZANNE, RenderType.cutout());
	}
}