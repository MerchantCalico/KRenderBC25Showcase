package net.merchantcalico.krendershowcase.block.entity;

import net.merchantcalico.krendershowcase.KRenderShowcase;
import net.merchantcalico.krendershowcase.block.CharacterBlock;
import net.merchantcalico.krendershowcase.data.CharacterData;
import net.merchantcalico.krendershowcase.registry.ShowcaseBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class CharacterBlockEntity extends BlockEntity {
    private Holder<CharacterData> data;

    public CharacterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ShowcaseBlockEntityTypes.CHARACTER, pos, blockState);
    }

    public Holder<CharacterData> getCharacterData() {
        return data;
    }

    public void use(Player player) {
        if (!player.level().isClientSide || getCharacterData() == null)
            return;
        for (var descriptionValue : getCharacterData().value().description())
            player.sendSystemMessage(descriptionValue);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CharacterBlockEntity blockEntity) {
        if (state.getValue(CharacterBlock.HALF) == DoubleBlockHalf.LOWER && (blockEntity.data == null || level.getGameTime() % 120 == 0)) {
            var validData = level.registryAccess().registry(KRenderShowcase.CHARACTER).orElseThrow().holders().filter(characterData -> characterData != blockEntity.data).toList();
            var newData = validData.get(level.getRandom().nextInt(validData.size()));
            if (newData != blockEntity.data) {
                blockEntity.updateCharacterData(newData);
                var otherBlockEntity = level.getBlockEntity(state.getValue(CharacterBlock.HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below(), ShowcaseBlockEntityTypes.CHARACTER);
                otherBlockEntity.ifPresent(be -> be.updateCharacterData(newData));
            }
        }
    }

    private void updateCharacterData(Holder<CharacterData> newData) {
        data = newData;
        if (hasLevel()) {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("data"))
            updateCharacterData(CharacterData.CODEC.decode(registries.createSerializationContext(NbtOps.INSTANCE), tag.get("data")).getOrThrow().getFirst());
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
