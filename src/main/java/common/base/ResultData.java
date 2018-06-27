package common.base;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回数据统一格式   ：标识、消息、数据 
 * @author tangpeng
 * @Description
 * @date 2018年6月27日
 */
public class ResultData implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5632639058005801645L;
	public static final int ERR_CODE_SUCCESS = 200;	//无异常
	public static final int ERR_CODE_FAIL = 500;		//有异常
	/**
	 * 标识(成功:true 失败:false)
	 */
	protected boolean result = true;

	/**
	 * 消息提示
	 */
	protected String message;

	/**
	 * 数据
	 */
	protected Object data;

	/**
	 * 消息编码
	 */
	protected String msgCode;

	public ResultData() {
	}

	public ResultData(Object data) {
		this.data = data;
	}

	public ResultData(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@SuppressWarnings("unchecked")
	public void setMapData(String key, Object value) {

		Map<String, Object> map = null;
		if (data instanceof Map<?, ?>) {
			map = (Map<String, Object>) data;
			map.put(key, value);
		} else {
			map = new HashMap<String, Object>();
			map.put(key, value);
			data = map;
		}
	}

	@SuppressWarnings("unchecked")
	public Object getMapData(String key) {

		Object object = null;
		if (data instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) data;
			object = map.get(key);
		}
		return object;
	}
}

