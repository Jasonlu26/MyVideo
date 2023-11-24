package com.yx.play.util;

public enum TestPosId {
    APP_ID("1172943670384"), // appId
    APP_KEY("9690a8558345e3b7"), // appKey
    POSID_SPLASH("afkr6wrpbc08"), // 开屏PosId
    POSID_INTERSTITIAL("afkr8e1jhe72"), // 插屏PosId
    POSID_REWARD("a0ihx57cf7ug"),// 激励视频PosId
    POSID_FEED("a0ii114xb7kk"),// 信息流PosId
    POSID_CONTENT("a0ii32i4v7m0"),// 视频流PosId
    POSID_CPU("a0ii4sgr2ohu"),// 资讯PosId
    POSID_FULLSCREEN("a0ii74evokgw"),// 全屏视频PosId
    POSID_BANNER("a0ii9eb7k73e");// 横幅PosId


    public String value;

    TestPosId(String posId) {
        this.value = posId;
    }
}
