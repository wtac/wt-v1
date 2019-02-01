package info.wutao.v1.api.cart;

import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.base.IBaseService;
import info.wutao.v1.pojo.MultiResultBean;

public interface ICartService{
    MultiResultBean addToCart(String uuid, long productId, int count);

    MultiResultBean resetCount(String uuid, long productId, int count);

    MultiResultBean remove(String uuid, long productId);

    MultiResultBean getCart(String uuid);
}
