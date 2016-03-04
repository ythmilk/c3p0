import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Main {

	static ComboPooledDataSource cpds = null;

	static {
		// �����и��ŵ㣬д�������ļ����뻻���ݿ⣬��
		// cpds = new ComboPooledDataSource("oracle");//����oracle���ݿ�
		cpds = new ComboPooledDataSource("mysql");// ����mysql���ݿ�
	}

	/**
	 * ������ݿ�����
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			return (Connection) cpds.getConnection();
		} catch (SQLException e) {
			System.out.println("����ʧ��");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ���ݿ�رղ���
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
	 * ����DBUtil��
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String querySql="select*from new_schema.new_table";
		java.sql.ResultSet res = stmt.executeQuery(querySql);
		// 5.��ӡ������������
		while (res.next()) {
			System.out.println("...������");
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
