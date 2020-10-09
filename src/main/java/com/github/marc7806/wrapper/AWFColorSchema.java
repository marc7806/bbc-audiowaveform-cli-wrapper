package com.github.marc7806.wrapper;

public enum AWFColorSchema {
    // blue waveform on a grey background
    AUDACITY("audacity"),
    // green waveform on a dark background
    AUDITION("audition");
    private final String val;

    AWFColorSchema(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}