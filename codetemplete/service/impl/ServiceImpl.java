package ##{srcPack}##.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yihao01.campus.client.base.mapper.BaseMapper;
import com.yihao01.campus.client.base.service.impl.BaseServiceImpl;
import com.yihao01.campus.client.base.ResultData;
import com.yihao01.campus.client.base.Page;
import ##{srcEntity}##.mapper.##{domainObjectName}##Mapper;
import ##{srcEntity}##.entity.##{domainObjectName}##;
import ##{srcPack}##.service.##{domainObjectName}##Service;

/**
 * @Description: ##{description}##
 * @author: ##{author}##
 * @date: ##{date}##
 */
@Service
public class ##{domainObjectName}##ServiceImpl extends BaseServiceImpl<##{domainObjectName}##, ##{tablePrimaryKeyType}##> implements ##{domainObjectName}##Service{

	@SuppressWarnings("unused")
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ##{domainObjectName}##Mapper ##{firstLowerDomainObjectName}##Mapper;
	
	@Override
	public BaseMapper<##{domainObjectName}##, ##{tablePrimaryKeyType}##> getMapper(){
		return ##{firstLowerDomainObjectName}##Mapper;
	}
	/**
	 * 列表
	 * @param request
	 * @param response
	 * @param page
	 */
	public void list(ResultData result,Page<##{domainObjectName}##> page,String searchKeys){
		Map<String, Object> mapSql=new HashMap<String, Object>();
		mapSql.put("searchKeys", searchKeys);
		List<##{domainObjectName}##> list = ##{firstLowerDomainObjectName}##Mapper.list(mapSql);
		page.setData(list);

		result.setData(page);
		
	}

	/**
	 * 添加保存
	 * @param request
	 * @param result
	 * @param entity
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSave(ResultData result,##{domainObjectName}## entity){
		##{firstLowerDomainObjectName}##Mapper.insert(entity);
	}
	
	/**
	 * 修改保存
	 * @param request
	 * @param result
	 * @param entity
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateSave(ResultData result,##{domainObjectName}## entity){
		##{firstLowerDomainObjectName}##Mapper.updateWithIf(entity);
	}

	/**
	 * 详情
	 * @param request
	 * @param result
	 * @param id
	 */
	public void findById(ResultData result,##{tablePrimaryKeyType}## id){
		##{domainObjectName}## entity=##{firstLowerDomainObjectName}##Mapper.findById(id);
		result.setData(entity);
	}
	
	/**
	 * 根据id删除
	 * @param request
	 * @param result
	 * @param id
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteById(ResultData result,##{tablePrimaryKeyType}## id){
		##{firstLowerDomainObjectName}##Mapper.deleteById(id);
	}
}
