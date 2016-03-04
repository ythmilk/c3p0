import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Main {

	static ComboPooledDataSource cpds = null;

	static {
		// 这里有个优点，写好配置文件，想换数据库，简单
		// cpds = new ComboPooledDataSource("oracle");//这是oracle数据库
		cpds = new ComboPooledDataSource("mysql");// 这是mysql数据库
	}

	/**
	 * 获得数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			return (Connection) cpds.getConnection();
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 数据库关闭操作
	 * 
	 * @param conn
	 * @param st
	 * @param pst
	 * @param rs
	 */
	public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 测试DBUtil类
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String querySql="select*from new_schema.new_table";
		java.sql.ResultSet res = stmt.executeQuery(querySql);
		// 5.打印结果集里的数据
		while (res.next()) {
			System.out.println("...有数据");
			System.out.print("the id: ");
			System.out.println(res.getInt(1));
			System.out.print("the name: ");
			System.out.println(res.getString("name"));
			System.out.print("the psw: ");
			System.out.println(res.getString("psw"));
			System.out.println();
		}
		System.out.println(conn.getClass().getName());
		close(conn, null, null);
	}
}
