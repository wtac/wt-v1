package info.wutao.v1.entity;

import java.io.Serializable;

public class ProductDesc implements Serializable {

    private Long id;

    private Long productId;

    private String productDesc;

    public ProductDesc() {
    }

    public ProductDesc(Long id, Long productId, String productDesc) {
        this.id = id;
        this.productId = productId;
        this.productDesc = productDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Override
    public String toString() {
        return "ProductDesc{" +
                "id=" + id +
                ", productId=" + productId +
                ", productDesc='" + productDesc + '\'' +
                '}';
    }
}