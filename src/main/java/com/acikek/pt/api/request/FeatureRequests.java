package com.acikek.pt.api.request;

import net.minecraft.util.Identifier;

import java.util.*;

public class FeatureRequests<T> {

    public static class Single extends FeatureRequests<List<Identifier>> {

        public Single(boolean all, List<Identifier> requests) {
            super(all, requests);
        }

        private static Single emptyWithAll(boolean all) {
            return new Single(all, new ArrayList<>());
        }

        public static Single empty() {
            return emptyWithAll(false);
        }

        public static Single useAll() {
            return emptyWithAll(true);
        }

        public boolean contains(Identifier type) {
            return all() || requests().contains(type);
        }

        @Override
        public String toString() {
            return "Single" + getString();
        }
    }

    public static class Content extends FeatureRequests<Map<Identifier, Single>> {

        public Content(boolean all, Map<Identifier, Single> requests) {
            super(all, requests);
        }

        private static Content emptyWithAll(boolean all) {
            return new Content(all, new HashMap<>());
        }

        public static Content empty() {
            return emptyWithAll(false);
        }

        public static Content useAll() {
            return emptyWithAll(true);
        }

        public Single getContent(Identifier sourceId) {
            var content = requests().getOrDefault(sourceId, Single.empty());
            return new Single(all() || content.all(), content.requests());
        }

        @Override
        public String toString() {
            return "Content" + getString();
        }
    }

    private final boolean all;
    private final T requests;

    public FeatureRequests(boolean all, T requests) {
        this.all = all;
        this.requests = requests;
    }

    public boolean all() {
        return all;
    }

    public T requests() {
        return requests;
    }

    protected String getString() {
        return "{" + (all ? "all" : "requests=" + requests.toString()) + "}";
    }
}
