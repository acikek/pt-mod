package com.acikek.pt.core.api.content.element;

import net.minecraft.util.Identifier;

import java.util.Objects;

public class ContentIdentifier extends Identifier {

    private final Identifier type;

    public ContentIdentifier(Identifier type, String namespace, String path) {
        super(namespace, path);
        Objects.requireNonNull(type);
        this.type = type;
    }

    public ContentIdentifier(Identifier type, Identifier id) {
        this(type, id.getNamespace(), id.getPath());
    }

    public Identifier type() {
        return type;
    }

    public Identifier base() {
        return new Identifier(getNamespace(), getPath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ContentIdentifier that = (ContentIdentifier) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return type + "[" + super.toString() + "]";
    }
}
