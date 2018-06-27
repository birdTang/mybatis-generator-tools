package main;

import common.Generator;
import common.GeneratorConfig;

/**
 * 代码生成器
 * @author tangpeng
 * @Description
 * @date 2018年6月20日
 */
public class Main {
	
	static String YES = "1";
	static String NO = "0";

  public static void main(String[] args) throws Exception {
		
		
		GeneratorConfig config = new GeneratorConfig();
		//数据表名
		config.tableName="wy_praise";
		
		//类描述
		config.description="点赞信息表";
		
		//包路径
		config.packageUrl="com.yihao01.campus.cloud.busniee";
		
		//类名称
		config.className="WyPraise";
		
		
		//是否生成相应模块
		config.controllerFlag = YES;
		config.serviceFlag = YES;
		config.entityFlag = YES;
		config.mapperDaoFlag = YES;
		config.mapperXmlFlag = YES;
		
		
		
		config.testFlag=NO;
		config.outFlag = NO;
		//计算其它参数
		config.refreshConfig();
		
		Generator generator = Generator.getInstance(config);
		generator.createCode();

	}
}