package info.wutao.v1.entity;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {

    private Long productId;

    private int count;

    public CartItem() {
    }

    public CartItem(Long productId, int count, Date updateTime) {
        this.productId = productId;
        this.count = count;
        this.updateTime = updateTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Date updateTime;
}
