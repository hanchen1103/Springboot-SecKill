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

    public static String getLikeKey(int entityType, int entityId) {
        return REDIS_LIKE + REDIS_SPLIT + entityType + REDIS_SPLIT + entityId;
    }


}
