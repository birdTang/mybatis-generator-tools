package common;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.GeneratorConfig;
import common.db.ColumnEntity;
import common.db.JdbcConnection;
import common.db.TableColumnData;
import common.sqlgen.CreateBase;
import common.sqlgen.CreaterEntity;
import common.sqlgen.SqlCreaterInsert;
import common.sqlgen.SqlCreaterSelect;
import common.sqlgen.SqlCreaterUpdate;
import common.util.FileUtil;
import common.util.NewIOUtil;
import common.util.StringUtil;


/**
 * 代码生成器特性：
 * 1、跨数据库 
 * 2、根据模板生成代码
 * 3、可指定生成代码路径
 * @author tangpeng
 * @Description
 * @date 2018年6月20日
 */
public class Generator {
	
	public static Generator generator = new Generator();
	
	private static GeneratorConfig config; 
	
	/*代码模板文件相对路径*/
	private final static String CONTROLLER_STRING = "/controller/Controller.java";
	private final static String SERVICE_STRING = "/service/Service.java";
	private final static String SERVICEIMPL_STRING = "/service/impl/ServiceImpl.java";
	private final static String ENTITY_STRING = "/entity/Entity.java";
	private final static String OUT_STRING = "/dto/Out.java";
	private final static String DAO_STRING = "/mapper/Mapper.java";
	private final static String MAPPER_STRING = "/mapper/xml/Mapper.xml";
	
	private final static String YES = "1";
	private final static String FSP = File.separator;
	
	private File javaFolder;
	private String templeteFolder;
	private Map<String, String> replaceMap;
	private Map<String,String> diffReplaceMap;
	private File mapperFolder;
	
	private Generator(){
		
	}
	
	public static Generator getInstance(GeneratorConfig config) {
		Generator.config = config;
		return generator;
	}
	

	/**
	 * 创建代码
	 * @throws Exception
	 */
	public void createCode() throws Exception {
		
		System.out.println("-------------start generator ...");

		// 模板代码文件夹
		this.templeteFolder = this.getTempleteFolder();
		
		// 代码文件夹
		this.javaFolder = this.createJavaCodeFolder();
		//mapper代码文件夹
		this.mapperFolder = this.createDaoCodeFolder();
		// 获取数据
		this.replaceMap = this.getReplaceData();
		
		this.diffReplaceMap = this.getDiffReplaceData();

		//替换，生成代码
		this.createEntity(ENTITY_STRING);
		this.createEntityOut(OUT_STRING);
		this.createMapperXml(MAPPER_STRING);
		this.createDao(DAO_STRING);
		this.createService(SERVICE_STRING);
		this.createServiceImpl(SERVICEIMPL_STRING);
		this.createController(CONTROLLER_STRING);

		System.out.println("------------------end generator!");

	}
	/**
	 * 获取模板文件夹
	 * @return
	 */
	private String getTempleteFolder() {
		return  config.appPath + FSP + config.templetePath.replace("/", FSP) + FSP ;
	}

