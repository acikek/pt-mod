package com.acikek.pt.core.impl.registry;

import com.acikek.pt.PT;
import com.acikek.pt.core.registry.ElementIds;
import net.minecraft.util.Identifier;

public class ElementIdImpls {

    public static class StringBacked extends ElementIds<String> {

        public StringBacked(String value) {
            super(value);
        }

        @Override
        protected String addSuffix(String suffix) {
            return value + suffix;
        }

        @Override
        public ElementIds<String> useString() {
            return this;
        }

        @Override
        public ElementIds<Identifier> useIdentifier() {
            return ElementIds.get(PT.id(value));
        }
    }

    public static class IdentifierBacked extends ElementIds<Identifier> {

        public IdentifierBacked(Identifier value) {
            super(value);
        }

        @Override
        protected Identifier addSuffix(String suffix) {
            return value.withSuffixedPath(suffix);
        }

        @Override
        public ElementIds<String> useString() {
            return ElementIds.get(value.getPath());
        }

        @Override
        public ElementIds<Identifier> useIdentifier() {
            return this;
        }
    }
}
