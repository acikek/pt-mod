package com.acikek.pt.api.impl;

public class PTApiImpl {

    public static String subscript(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            result.append(Character.toString(0x2080 + (c - 48)));
        }
        return result.toString();
    }
}
