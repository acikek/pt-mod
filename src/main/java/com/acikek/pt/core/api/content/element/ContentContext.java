package com.acikek.pt.core.api.content.element;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.registry.ElementIds;

public abstract class ContentContext {

    private final Element parent;
    private final ElementIds<String> contentIds;

    private ContentContext(Element parent, ElementIds<String> contentIds) {
        this.parent = parent;
        this.contentIds = contentIds;
    }

    /**
     * @return the parent element that this content is attached to
     */
    public Element parent() {
        return parent;
    }

    /**
     * @return {@link Element#elementIds()}. If this content <em>is or has a parent of</em> an {@link ElementRefinedState}
     * that <b>isn't</b> primary, these IDs are instance-specific.
     */
    public ElementIds<String> contentIds() {
        return contentIds;
    }

    public static class State extends ContentContext {

        public State(Element parent, ElementIds<String> contentIds) {
            super(parent, contentIds);
        }
    }

    public static class Source extends ContentContext {

        private final ElementRefinedState state;

        public Source(Element parent, ElementIds<String> contentIds, ElementRefinedState state) {
            super(parent, contentIds);
            this.state = state;
        }

        /**
         * @return the refined state that this source is attached to
         */
        public ElementRefinedState state() {
            return this.state;
        }
    }
}
