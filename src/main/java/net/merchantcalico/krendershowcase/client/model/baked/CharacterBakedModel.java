package net.merchantcalico.krendershowcase.client.model.baked;

import com.kneelawk.krender.engine.api.buffer.QuadEmitter;
import com.kneelawk.krender.engine.api.model.*;
import com.mojang.datafixers.util.Pair;
import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.block.CharacterBlock;
import net.merchantcalico.krendershowcase.block.entity.CharacterBlockEntity;
import net.merchantcalico.krendershowcase.client.model.CharacterModelData;
import net.merchantcalico.krendershowcase.data.CharacterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Map;

public record CharacterBakedModel(Map<ResourceKey<CharacterData>, CharacterModelData<BakedModelCore<Object>>> models) implements BakedModelCore<Pair<BakedModelCore<Object>, Object>> {
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
    @SuppressWarnings("deprecation")
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(KRenderShowcase.asResource("item/character"));
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return ModelUtils.BLOCK_DISPLAY;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    @Nullable
    public Pair<BakedModelCore<Object>, Object> getBlockKey(ModelBlockContext modelBlockContext) {
        if (modelBlockContext.level().getBlockEntity(modelBlockContext.pos()) instanceof CharacterBlockEntity characterBlockEntity) {
            if (characterBlockEntity.getCharacterData() == null)
                return null;
            var data = models.get(characterBlockEntity.getCharacterData().unwrapKey().orElseThrow());
            if (data == null)
                return null;
            if (modelBlockContext.state().getValue(CharacterBlock.HALF) == DoubleBlockHalf.UPPER)
                return Pair.of(data.top(), data.top().getBlockKey(modelBlockContext));
            return Pair.of(data.bottom(), data.bottom().getBlockKey(modelBlockContext));
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
