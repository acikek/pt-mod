package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ElementRefinedState<D> extends ContentBase<D, ContentContext.State> {

    /**
     * @return the instance-specific identifier for this refined state
     */
    @NotNull Identifier getId();

    default boolean isInstance(Identifier id) {
        return getId().equals(id);
    }

    default boolean isMain() {
        return isInstance(RefinedStates.MAIN);
    }
}
