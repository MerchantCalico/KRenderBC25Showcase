package net.merchantpug.krendershowcase.client.model;

import com.kneelawk.krender.engine.api.KRenderer;
import com.kneelawk.krender.engine.api.buffer.QuadEmitter;
import com.kneelawk.krender.engine.api.model.*;
import com.kneelawk.krender.engine.base.model.BakedModelCoreProvider;
import com.mojang.datafixers.util.Pair;
import net.merchantpug.krendershowcase.block.CharacterBlock;
import net.merchantpug.krendershowcase.block.entity.CharacterBlockEntity;
import net.merchantpug.krendershowcase.data.CharacterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record CharacterBakedModel(Map<ResourceKey<CharacterData>, List<CharacterModelData<BakedModel>>> models) implements BakedModelCore<Pair<BakedModelCore<Object>, Object>> {

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ResourceLocation.withDefaultNamespace("block/stone"));
    }

    @Override
    public ItemTransforms getTransforms() {
        return ModelUtils.BLOCK_DISPLAY;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    @Nullable
    public Pair<BakedModelCore<Object>, Object> getBlockKey(ModelBlockContext modelBlockContext) {
        if (modelBlockContext.level().getBlockEntity(modelBlockContext.pos()) instanceof CharacterBlockEntity characterBlockEntity) {
            if (characterBlockEntity.getCharacterData() == null)
                return null;
            var list = models.getOrDefault(characterBlockEntity.getCharacterData().unwrapKey().orElseThrow(), List.of());
            var modelData = list.get((int)characterBlockEntity.getLevel().getGameTime() % list.size());
            BakedModel bakedModel;
            if (modelBlockContext.state().getValue(CharacterBlock.HALF) == DoubleBlockHalf.UPPER && modelData.top() != null)
                bakedModel = modelData.top();
            else if (modelData.bottom() != null)
                bakedModel = modelData.bottom();
            else
                return null;
            if (bakedModel instanceof BakedModelCoreProvider coreProvider)
                return Pair.of((BakedModelCore<Object>) coreProvider.krender$getCore(), coreProvider.krender$getCore().getBlockKey(modelBlockContext));
            var core = KRenderer.getDefault().bakedModelUnwrapper().unwrap(bakedModel);
            return Pair.of((BakedModelCore<Object>) core, core.getBlockKey(modelBlockContext));
        }
        return null;
    }

    @Override
    public void renderBlock(QuadEmitter quadEmitter, @UnknownNullability Pair<BakedModelCore<Object>, Object> pair) {
        if (pair != null)
            pair.getFirst().renderBlock(quadEmitter, pair.getSecond());
    }

    @Override
    public void renderItem(QuadEmitter quadEmitter, ModelItemContext modelItemContext) {

    }
}
