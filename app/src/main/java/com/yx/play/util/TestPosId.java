package com.yx.play.util;

public enum TestPosId {
    APP_ID("1019924923360"), // appId
    APP_KEY("e71ab76c990bdb3a"), // appKey
    POSID_SPLASH("a0ihndcbpu64"), // 开屏PosId
    POSID_INTERSTITIAL("a0ihv3h7c75a"), // 插屏PosId
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
