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
		config.tableName="school_staff";
		
		//类描述
		config.description="班级信息";
		
		//包路径
		config.packageUrl="com.yihao01.campus.center";
		
		//mapper,entity生成包目录(当mapper抽出来放在单独的项目中时配置)
		config.mapperPackageUrl = "com.yihao01.campus.client.campusbase";
		//类名称
		config.className="SchoolStaff";
		
		
		//是否生成相应模块
		config.controllerFlag = NO;
		config.serviceFlag = NO;
		config.entityFlag = YES;
		config.mapperDaoFlag = YES;
		config.mapperXmlFlag = YES;
		
		//相应模块的entity\mapper\xml是否存在不同项目包下（这里是：mapperPackageUrl下）
		config.diffEntityFlag = YES;
		config.diffmapperDaoFlag = YES;
		config.diffMapperXmlFlag = YES;
		
		
		config.testFlag=NO;
		config.outFlag = NO;
		//计算其它参数
		config.refreshConfig();
		
		Generator generator = Generator.getInstance(config);
		generator.createCode();

	}
}