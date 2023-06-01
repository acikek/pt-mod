package com.acikek.pt.core.api.content.phase;

public enum ContentPhase {
    /**
     * Content that never exists.
     */
    NULL,
    /**
     * Content that exists externally <b>in full</b>.<br>
     * Requests should not affect this content.
     */
    EXTERNAL,
    /**
     * Content that exists internally but hasn't been created yet.
     */
    INTERNAL,
    /**
     * Content that exists internally and
     * has been created upon request.
     */
    REQUESTED
}
