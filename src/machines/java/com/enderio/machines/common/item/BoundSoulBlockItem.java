package com.enderio.machines.common.item;

import com.enderio.api.capability.IMultiCapabilityItem;
import com.enderio.api.capability.MultiCapabilityProvider;
import com.enderio.base.common.capability.EntityStorageItemStack;
import com.enderio.base.common.init.EIOCapabilities;
import com.enderio.core.client.item.IAdvancedTooltipProvider;
import com.enderio.core.common.util.EntityUtil;
import com.enderio.core.common.util.TooltipUtil;
import com.enderio.machines.common.lang.MachineLang;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoundSoulBlockItem extends BlockItem implements IMultiCapabilityItem, IAdvancedTooltipProvider {

    public BoundSoulBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void addCommonTooltips(ItemStack itemStack, @Nullable Player player, List<Component> tooltips) {
        itemStack
            .getCapability(EIOCapabilities.ENTITY_STORAGE)
            .ifPresent(entityStorage -> entityStorage
                .getStoredEntityData()
                .getEntityType()
                .ifPresentOrElse(entityType -> tooltips.add(TooltipUtil.style(Component.translatable(EntityUtil.getEntityDescriptionId(entityType)))),
                    () -> tooltips.add(TooltipUtil.style(MachineLang.TOOLTIP_NO_SOULBOUND))));
    }

    @Override
    public @Nullable MultiCapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt, MultiCapabilityProvider provider) {
        provider.add(EIOCapabilities.ENTITY_STORAGE, LazyOptional.of(() -> new EntityStorageItemStack(stack)));
        return provider;
    }
}
