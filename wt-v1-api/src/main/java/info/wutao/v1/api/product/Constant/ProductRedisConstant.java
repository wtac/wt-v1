package info.wutao.v1.api.product.Constant;

public interface ProductRedisConstant {

    /**
     * 后面接id
     */
    String PRODUCT_ID = "product:id:";

    /**
     * product分布式锁,后面接id
     */
    String PRODUCT_LOCK = "product:lock:";
}
