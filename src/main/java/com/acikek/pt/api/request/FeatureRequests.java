package com.acikek.pt.api.request;

import net.minecraft.util.Identifier;

import java.util.*;

public class FeatureRequests<T> {

    public static class Content extends FeatureRequests<List<Identifier>> {

        public Content(boolean all, List<Identifier> requests) {
            super(all, requests);
        }

        private static Content emptyWithAll(boolean all) {
            return new Content(all, new ArrayList<>());
        }

        public static Content empty() {
            return emptyWithAll(false);
        }

        public static Content useAll() {
            return emptyWithAll(true);
        }

        public boolean contains(Identifier type) {
            return all() || requests().contains(type);
        }
    }

    public static class Sources extends FeatureRequests<Map<Identifier, Content>> {

        public Sources(boolean all, Map<Identifier, Content> requests) {
            super(all, requests);
        }

        private static Sources emptyWithAll(boolean all) {
            return new Sources(all, new HashMap<>());
        }

        public static Sources empty() {
            return emptyWithAll(false);
        }

        public static Sources useAll() {
            return emptyWithAll(true);
        }

        public Content getContent(Identifier sourceId) {
            var content = requests().getOrDefault(sourceId, Content.empty());
            return new Content(all() || content.all(), content.requests());
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
}
