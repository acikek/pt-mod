package com.acikek.pt.core.impl.refined;

import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.phase.PhasedContent;
import com.acikek.pt.core.api.refined.RefinedStateData;
import com.acikek.pt.core.api.refined.RefinedStateType;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FluidRefinedState extends BaseRefinedState<RefinedStateData.HasFluid> {

    private final PhasedContent<Fluid> fluid;

    public FluidRefinedState(Identifier id, PhasedContent<Item> item, PhasedContent<Item> miniItem, PhasedContent<Block> block, PhasedContent<Fluid> fluid) {
        super(id, item, miniItem, block, RefinedStateType.FLUID);
        Objects.requireNonNull(fluid);
        this.fluid = fluid;
    }

    @Override
    public @NotNull ContentIdentifier typeId() {
        return RefinedStates.FLUID;
    }

    @Override
    public boolean isType(ContentIdentifier id) {
        return super.isType(id) || id.equals(RefinedStates.BASE);
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        super.buildTranslations(builder);
        fluid.require(fluid -> builder.add(fluid.getDefaultState().getBlockState().getBlock(), parent().display().englishName()));
        if (isMain()) {
            builder.add(parent().getConventionalFluidKey("%s"), parent().display().englishName());
        }
    }

    @Override
    public void buildFluidTags(PTTagProviders.FluidTagProvider provider) {
        fluid.require(fluid -> provider.add(parent().getConventionalFluidTag("%s"), fluid));
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        super.register(registry, features);
        if (features.contains(RequestTypes.CONTENT)) {
            fluid.create(fluid -> registry.registerFluid(contentIds().getFluidId(), fluid));
        }
    }

    @Override
    public RefinedStateData.HasFluid getData() {
        return new RefinedStateData.HasFluid(block.get(), item.get(), miniItem.get(), fluid.get());
    }
}
