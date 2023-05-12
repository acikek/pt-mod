package com.acikek.pt.api.impl.request;

import com.acikek.pt.api.request.ContentRequestEvent;
import com.acikek.pt.api.request.MineralRequestEvent;
import com.acikek.pt.api.request.RequestEvent;

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