	/**
	 * 查询数据库,获取表元数据
	 * 
	 * 生成模板替代数据:SQL和类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getReplaceData() throws Exception {

		// 数据库连接
		Connection conn = JdbcConnection.getConection();
		
		Map<String, String> replaceMap = new HashMap<String, String>();
		
		//包路径
		replaceMap.put("srcPack", config.packageUrl);
		//entity包路径
		if (YES.equals( config.diffEntityFlag)) {
			replaceMap.put("srcEntity", config.mapperPackageUrl);
		}else {
			replaceMap.put("srcEntity", config.packageUrl);
		}
		
		//类信息
		replaceMap.put("description", config.description);
		replaceMap.put("author", config.author);
		replaceMap.put("date", config.date);
		
		//表名称
		replaceMap.put("tableName", config.tableName);
		
		//类名称
		replaceMap.put("domainObjectName", config.className);
		replaceMap.put("firstLowerDomainObjectName",config.firstLowerClassName);
		
		//请求url，controller层@RequestMapping("")
		replaceMap.put("requestMappingName", config.lowerClassName);
		
		//解析数据表列信息
		TableColumnData tableData = new TableColumnData(conn, config.tableName);
		//转换数据类型（未完成）

		//获取主键类型
		config.tablePKType = this.getPrimaryKeyType(tableData);
		replaceMap.put("tablePrimaryKeyType", config.tablePKType);
		
		
		//设置SQL语句替换信息
		setSQLData(conn,tableData,replaceMap);
		
		replaceMap.put("entityProperty", CreaterEntity.entity(conn, config.tableName));

		conn.close();
		
		return replaceMap;

	}
	
	/**
	 * 查询数据库,获取表元数据
	 * 
	 * 生成模板替代数据:SQL和类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getDiffReplaceData() throws Exception {

		// 数据库连接
		Connection conn = JdbcConnection.getConection();
		
		Map<String, String> replaceMap = new HashMap<String, String>();
		
		//包路径
		replaceMap.put("srcPack", config.mapperPackageUrl);
		
		//类信息
		replaceMap.put("description", config.description);
		replaceMap.put("author", config.author);
		replaceMap.put("date", config.date);
		
		//表名称
		replaceMap.put("tableName", config.tableName);
		
		//类名称
		replaceMap.put("domainObjectName", config.className);
		replaceMap.put("firstLowerDomainObjectName",config.firstLowerClassName);
		
		//请求url，controller层@RequestMapping("")
		replaceMap.put("requestMappingName", config.lowerClassName);
		
		//解析数据表列信息
		TableColumnData tableData = new TableColumnData(conn, config.tableName);
		//转换数据类型（未完成）

		//获取主键类型
		config.tablePKType = this.getPrimaryKeyType(tableData);
		replaceMap.put("tablePrimaryKeyType", config.tablePKType);
		
		
		//设置SQL语句替换信息
		setSQLData(conn,tableData,replaceMap);
		
		replaceMap.put("entityProperty", CreaterEntity.entity(conn, config.tableName));

		conn.close();
		
		return replaceMap;

	}
	/**
	 * 生成SQL语句替换值
	 * @param conn
	 * @param tableData
	 * @param replaceMap
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void setSQLData(Connection conn, TableColumnData tableData, Map<String, String> replaceMap) throws ClassNotFoundException, SQLException, IOException {
		
		//resultMap
		replaceMap.put("resultMap", CreateBase.replaceMap(tableData));
		
		//1、新增语句
		replaceMap.put("insert", SqlCreaterInsert.insert(tableData));
		replaceMap.put("splitMethod", "splitToString");
		if (config.tablePKType.equals("Integer")) {
			replaceMap.put("autoGeneratedKeys", "useGeneratedKeys=\"true\" keyProperty=\"id\"");
		} else if (config.tablePKType.equals("String")) {
			replaceMap.put("autoGeneratedKeys", "");
		}

		replaceMap.put("insertWithIf", SqlCreaterInsert.insertWithIf(tableData));
		replaceMap.put("insertBatch", SqlCreaterInsert.insertBatch(tableData));
		replaceMap.put("insertBatchWithIf", SqlCreaterInsert.insertBatchWithIf(tableData));

		//2、修改语句
		replaceMap.put("update", SqlCreaterUpdate.update(tableData));
		replaceMap.put("updateWithIf", SqlCreaterUpdate.updateWithIf(tableData));
		replaceMap.put("updateBatch", SqlCreaterUpdate.updateBatch(tableData));
		replaceMap.put("updateBatchWithIf", SqlCreaterUpdate.updateBatchWithIf(tableData));
		
		//3、查找语句
		replaceMap.put("find", SqlCreaterSelect.find(tableData));
		//4、删除语句
		replaceMap.put("delete", "delete from " + config.tableName + " ");
		//5、统计语句
		replaceMap.put("count_*", "SELECT COUNT(*) from " + config.tableName + " a");
		
		//6、逻辑删除语句
//		replaceMap.put("logicDelete", "update "+config.tableName+" set status1 = '-1' ");
		
	}
	
	/**
	 * 创建java bean文件
	 * @param templeteFilePath  模板相对路径
	 * @throws Exception
	 */
	private void createEntity(String templeteFilePath) throws Exception {
		if (YES.equals(config.entityFlag) &&  YES.equals(config.diffEntityFlag)) {
			this.diffcoreHandle(templeteFilePath, false);
		}
		else if (YES.equals(config.entityFlag)) {
			this.coreHandle(templeteFilePath,false);
		}
	}
	
	private void createEntityOut(String templeteFilePath) throws Exception {
		if (YES.equals(config.outFlag)) {
			this.coreHandle(templeteFilePath,true);
		}
	}

	private void createMapperXml(String templeteFilePath) throws Exception {
		if (YES.equals(config.mapperXmlFlag) &&  YES.equals(config.diffMapperXmlFlag)) {
			this.diffcoreHandle(templeteFilePath, true);
		}
		else if (YES.equals(config.mapperXmlFlag)) {
			this.coreHandle(templeteFilePath);
		}
	}
	

	private void createDao(String templeteFilePath) throws Exception {
		if (YES.equals(config.mapperDaoFlag) &&  YES.equals(config.diffmapperDaoFlag)) {
			this.diffcoreHandle(templeteFilePath, true);
		}
		else if (YES.equals(config.mapperDaoFlag)) {
			this.coreHandle(templeteFilePath);
		}
	}

	private void createService(String templeteFilePath) throws Exception {
		if (YES.equals(config.serviceFlag)) {
			this.coreHandle(templeteFilePath);
		}
	}
	private void createServiceImpl(String templeteFilePath) throws Exception {
		if (YES.equals(config.serviceFlag)) {
			this.coreHandle(templeteFilePath);
		}
	}

	private void createController(String templeteFilePath) throws Exception {
		if (YES.equals(config.controllerFlag)) {
			this.coreHandle(templeteFilePath);
		}
	}
	
