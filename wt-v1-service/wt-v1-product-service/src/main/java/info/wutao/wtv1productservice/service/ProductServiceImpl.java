package info.wutao.wtv1productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.wutao.v1.api.product.IProductService;
import info.wutao.v1.api.vo.ProductVo;
import info.wutao.v1.base.BaseServiceImpl;
import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.base.IBaseService;
import info.wutao.v1.entity.Page;
import info.wutao.v1.entity.Product;
import info.wutao.v1.entity.ProductDesc;
import info.wutao.v1.mapper.ProductDescMapper;
import info.wutao.v1.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService{


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDescMapper productDescMapper;

    @Override
    public IBaseDao getBaseDao() {
        return productMapper;
    }

    @Override
    public ProductVo add(ProductVo productVo) {
        Product product = productVo.getProduct();
        product.setFlag(true);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        //暂时没有user写死
        product.setUpdateUser(1L);
        product.setCreateUser(1L);

        productMapper.insert(product);

        ProductDesc productDesc = new ProductDesc();
        productDesc.setProductDesc(productVo.getProductDesc());
        productDesc.setProductId(product.getId());
        productDescMapper.insert(productDesc);

        return productVo;
    }
}
