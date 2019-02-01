package info.wutao.v1.api.vo;

import java.util.Date;

public class CartVO {

    private Long id;

    private String name;

    private String image;

    private Long price;

    private int count;

    private Date updateTime;

    public CartVO() {
    }

    public CartVO(Long id, String name, String image, Long price, int count, Date updateTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.count = count;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "CartVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", updateTime=" + updateTime +
                '}';
    }
}