	private void coreHandle(String templeteFilePath) throws Exception {
		coreHandle(templeteFilePath, true);
	}
	
	/**
	 * 执行步骤：
	 * 1、根据模板文件相对路径生成代码文件相对路径，保证目录结构一致
	 * 2、读取模板数据
	 * 3、替换模板字符串
	 * 4、生成目标类文件
	 * 5、保持到目标文件
	 * @param templeteFilePath
	 * @param isEndName 是否使用模板名称拼接
	 * @throws Exception
	 */
	private void coreHandle(String templeteFilePath,boolean isEndName) throws Exception {
		
		//1、生成目录结构路径
		File templeteFile = new File(this.templeteFolder+templeteFilePath);
		File temp = new File(this.javaFolder.toString() + templeteFilePath);
		String filename = config.className;
		if(isEndName){
			filename += temp.getName();
		}else {
			filename +=temp.getName().substring(temp.getName().lastIndexOf("."));
		}
		String javaFilePath = temp.toString().replace(temp.getName(), filename);
		
		System.out.println("类型:"+templeteFile.getName());
		System.out.println("模板文件:"+templeteFile.toString());
		System.out.println("代码文件:"+javaFilePath);
		System.out.println("-----------------------------");
		
		//2、读取模板，替换字符串，保持代码到目标文件
		String templateFileContext = NewIOUtil.readeFile(templeteFile.toString(), "UTF-8").toString();
		String outContent = executeReplace(templateFileContext, this.replaceMap);
		File javaFile = FileUtil.createFileAndFolder(javaFilePath);
		FileUtil.writeFile(javaFile.toString(), outContent);
		
	}
	
	/**
	 * 执行步骤：
	 * 1、根据模板文件相对路径生成代码文件相对路径，保证目录结构一致
	 * 2、读取模板数据
	 * 3、替换模板字符串
	 * 4、生成目标类文件
	 * 5、保持到目标文件
	 * @param templeteFilePath
	 * @param isEndName 是否使用模板名称拼接
	 * @throws Exception
	 */
	private void diffcoreHandle(String templeteFilePath,boolean isEndName) throws Exception {
		
		//1、生成目录结构路径
		File templeteFile = new File(this.templeteFolder+templeteFilePath);
		File temp = new File(this.mapperFolder.toString() + templeteFilePath);
		String filename = config.className;
		if(isEndName){
			filename += temp.getName();
		}else {
			filename +=temp.getName().substring(temp.getName().lastIndexOf("."));
		}
		String javaFilePath = temp.toString().replace(temp.getName(), filename);
		
		System.out.println("类型:"+templeteFile.getName());
		System.out.println("模板文件:"+templeteFile.toString());
		System.out.println("代码文件:"+javaFilePath);
		System.out.println("-----------------------------");
		
		//2、读取模板，替换字符串，保持代码到目标文件
		String templateFileContext = NewIOUtil.readeFile(templeteFile.toString(), "UTF-8").toString();
		String outContent = executeReplace(templateFileContext, this.diffReplaceMap);
		File javaFile = FileUtil.createFileAndFolder(javaFilePath);
		FileUtil.writeFile(javaFile.toString(), outContent);
		
	}
	

	/**
	 * 替换字符串,设置配置规则
	 * 
	 * @param outContent
	 * @param replaceMap
	 * @return
	 */
	private static String executeReplace(String outContent, Map<String, String> replaceMap) {
		for (String str : replaceMap.keySet()) {
			outContent = outContent.replace("##{" + str + "}##", replaceMap.get(str));
			if (str.equals("entityProperty")) {
				if (outContent.contains("private Date")) {
					outContent = outContent.replace("##{importDate}##", "import java.util.Date;\nimport com.fasterxml.jackson.annotation.JsonFormat;");
				} else {
					outContent = outContent.replace("##{importDate}##", "");
				}
			}
		}

		return outContent;
	}

	/**
	 * 创建代码文件夹 路径
	 * 例如：src/main/java+com.web.app.system
	 * 
	 * @return
	 */
	private File createJavaCodeFolder() {
		return FileUtil.createFolder(config.targetPath + FSP + config.javaPath.replace("/", FSP) + FSP + config.packageUrl.replace(".", FSP));
	}

	private File createDaoCodeFolder() {
		return FileUtil.createFolder(config.mapperPath + FSP + config.javaPath.replace("/", FSP) + FSP + config.mapperPackageUrl.replace(".", FSP));
	}
	/**
	 * 获取主键类型
	 * @param tableData
	 * @return
	 */
	private String getPrimaryKeyType(TableColumnData tableData){
		ColumnEntity idColumn = tableData.getColumnList().get(0);
		String idType = "";
		if(null != idColumn){
			if("VARCHAR".equals(idColumn.getTypeName()) ){
				idType = "String";
			}
			if("INT".equals(idColumn.getTypeName())){
				idType = "Integer";
			}
		}
		return idType;
	}
}
