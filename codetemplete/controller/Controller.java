package ##{srcPack}##.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


import com.yihao01.campus.client.base.ResultData;
import com.yihao01.campus.client.base.Page;
import ##{srcPack}##.service.##{domainObjectName}##Service;
import ##{srcEntity}##.entity.##{domainObjectName}##;


/**
 * @Description: ##{description}##
 * @author: ##{author}##
 * @date: ##{date}##
 */
@RestController
@RequestMapping("/##{requestMappingName}##")
public class ##{domainObjectName}##Controller{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ##{domainObjectName}##Service ##{firstLowerDomainObjectName}##Service;
	
	/**
	 * 列表
	 * @param page
	 * @param searchKeys
	 * @return
	 */
	@GetMapping(value="/list")
	public ResultData list(HttpServletRequest request,Page<##{domainObjectName}##> page,String searchKeys){
		ResultData result=new ResultData();
		##{firstLowerDomainObjectName}##Service.list(result,page,searchKeys);

		return result;
	}

	/**
	 * 添加保存
	 * @param entity
	 * @return
	 */
	@PostMapping(value="/addSave")
	public ResultData addSave(##{domainObjectName}## entity){
		ResultData result=new ResultData();
		##{firstLowerDomainObjectName}##Service.addSave(result,entity);
		return result;
	}
	
	/**
	 * 修改保存
	 * @param entity
	 * @return
	 */
	@PostMapping(value="/updateSave")
	public ResultData updateSave(##{domainObjectName}## entity){
		ResultData result=new ResultData();
		##{firstLowerDomainObjectName}##Service.updateSave(result,entity);
		return result;
	}
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@GetMapping(value="/findById")
	public ResultData findById(##{tablePrimaryKeyType}## id){		
		ResultData result=new ResultData();
		##{firstLowerDomainObjectName}##Service.findById(result,id);
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param ids
	 * @return
	 */
	@PostMapping(value="/deleteById")
	public ResultData deleteById(##{tablePrimaryKeyType}## id){		
		ResultData result=new ResultData();
		##{firstLowerDomainObjectName}##Service.deleteById(result,id);
		return result;
	}

}
