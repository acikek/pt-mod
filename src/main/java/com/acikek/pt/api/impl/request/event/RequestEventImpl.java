package com.acikek.pt.api.impl.request.event;

import com.acikek.pt.api.request.event.ContentRequestEvent;
import com.acikek.pt.api.request.event.MineralRequestEvent;
import com.acikek.pt.api.request.event.RequestEvent;

public class RequestEventImpl implements RequestEvent {

    private final MineralRequestEvent minerals = new MineralRequestImpl();
    private final ContentRequestEvent states = new ContentRequestImpl();
    private final ContentRequestEvent sources = new ContentRequestImpl();

    @Override
    public MineralRequestEvent minerals() {
        return minerals;
    }

    @Override
    public ContentRequestEvent states() {
        return states;
    }

    @Override
    public ContentRequestEvent sources() {
        return sources;
    }

    @Override
    public String toString() {
        return "RequestEventImpl{" +
                "minerals=" + minerals +
                ", states=" + states +
                ", sources=" + sources +
                '}';
    }
}
