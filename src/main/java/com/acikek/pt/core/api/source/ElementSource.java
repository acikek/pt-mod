package com.acikek.pt.core.api.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface ElementSource extends ElementContentBase<ContentContext.Source> {

    /**
     * The content type for {@link ContentIdentifier}s.
     */
    Identifier CONTENT_TYPE = PT.id("source");

    /**
     * @return a content identifier wrapper using the {@link ElementSource#CONTENT_TYPE}
     */
    static ContentIdentifier id(Identifier of) {
        return new ContentIdentifier(CONTENT_TYPE, of);
    }

    /**
     * @see ElementSource#id(Identifier)
     */
    static ContentIdentifier id(String of) {
        return id(PT.id(of));
    }

    /**
     * @see ContentContext.Source#state()
     */
    default ElementRefinedState state() {
        return context().state();
    }

    @Override
    default ContentContext.Source createContext(ElementContentBase<ContentContext.Source> alike) {
        var source = (ElementSource) alike;
        return element().getSourceContext(source, state());
    }

    @Override
    default ElementSource extend(ElementContentBase<ContentContext.Source> extension) {
        ElementContentBase.super.extend(extension);
        return this;
    }

    @Override
    @NotNull
    List<ElementSource> extensions();

    @Override
    default List<ElementSource> allContent() {
        List<ElementSource> list = new ArrayList<>();
        list.add(this);
        list.addAll(extensions());
        return list;
    }

    @Override
    @Nullable ElementSource root();
}
