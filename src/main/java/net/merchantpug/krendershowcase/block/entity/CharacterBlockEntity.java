package net.merchantpug.krendershowcase.block.entity;

import net.merchantpug.krendershowcase.KRenderShowcase;
import net.merchantpug.krendershowcase.data.CharacterData;
import net.merchantpug.krendershowcase.registry.ShowcaseBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CharacterBlockEntity extends BlockEntity {
    private Holder<CharacterData> data;

    public CharacterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ShowcaseBlockEntityTypes.CHARACTER, pos, blockState);
    }

    public Holder<CharacterData> getCharacterData() {
        return data;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CharacterBlockEntity blockEntity) {
        if (blockEntity.data == null || level.getGameTime() % 120 == 0) {
            var newData = level.registryAccess().registry(KRenderShowcase.CHARACTER).orElseThrow().getRandom(level.random).orElseThrow();
            if (newData != blockEntity.data) {
                blockEntity.data = newData;
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
            }
        }
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("data"))
            data = CharacterData.CODEC.decode(registries.createSerializationContext(NbtOps.INSTANCE), tag.get("data")).getOrThrow().getFirst();

        if (hasLevel())
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (data != null)
            tag.put("data", CharacterData.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), data).getOrThrow());
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

}
