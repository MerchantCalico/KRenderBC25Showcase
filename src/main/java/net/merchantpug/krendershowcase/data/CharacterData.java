package net.merchantpug.krendershowcase.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.krendershowcase.KRenderShowcase;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;

import java.util.List;

public record CharacterData(List<Component> description) {
    public static final Codec<CharacterData> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ComponentSerialization.CODEC.listOf().optionalFieldOf("description", List.of()).forGetter(CharacterData::description)
    ).apply(inst, CharacterData::new));
    public static final Codec<Holder<CharacterData>> CODEC = RegistryFixedCodec.create(KRenderShowcase.CHARACTER);
}
