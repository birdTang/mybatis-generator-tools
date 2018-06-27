package common.base;

import java.util.List;
import java.util.Map;


/**
 * 基础Mapper,xml文件中不需要实例化接口
 * @author tangpeng
 * @Description
 * @date 2018年6月20日
 */
public interface BaseMapper<T, PK> {

	  int insert(T paramT);
	  
	  int insertWithIf(T paramT);
	  
	  int insertBatch(Map<String, Object> paramMap);

	  int insertBatchWithIf(Map<String, Object> paramMap);
	  
	  int deleteById(PK paramPK);

	  int deleteByIds(PK[] paramArrayOfPK);

	  int deleteByMap(Map<String, Object> paramMap);
	  
	  int update(T entity);
	  
	  int updateWithIf(T entity);

	  T findById(PK id);
	  
	  T findOneByMap(Map<String, Object> paramMap);

	  List<T> findListByMap(Map<String, Object> paramMap);

	  List<T> list(Map<String, Object> paramMap);

	  int findCountByMap(Map<String, Object> paramMap);
}