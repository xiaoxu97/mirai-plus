package com.zhuangxv.miraiplus.enums;

public enum FacePlus {
    jingya(0, "惊讶");

    private final int faceId;

    private final String name;

    private FacePlus(int faceId, String name) {
        this.faceId = faceId;
        this.name = name;
    }

    public int getFaceId() {
        return faceId;
    }

    public String getName() {
        return name;
    }
}
