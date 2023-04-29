package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ElementRefinedState extends ContentBase<ContentContext.State> {

    /**
     * @return the instance-specific identifier for this refined state
     */
    @NotNull Identifier getId();
}
