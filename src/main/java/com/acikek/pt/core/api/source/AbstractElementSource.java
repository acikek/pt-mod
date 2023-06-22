package com.acikek.pt.core.api.source;

import com.acikek.pt.core.api.content.element.AbstractElementContent;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A base implementation of {@link ElementSource} that future implementors may inherit from.
 * @see AbstractElementContent
 */
public abstract class AbstractElementSource extends AbstractElementContent<ContentContext.Source> implements ElementSource {

    private List<ElementSource> extensions;
    private ElementSource root = null;

    protected AbstractElementSource(Identifier id) {
        super(id);
    }

    @Override
    public ElementSource extend(ElementContentBase<ContentContext.Source> extension) {
        super.extend(extension);
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        extensions.add((ElementSource) extension);
        return this;
    }

    @Override
    public @Nullable ElementSource root() {
        return root;
    }

    @Override
    public void setRoot(ElementContentBase<ContentContext.Source> root) {
        this.root = (ElementSource) root;
    }

    @Override
    public @NotNull List<ElementSource> extensions() {
        return extensions != null
                ? extensions
                : Collections.emptyList();
    }
}
