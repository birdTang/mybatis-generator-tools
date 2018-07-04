package common.sqlgen;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.db.ColumnEntity;
import common.db.TableColumnData;
import common.util.StringUtil;





public class SqlCreaterSelect  {

	static final String NEXT = CreaterUtil.NEXT;
	
	
	public static String find(TableColumnData table) throws SQLException, IOException, ClassNotFoundException{
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("\t\t\n");
		sb.append("\t\tselect  \t\n");
		
		List<ColumnEntity> columns = table.getColumnList();
		
		for (ColumnEntity en : columns) {
			sb.append("\t\t\ta."+en.getName()+" "+StringUtil.lineToHump(en.getName())+","+NEXT);
		}
		sb.delete(sb.length() - 2, sb.length());
		
		sb.append("\n\t\tfrom "+table.getTableName()+" a"+"\t\n");
		
		return sb.toString();
	}
	
	public static String findWithIf(TableColumnData table) throws SQLException, IOException, ClassNotFoundException{
		
		StringBuffer sb=new StringBuffer();

		sb.append("<where> \t\n");
		
		List<ColumnEntity> columns = table.getColumnList();
		
		for (ColumnEntity en : columns) {
			
			// 若为主键且自动增长，若是则不生成该列
			if (en.isPrimaryKey()) continue;
			sb.append("\t\t\t<if test=\" "+StringUtil.lineToHump(en.getName())+"!=null \">\t\n");
			sb.append("\t\t\t\t AND a."+en.getName()+"=#{"+StringUtil.lineToHump(en.getName())+"}\t\n\t\t\t</if>\t\n");
		}
		sb.append("\t\t</where>\t\n");
		return sb.toString();
	}
	
}
