package info.wutao.v1.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBaseService<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKeyWithBLOBs(T t);

    int updateByPrimaryKey(T t);

    public List<T> getList();

    public PageInfo<T> getPage(int currentPage, int pageSize);

}
