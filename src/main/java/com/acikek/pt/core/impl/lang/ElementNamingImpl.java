package com.acikek.pt.core.impl.lang;

import com.acikek.pt.core.lang.ElementNaming;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class ElementNamingImpl implements ElementNaming {

    private final String id;
    private final String name;
    private final String symbol;

    public ElementNamingImpl(String id, String name, String symbol) {
        Stream.of(id, name, symbol).forEach(Objects::requireNonNull);
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public @NotNull String id() {
        return id;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull String symbol() {
        return symbol;
    }
}
