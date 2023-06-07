package com.acikek.pt.core.api.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import net.minecraft.util.Identifier;

public interface ElementSource<D> extends ElementContentBase<D, ContentContext.Source> {

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
    default ElementRefinedState<?> state() {
        return context().state();
    }
}
