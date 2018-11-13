package common.base;

import java.util.List;


/**
 * BaseServiceImpl
 * @author tangpeng
 * @Description
 * @date 2018年6月20日
 */
public abstract class BaseServiceImpl<T, PK> implements BaseService<T, PK> {

	public abstract BaseMapper<T, PK> getMapper();

	public int insert(T obj) {
		return getMapper().insert(obj);
	}

	public int deleteById(PK id) {
		return getMapper().deleteById(id);
	}

	public int deleteByIds(PK[] ids) {
		return getMapper().deleteByIds(ids);
	}

	public int update(T obj) {
		return getMapper().update(obj);
	}

	public List<T> findListByMap(T paramT) {
		return getMapper().findListByParam(paramT);
	}

	public T findById(PK id) {
		return getMapper().findById(id);
	}
	
	public T findOneByMap(T paramT) {
		return getMapper().findOneByParam(paramT);
	}


}
