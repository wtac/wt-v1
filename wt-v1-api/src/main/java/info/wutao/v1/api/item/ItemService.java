package info.wutao.v1.api.item;

import info.wutao.v1.entity.Product;
import info.wutao.v1.pojo.MultiResultBean;

public interface ItemService {

    public MultiResultBean createPage (Product product);
}
