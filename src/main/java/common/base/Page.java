package common.base;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * @author tangpeng
 * @Description
 * @date 2018年6月27日
 */
public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = -3177409666109841117L;

	private static final Integer PAGESIZE_DEFAULT = 10;// 如果前端未传入pageSize，则采用此默认值

	private Integer currentPage = 1;//当前第几页

	private Integer pageSize = 10;//页面大小

	private Integer totalRecords;// 总记录数
	
	private Integer totalPages;//总页数

	private String orderColumn;//排序字段

	private boolean orderASC;//是否升序排列
	
	private Integer recordStart;//从那条记录开始查询

	private List<T> data;  //返回信息

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		if (null == pageSize || 0 == pageSize) {
			return PAGESIZE_DEFAULT;
		}
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public Integer getTotalRecords() {
		return totalRecords == null?0:totalRecords;
	}
	
	public Integer getTotalPages() {
		if(totalPages != null && totalPages != 0){
			return totalPages;
		}else{
			return Double.valueOf(Math.ceil(getTotalRecords()/(double)getPageSize())).intValue();
		}
	}
	
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public void setRecordStart(Integer recordStart) {
		this.recordStart = recordStart;
	}

	public int getRecordStart() {
		if(recordStart == null){
			if (getCurrentPage() == 0 || getCurrentPage() == 1) {
				return 0;
			}
			int start = (getCurrentPage().intValue() - 1)* this.pageSize.intValue();
			return start < 0 ? 0 : start;
		}else{
			return recordStart;
		}
	}
	
	public String getOrderColumn() {
		return this.orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public boolean isOrderASC() {
		return this.orderASC;
	}

	public void setOrderASC(boolean orderASC) {
		this.orderASC = orderASC;
	}
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
