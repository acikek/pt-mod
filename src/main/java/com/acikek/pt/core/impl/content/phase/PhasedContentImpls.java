package com.acikek.pt.core.impl.content.phase;

import com.acikek.pt.core.api.content.phase.ContentPhase;
import com.acikek.pt.core.api.content.phase.PhasedContent;

import java.util.Optional;
import java.util.function.Supplier;

public class PhasedContentImpls {

    public static class Internal<T> extends PhasedContent<T> {

        private ContentPhase phase = ContentPhase.INTERNAL;

        public Internal(Supplier<T> supplier) {
            super(supplier);
        }

        @Override
        public ContentPhase phase() {
            return phase;
        }

        @Override
        protected T createContent() {
            phase = ContentPhase.REQUESTED;
            value = Optional.of(supplier.get());
            supplier = null;
            return value.get();
        }
    }

    public static class External<T> extends PhasedContent<T> {

        private final T created;

        public External(T created) {
            super(null);
            this.created = created;
        }

        @Override
        public ContentPhase phase() {
            return ContentPhase.EXTERNAL;
        }

        @Override
        protected T createContent() {
            value = Optional.of(created);
            return created;
        }
    }

    public static class Null<T> extends PhasedContent<T> {

        public Null() {
            super(() -> null);
        }

        @Override
        public ContentPhase phase() {
            return ContentPhase.NULL;
        }

        @Override
        protected T createContent() {
            return null;
        }
    }
}
