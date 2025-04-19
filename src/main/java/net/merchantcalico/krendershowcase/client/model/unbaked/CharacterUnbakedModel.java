package net.merchantcalico.krendershowcase.client.model.unbaked;

import com.kneelawk.krender.engine.api.KRenderer;
import com.kneelawk.krender.engine.api.model.BakedModelCore;
import com.kneelawk.krender.engine.base.model.BakedModelCoreProvider;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.client.model.CharacterModelData;
import net.merchantcalico.krendershowcase.client.model.baked.CharacterBakedModel;
import net.merchantcalico.krendershowcase.data.CharacterData;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CharacterUnbakedModel(Map<ResourceKey<CharacterData>, CharacterModelData<ResourceLocation>> models) implements UnbakedModel {
    private static final Codec<CharacterModelData<ResourceLocation>> MODEL_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("top").forGetter(CharacterModelData::top),
            ResourceLocation.CODEC.fieldOf("bottom").forGetter(CharacterModelData::bottom)
    ).apply(inst, CharacterModelData::new));
    public static final Codec<Pair<ResourceKey<CharacterData>, CharacterModelData<ResourceLocation>>> FILE_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceKey.codec(KRenderShowcase.CHARACTER).fieldOf("character").forGetter(Pair::getFirst),
            MODEL_CODEC.fieldOf("models").forGetter(Pair::getSecond)
    ).apply(inst, Pair::of));

    @Override
    public @NotNull Collection<ResourceLocation> getDependencies() {
        return models.values().stream().flatMap(data -> Stream.of(data.top(), data.bottom())).toList();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
        getDependencies().forEach(resolver::apply);
    }

    @Override
    public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
        return KRenderer.getDefault().bakedModelFactory()
                .wrap(new CharacterBakedModel(models.entrySet().stream().map(entry -> {
                    CharacterModelData<BakedModelCore<Object>> bakedModels = new CharacterModelData<>(
                                    bakeModelWithDependencies(
                                            baker.getModel(entry.getValue().top()),
                                            baker,
                                            spriteGetter,
                                            state
                                    ),
                                    bakeModelWithDependencies(
                                            baker.getModel(entry.getValue().bottom()),
                                            baker,
                                            spriteGetter,
                                            state
                                    )
                            );
                    return Pair.of(entry.getKey(), bakedModels);
                }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))));
    }

    @SuppressWarnings({"unchecked", "OverrideOnly"})
    private static BakedModelCore<Object> bakeModelWithDependencies(UnbakedModel model, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
        model.resolveParents(baker::getModel);
        BakedModel bakedModel = model.bake(baker, spriteGetter, state);
        if (bakedModel instanceof BakedModelCoreProvider coreProvider)
            return (BakedModelCore<Object>) coreProvider.krender$getCore();
        return (BakedModelCore<Object>) KRenderer.getDefault().bakedModelUnwrapper().unwrap(bakedModel);
    }
}
