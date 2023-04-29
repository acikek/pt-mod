package com.acikek.pt.core.api.source;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ElementSource extends ContentBase<ContentContext.Source> {
}
