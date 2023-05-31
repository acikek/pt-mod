package com.acikek.pt.core.api.content;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface ContentBase {

    /**
     * Registers all content associated with this base.
     * @param registry the registry associated with the content's {@link AbstractPeriodicTable}
     * @param features features requested from this content type by the {@link RequestEvent}
     */
    void register(PTRegistry registry, FeatureRequests.Single features);

    /**
     * Initializes content on the client <b>after</b>
     * {@link ContentBase#register(PTRegistry, FeatureRequests.Single) has been called
     * on the common side.
     */
    @Environment(EnvType.CLIENT)
    default void initClient() {
        // Empty
    }
}
