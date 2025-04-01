package net.merchantcalico.krendershowcase.client.model.unbaked;

import com.kneelawk.krender.engine.api.KRenderer;
import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.client.model.baked.DiscoFloorBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * MIT License
 * <p>
 * Copyright (c) 2024 Cyan Kneelawk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class DiscoFloorUnbakedModel implements UnbakedModel {
    @Override
    public @NotNull Collection<ResourceLocation> getDependencies() {
        return List.of();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {

    }

    @Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter,
                                     ModelState state) {
        Function<String, TextureAtlasSprite> material =
            (String str) -> spriteGetter.apply(new Material(TextureAtlas.LOCATION_BLOCKS, KRenderShowcase.asResource(str)));
        return KRenderer.getDefault().bakedModelFactory()
            .wrap(new DiscoFloorBakedModel(material.apply("block/disco_floor"), new TextureAtlasSprite[]{
                material.apply("block/disco_floor_convex"),
                material.apply("block/disco_floor_horizontal"),
                material.apply("block/disco_floor_vertical"),
                material.apply("block/disco_floor_concave"),
                material.apply("block/disco_floor_center")
            }, new TextureAtlasSprite[]{
                material.apply("block/disco_floor_glow_convex"),
                material.apply("block/disco_floor_glow_horizontal"),
                material.apply("block/disco_floor_glow_vertical"),
                material.apply("block/disco_floor_glow_concave"),
                material.apply("block/disco_floor_glow_center")
            }));
    }
}