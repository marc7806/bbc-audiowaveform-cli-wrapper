package com.github.marc7806.wrapper;

public enum AWFBit {
    EIGHT("8"), SIXTEEN("16");
    private final String val;

    AWFBit(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}