package com.acikek.pt.core.api.refined;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import com.acikek.pt.core.api.content.element.ContentContext;
import net.minecraft.util.Identifier;

public interface ElementRefinedState<D> extends ElementContentBase<D, ContentContext.State> {

    /**
     * The content type for {@link ContentIdentifier}s.
     */
    Identifier CONTENT_TYPE = PT.id("refined_state");

    /**
     * @return a content identifier wrapper using the {@link ElementRefinedState#CONTENT_TYPE}
     */
    static ContentIdentifier id(Identifier of) {
        return new ContentIdentifier(CONTENT_TYPE, of);
    }

    /**
     * @see ElementRefinedState#id(Identifier)
     */
    static ContentIdentifier id(String of) {
        return id(PT.id(of));
    }
}
