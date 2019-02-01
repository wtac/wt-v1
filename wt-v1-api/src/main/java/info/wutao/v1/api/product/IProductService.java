package info.wutao.v1.api.product;

import com.github.pagehelper.PageInfo;
import info.wutao.v1.api.vo.ProductVo;
import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.base.IBaseService;
import info.wutao.v1.entity.Page;
import info.wutao.v1.entity.Product;

import java.util.List;

public interface IProductService extends IBaseService<Product> {

    ProductVo add(ProductVo productVo);
}
