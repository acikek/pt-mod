package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.source.ElementSource;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class BaseSource<D> implements ElementSource<D> {

    private final Identifier id;

    public BaseSource(Identifier id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    @Override
    public @NotNull Identifier id() {
        return id;
    }
}
