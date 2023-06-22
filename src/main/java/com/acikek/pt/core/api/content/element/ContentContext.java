package com.acikek.pt.core.api.content.element;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.registry.ElementIds;

public abstract class ContentContext {

    private final Element element;
    private final ElementIds<String> contentIds;

    private ContentContext(Element element, ElementIds<String> contentIds) {
        this.element = element;
        this.contentIds = contentIds;
    }

    /**
     * @return the parent element that this content is attached to
     */
    public Element element() {
        return element;
    }

    /**
     * @return {@link Element#elementIds()}. If this content <em>is or has a parent of</em> an {@link ElementRefinedState}
     * that <b>isn't</b> primary, these IDs are instance-specific.
     */
    public ElementIds<String> contentIds() {
        return contentIds;
    }

    public static class State extends ContentContext {

        public State(Element element, ElementIds<String> contentIds) {
            super(element, contentIds);
        }
    }

    public static class Source extends ContentContext {

        private final ElementRefinedState state;

        public Source(Element element, ElementIds<String> contentIds, ElementRefinedState state) {
            super(element, contentIds);
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
