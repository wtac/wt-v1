package info.wutao.v1.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    public abstract IBaseDao getBaseDao ();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return getBaseDao().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getBaseDao().insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        return (T)getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getBaseDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(T t) {
        return getBaseDao().updateByPrimaryKeyWithBLOBs(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getBaseDao().updateByPrimaryKey(t);
    }

    @Override
    public List<T> getList() {
        return getBaseDao().getList();
    }

    @Override
    public PageInfo<T> getPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<T> list = getList();
        return new PageInfo<>(list, 5);
    }

}
