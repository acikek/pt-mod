package com.acikek.pt.core.api.refined;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.ElementContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ElementRefinedState<D> extends ElementContentBase<D, ContentContext.State> {

    /**
     * The instance ID of the <b>primary</b> refined state of an element.
     */
    Identifier MAIN = PT.id("main");

    /**
     * @return the instance-specific identifier for this refined state
     */
    @NotNull Identifier id();

    /**
     * @return whether this refined state is an instance of the specified id
     * @see RefinedStates
     * @see ElementRefinedState#id()
     */
    default boolean isInstance(Identifier id) {
        return id().equals(id);
    }

    /**
     * @return whether the instance ID of this refined state is {@link ElementRefinedState#MAIN}
     */
    default boolean isMain() {
        return isInstance(MAIN);
    }
}
