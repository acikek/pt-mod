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
        public ElementIds<String> append(String suffix) {
            return ElementIds.create(get(suffix));
        }

        @Override
        public ElementIds<String> useString() {
            return this;
        }

        @Override
        public ElementIds<Identifier> useIdentifier(String key) {
            return ElementIds.create(new Identifier(key, value));
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
        public ElementIds<Identifier> append(String suffix) {
            return ElementIds.create(get(suffix));
        }

        @Override
        public ElementIds<String> useString() {
            return ElementIds.create(value.getPath());
        }

        @Override
        public ElementIds<Identifier> useIdentifier(String key) {
            return new IdentifierBacked(new Identifier(key, value.getPath()));
        }
    }
}
