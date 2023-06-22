package com.acikek.pt.core.api.refined;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import com.acikek.pt.core.api.content.element.ContentContext;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface ElementRefinedState extends ElementContentBase<ContentContext.State> {

    /**
     * The content type for {@link ContentIdentifier}s.
     */
    Identifier CONTENT_TYPE = PT.id("refined_state");

    /**
     * @return whether this state is the primary state for the parent element
     */
    boolean isPrimary();

    /**
     * @return this refined state with {@link ElementRefinedState#isPrimary()} set to {@code true}
     */
    ElementRefinedState primary();

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

    @Override
    default ElementRefinedState extend(ElementContentBase<ContentContext.State> extension) {
        ElementContentBase.super.extend(extension);
        return this;
    }

    @Override
    List<ElementRefinedState> extensions();

    @Override
    default List<ElementRefinedState> allContent() {
        List<ElementRefinedState> list = new ArrayList<>();
        list.add(this);
        list.addAll(extensions());
        return list;
    }

    @Override
    @Nullable ElementRefinedState root();
}
