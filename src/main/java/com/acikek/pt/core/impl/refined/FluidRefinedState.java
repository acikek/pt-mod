package com.acikek.pt.core.impl.refined;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.RefinedStateType;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class FluidRefinedState extends BaseRefinedState {

    private final PhasedContent<Fluid> fluid;

    public FluidRefinedState(Identifier id, Supplier<Item> item, Supplier<Item> miniItem, Supplier<Block> block, Supplier<Fluid> fluid) {
        super(id, item, miniItem, block, RefinedStateType.FLUID);
        Objects.requireNonNull(fluid);
        this.fluid = PhasedContent.of(fluid);
    }

    @Override
    public @NotNull Identifier getTypeId() {
        return RefinedStates.FLUID;
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        super.buildTranslations(builder, parent);
        builder.add(fluid.require().getDefaultState().getBlockState().getBlock(), parent.display().englishName());
    }

    @Override
    public void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, Element parent) {
        provider.apply(parent.getConventionalFluidTag("%s")).add(fluid.require());
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids, ContentContext.State context, FeatureRequests.Single features) {
        super.register(registry, ids, context, features);
        if (features.contains(RequestTypes.CONTENT)) {
            registry.registerFluid(ids.getFluidId(), fluid.create());
        }
    }
}
