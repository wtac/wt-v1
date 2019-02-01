package info.wutao.wtv1cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import info.wutao.v1.api.cart.ICartService;
import info.wutao.v1.api.cart.constant.CartCookieConstant;
import info.wutao.v1.api.cart.constant.CartRedisConstant;
import info.wutao.v1.api.product.Constant.ProductRedisConstant;
import info.wutao.v1.api.vo.CartVO;
import info.wutao.v1.base.BaseServiceImpl;
import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.entity.CartItem;
import info.wutao.v1.entity.Product;
import info.wutao.v1.mapper.ProductMapper;
import info.wutao.v1.pojo.MultiResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public MultiResultBean addToCart(String uuid, long productId, int count) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        String cartKey = new StringBuilder().append(CartRedisConstant.GUEST_CART).append(uuid).toString();

        //第一次访问或者购物车没有数据时
        List<CartItem> cart = (List<CartItem>) redisTemplate.opsForValue().get(cartKey);
        if (cart == null || cart.size() == 0) {
            cart = new ArrayList<>();
            cart.add(new CartItem(productId, count, new Date()));
            redisTemplate.opsForValue().set(cartKey, cart);
            redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

            return MultiResultBean.successResult(new Object[]{cart});
        }

        //购物车有商品
        for (CartItem cartItem : cart) {

            if (cartItem.getProductId().longValue() == productId) {
                //存在，变更数量既可
                cartItem.setCount(cartItem.getCount()+count);
                redisTemplate.opsForValue().set(cartKey, cart);
                redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

                return MultiResultBean.successResult(new Object[]{cart});
            }
        }

        //遍历结束，没有匹配，则购物车不存在相同商品
        cart.add(new CartItem(productId, count, new Date()));
        redisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

        return MultiResultBean.successResult(new Object[]{cart});
    }

    @Override
    public MultiResultBean resetCount(String uuid, long productId, int count) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        String cartKey = new StringBuilder(CartRedisConstant.GUEST_CART).append(uuid).toString();
        List<CartItem> cart = (List<CartItem>) redisTemplate.opsForValue().get(cartKey);

        //没有购物车
        if (cart == null || cart.size() == 0) {
            cart = new ArrayList<>();
            cart.add(new CartItem(productId, count, new Date()));
            redisTemplate.opsForValue().set(cartKey, cart);
            redisTemplate.expire(cartKey, 30 , TimeUnit.DAYS);

            return MultiResultBean.successResult(new Object[]{cart});
        }

        for (CartItem cartItem : cart) {
            if (cartItem.getProductId().longValue() == productId) {
                cartItem.setCount(count);
                redisTemplate.opsForValue().set(cartKey, cart);
                redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

                return MultiResultBean.successResult(new Object[]{cart});
            }
        }

        cart.add(new CartItem(productId, count, new Date()));
        redisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

        return MultiResultBean.successResult(new Object[]{cart});



    }

    @Override
    public MultiResultBean remove(String uuid, long productId) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        String cartKey = new StringBuilder(CartRedisConstant.GUEST_CART).append(uuid).toString();
        List<CartItem> cart = (List<CartItem>) redisTemplate.opsForValue().get(cartKey);

        if (cart == null || cart.size() == 0) {
            MultiResultBean resultBean = MultiResultBean.errorResult("不存在购物车");
            return resultBean;
        }

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProductId().longValue() == productId) {
                cart.remove(i);

                break;
            }
        }

        redisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.expire(cartKey, 30, TimeUnit.DAYS);

        return MultiResultBean.successResult(new Object[]{cart});
    }

    @Override
    public MultiResultBean getCart(String uuid) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        String cartKey = new StringBuilder(CartRedisConstant.GUEST_CART).append(uuid).toString();
        List<CartItem> cart = (List<CartItem>) redisTemplate.opsForValue().get(cartKey);

        if (cart == null || cart.size() == 0) {
            return MultiResultBean.errorResult("不存在购物车");
        }

        List<CartVO> cartVOS = new ArrayList<>();

        for (CartItem cartItem : cart) {

            String redisProductId = ProductRedisConstant.PRODUCT_ID+cartItem.getProductId();
            Product Product = (Product) redisTemplate.opsForValue().get(redisProductId);

            if (Product == null) {
                //从数据库中取
                try {
                    redisTemplate.opsForValue().setIfAbsent(ProductRedisConstant.PRODUCT_LOCK, "");
                    "".hashCode();
                }finally {

                }
            }
        }

        return null;
    }
}
