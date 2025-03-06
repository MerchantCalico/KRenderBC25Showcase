package net.merchantpug.krendershowcase.client.model.baked;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kneelawk.krender.engine.api.KRenderer;
import com.kneelawk.krender.engine.api.TriState;
import com.kneelawk.krender.engine.api.buffer.QuadEmitter;
import com.kneelawk.krender.engine.api.material.BlendMode;
import com.kneelawk.krender.engine.api.material.RenderMaterial;
import com.kneelawk.krender.engine.api.mesh.Mesh;
import com.kneelawk.krender.engine.api.mesh.MeshBuilder;
import com.kneelawk.krender.engine.api.model.BakedModelCore;
import com.kneelawk.krender.engine.api.model.ModelBlockContext;
import com.kneelawk.krender.engine.api.model.ModelItemContext;
import com.kneelawk.krender.engine.api.model.ModelUtils;
import net.merchantpug.krendershowcase.registry.ShowcaseDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class MiniBlockBakedModel implements BakedModelCore<Void> {
    private static final RenderMaterial GLOW_MATERIAL =
            KRenderer.getDefault().materialManager().materialFinder().setBlendMode(BlendMode.CUTOUT).setEmissive(true)
                    .setDiffuseDisabled(true).setAmbientOcclusionMode(TriState.FALSE).find();
    private final Cache<BlockState, Mesh> meshes = CacheBuilder.newBuilder()
            .maximumSize(256)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ResourceLocation.withDefaultNamespace("block/iron_block"));
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
    public Void getBlockKey(ModelBlockContext ctx) {
        return null;
    }

    @Override
    public void renderBlock(QuadEmitter quadEmitter, Void unused) {

    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void renderItem(QuadEmitter renderTo, ModelItemContext ctx) {
        if (ctx.stack().getComponents().has(ShowcaseDataComponents.BLOCK_STATE)) {
            BlockState state = ctx.stack().getComponents().get(ShowcaseDataComponents.BLOCK_STATE);
            if (!meshes.asMap().containsKey(state))
                meshes.put(state, buildMesh(state));
            meshes.asMap().get(state).outputTo(renderTo);
        }
    }

    private static Mesh buildMesh(BlockState state) {
        MeshBuilder meshBuilder = KRenderer.getDefault().meshBuilder();
        QuadEmitter emitter = meshBuilder.emitter();
        for (Direction direction : Direction.values()) {
            emitter.square(direction, 0.25f, 0.25f, 0.75f, 0.75f, 0.25f);
            emitter.setUv(0, 0f, 0f);
            emitter.setUv(1, 0f, 1f);
            emitter.setUv(2, 1f, 1f);
            emitter.setUv(3, 1f, 0f);
            emitter.spriteBake(Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getParticleIcon(), QuadEmitter.BAKE_ROTATE_NONE);
            emitter.setQuadColor(-1, -1, -1, -1);
            emitter.setColorIndex(-1);
            emitter.setMaterial(GLOW_MATERIAL);
            emitter.emit();
        }

        return meshBuilder.build();
    }
}