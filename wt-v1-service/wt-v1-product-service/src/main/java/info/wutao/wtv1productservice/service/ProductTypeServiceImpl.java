package info.wutao.wtv1productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import info.wutao.v1.api.productType.IProductTypeService;
import info.wutao.v1.base.BaseServiceImpl;
import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.base.IBaseService;
import info.wutao.v1.entity.ProductType;
import info.wutao.v1.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;



    @Override
    public IBaseDao getBaseDao() {
        return productTypeMapper;
    }
}
