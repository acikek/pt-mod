package com.acikek.pt.core.api.content;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;

public class ContentContext {

    private final Element parent;

    private ContentContext(Element parent) {
        this.parent = parent;
    }

    public Element parent() {
        return parent;
    }

    public static class State extends ContentContext {

        public State(Element parent) {
            super(parent);
        }
    }

    public static class Source extends ContentContext {

        private final ElementRefinedState state;

        public Source(Element parent, ElementRefinedState state) {
            super(parent);
            this.state = state;
        }

        public ElementRefinedState state() {
            return this.state;
        }
    }
}
