package com.kill.provider.util;

public class RedisKeyUtil {

    /**
     * 库存量
     */
    public final static String STOCK_COUNT = "STOCK-COUNT-";

    /**
     *已售数量
     */
    public final static String STOCK_SALE = "STOCK-SALE-";

    /**
     * 版本
     */
    public final static String STOCK_VERSION = "STOCK-VERSION-";

    /**
     * 购物车
     */
    public final static String SHOPPING_CAR_ID = "SHOPPING-CAR-ID-";

    public final static String REDIS_SPLIT = "-";

    public final static String REDIS_LIKE = "REDIS_LIKE";

    public final static String REDIS_WATER = "REDIS_WATER";

    public final static String REDIS_MONTH = "-MONTH-";

    public final static String REDIS_SEASON = "-SEASON-";

    public final static String REDIS_GAS = "REDIS_GAS";

    public final static String REDIS_POWER = "REDIS_POWER";

    public final static String REDIS_ACT = "REDIS_KILLACT";

    public final static String REDIS_PRODUCT_ACT = "REDIS_PRODUCT_ACT";

    public static String getREDIS_PRODUCT_ACT(int stockId) {
        return REDIS_PRODUCT_ACT + REDIS_SPLIT + stockId;
    }

    public static String getActKey(int entityId) {
        return REDIS_ACT + REDIS_SPLIT + entityId;
    }

    public static String getLikeKey(int entityType, int entityId) {
        return REDIS_LIKE + REDIS_SPLIT + entityType + REDIS_SPLIT + entityId;
    }

    public static String getWaterMonth(int uid) {
        return REDIS_WATER + REDIS_SPLIT + REDIS_MONTH + REDIS_SPLIT + uid;
    }

    public static String getWaterSeason(int uid) {
        return REDIS_WATER + REDIS_SPLIT + REDIS_SEASON + REDIS_SPLIT + uid;
    }

    public static String getGasMonth(int uid) {
        return REDIS_GAS + REDIS_SPLIT + REDIS_MONTH + REDIS_SPLIT + uid;
    }

    public static String getGasSeason(int uid) {
        return REDIS_GAS + REDIS_SPLIT + REDIS_SEASON + REDIS_SPLIT + uid;
    }

    public static String getPowerMonth(int uid) {
        return REDIS_POWER + REDIS_SPLIT + REDIS_MONTH + REDIS_SPLIT + uid;
    }

    public static String getPowerSeason(int uid) {
        return REDIS_POWER + REDIS_SPLIT + REDIS_SEASON + REDIS_SPLIT + uid;
    }

}
