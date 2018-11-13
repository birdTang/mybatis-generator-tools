package common.base;

import java.util.List;


/**
 * 基础Service
 * @author tangpeng
 * @Description
 * @date 2018年6月20日
 */
public interface BaseService<T, PK> {
	 
	int insert(T entiry);

	int deleteById(PK id);

	int deleteByIds(PK[] ids);
	
	int update(T paramT);

	T findById(PK id);
	
	T findOneByMap(T paramT);
	
	List<T> findListByMap(T paramT);
	
	
}
