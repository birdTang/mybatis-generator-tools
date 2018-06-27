package common.sqlgen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.db.ColumnEntity;
import common.db.TableColumnData;
import common.util.StringUtil;

public class CreateBase {

	public static String replaceMap(TableColumnData table) throws SQLException, IOException, ClassNotFoundException {
		StringBuffer sb=new StringBuffer();
		List<ColumnEntity> columns = table.getColumnList();
		for (ColumnEntity en : columns) {
			sb.append("\t\t<result column=\"" + en.getName() + "\" property=\"" + StringUtil.lineToHump(en.getName()) +"\" />"+ CreaterUtil.NEXT);
		}
		return sb.toString();
	}
}
