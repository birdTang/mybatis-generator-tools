package ##{srcPack}##.service;


import com.yihao01.campus.client.base.ResultData;
import com.yihao01.campus.client.base.Page;
import com.yihao01.campus.client.base.service.BaseService;
import ##{srcPack}##.entity.##{domainObjectName}##;

/**
 * @Description: ##{description}##
 * @author: ##{author}##
 * @date: ##{date}##
 */
public interface ##{domainObjectName}##Service extends BaseService<##{domainObjectName}##, ##{tablePrimaryKeyType}##> {
	
	/**
	 * 列表
	 * @param request
	 * @param result
	 * @param page
	 * @return
	 */
	void list(ResultData result, Page<##{domainObjectName}##> page,String searchKeys);
	
	/**
	 * 添加保存
	 * @param request
	 * @param result
	 * @param entity
	 * @return
	 */
	void addSave(ResultData result, ##{domainObjectName}## entity);
	
	/**
	 * 修改保存
	 * @param request
	 * @param result
	 * @param entity
	 * @return
	 */
	void updateSave(ResultData result, ##{domainObjectName}## entity);
	
	/**
	 * 详情
	 * @param request
	 * @param result
	 * @param id
	 * @return
	 */
	void findById( ResultData result, ##{tablePrimaryKeyType}## id);
	
	
	/**
	 * 根据id删除
	 * @param request
	 * @param result
	 * @param ids
	 * @return
	 */
	void deleteById(ResultData result, ##{tablePrimaryKeyType}## id);
}
