package com.acikek.pt.core.impl.registry;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.registry.ElementIds;
import net.minecraft.util.Identifier;

public class ElementIdImpls {

    public static class StringBacked extends ElementIds<String> {

        public StringBacked(String value) {
            super(value);
        }

        @Override
        public String get(String suffix) {
            return value + suffix;
        }

        @Override
        public ElementIds<String> useString() {
            return this;
        }

        @Override
        public ElementIds<Identifier> useIdentifier() {
            return ElementIds.create(PT.id(value));
        }
    }

    public static class IdentifierBacked extends ElementIds<Identifier> {

        public IdentifierBacked(Identifier value) {
            super(value);
        }

        @Override
        public Identifier get(String suffix) {
            return value.withSuffixedPath(suffix);
        }

        @Override
        public ElementIds<String> useString() {
            return ElementIds.create(value.getPath());
        }

        @Override
        public ElementIds<Identifier> useIdentifier() {
            return this;
        }
    }
}
