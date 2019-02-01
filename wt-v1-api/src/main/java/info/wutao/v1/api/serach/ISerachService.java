package info.wutao.v1.api.serach;

import info.wutao.v1.pojo.PageResultBean;

import java.util.List;

public interface ISerachService <T> {
    List<T> listByName (String queryCriteria);

    PageResultBean<T> page(String queryName, Integer pageNum, Integer pageSize);
}
