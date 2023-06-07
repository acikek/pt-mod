package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AtmosphericSource extends BaseSource<Range<Integer>> {

    private final Range<Integer> range;
    private ContentContext.Source context;

    public AtmosphericSource(Identifier id, Range<Integer> range) {
        super(id);
        this.range = range;
    }

    @Override
    public ContentContext.Source context() {
        return context;
    }

    @Override
    public void setContext(ContentContext.Source context) {
        super.setContext(context);
        this.context = context;
    }

    @Override
    public @NotNull ContentIdentifier typeId() {
        return ElementSources.ATMOSPHERIC;
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        // Empty
    }

    @Override
    public Item mineralResultItem() {
        return null;
    }

    @Override
    public List<Block> getBlocks() {
        return Collections.emptyList();
    }

    @Override
    public List<Item> getItems() {
        return Collections.emptyList();
    }

    @Override
    public Range<Integer> getData() {
        return range;
    }
}
