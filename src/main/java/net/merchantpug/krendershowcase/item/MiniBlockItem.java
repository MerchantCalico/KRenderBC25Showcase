package net.merchantpug.krendershowcase.item;

import net.merchantpug.krendershowcase.registry.ShowcaseDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MiniBlockItem extends Item {
    public MiniBlockItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state != context.getItemInHand().get(ShowcaseDataComponents.BLOCK_STATE) && state.isCollisionShapeFullBlock(context.getLevel(), context.getClickedPos())) {
            context.getItemInHand().set(ShowcaseDataComponents.BLOCK_STATE, state);
            context.getPlayer().getCooldowns().addCooldown(this, 20);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.krendershowcase.mini_block.tooltip"));
    }
}
