package net.merchantcalico.krendershowcase.client.model.unbaked;

import com.kneelawk.krender.engine.api.KRenderer;
import net.merchantcalico.krendershowcase.client.model.baked.MiniBlockBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class MiniBlockUnbakedModel implements UnbakedModel {
    @Override
    public @NotNull Collection<ResourceLocation> getDependencies() {
        return List.of();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {}

    @Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
        return KRenderer.getDefault()
                .bakedModelFactory().wrap(new MiniBlockBakedModel());
    }
}
