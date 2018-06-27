package common.base;

import java.util.List;
import java.util.Map;


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

	public List<T> findListByMap(Map<String, Object> mapSql) {
		return getMapper().findListByMap(mapSql);
	}

	public T findById(PK id) {
		return getMapper().findById(id);
	}
	
	public T findOneByMap(Map<String, Object> paramMap) {
		return getMapper().findOneByMap(paramMap);
	}


}
