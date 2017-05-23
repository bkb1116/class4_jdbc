package kr.co.sist.menu.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import kr.co.sist.menu.vo.MenuVO;
import kr.co.sist.menu.vo.OrderAllVO;
import kr.co.sist.menu.vo.OrderVO;

public class MenuDAO {
	private static MenuDAO m_dao;

	private MenuDAO() {
	}// MenuDAO

	public static MenuDAO getInstance() {

		if (m_dao == null) {
			m_dao = new MenuDAO();
		} // end if

		return m_dao;
	}// getInstance

	private Connection getConnection() throws SQLException {
		Connection con = null;

		Properties prop = new Properties();
		try {
			File file = new File("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/dao/menu_db.properties");
			
			if (file.exists()) {
				prop.load(new FileInputStream(file));
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String id = prop.getProperty("dboid");
				String pass = prop.getProperty("dbopwd");

				try {
					Class.forName(driver);
					con = DriverManager.getConnection(url, id, pass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} // end catch

			} else {
				JOptionPane.showMessageDialog(null, "설정파일의 경로를 확인해주세요");
			} // end else

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return con;
	}// getConnection

	/**
	 * item_code, img, menu, info, price를 조회하여 MenuVO에 저장하고 List에 추가하여 반환하는 일
	 * 
	 * @return List<MenuVO>
	 * @throws SQLException
	 */
	public List<MenuVO> selectMenuList() throws SQLException {
		List<MenuVO> list = new ArrayList<MenuVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1.
			// 2.
			con = getConnection();
			// 3.
			String selectMenu = "select item_code, img, menu, info, price from menu";
			pstmt = con.prepareStatement(selectMenu);
			// 4.
			rs = pstmt.executeQuery();

			MenuVO mv = null;
			while (rs.next()) {
				mv = new MenuVO();
				mv.setItem_code(rs.getString("item_code"));
				mv.setImg(rs.getString("img"));
				mv.setMenu(rs.getString("menu"));
				mv.setInfo(rs.getString("info"));
				mv.setPrice(rs.getInt("price"));
				
				list.add(mv);
			} // end while

		} finally {
			// 5.
			if (rs != null) {
				rs.close();
			} // end if

			if (pstmt != null) {
				pstmt.close();
			} // end if

			if (con != null) {
				con.close();
			} // end if
		}

		return list;
	}// selectMenuList

	/**
	 * 메뉴 주문 정보(item_code, name, quan, ipAddr)를 테이블에 추가하는 일
	 * 
	 * @param ov
	 * @throws SQLException
	 */
	public void insertOrder(OrderVO ov) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
		// 1.
		// 2.
			con=getConnection();
		// 3.
			String insertOrdering="insert into ordering(order_num, name, item_code, quan, ipaddr, order_date) values(seq_ordering.nextval, ?, ?, ?, ?, sysdate)";
			pstmt=con.prepareStatement(insertOrdering);
		// 4.
			pstmt.setString(1, ov.getName());
			pstmt.setString(2, ov.getItem_code());
			pstmt.setInt(3, ov.getQuan());
			pstmt.setString(4, ov.getIpAddr());

			pstmt.executeUpdate();
		} finally {
		// 5.
			
			if (pstmt != null) {
				pstmt.close();
			} // end if

			if (con != null) {
				con.close();
			} // end if
		}

		// item_code, name, quan,  ipaddr
	}// insertOrder

	/**
	 * 메뉴를 테이블에 추가하는 일
	 * 
	 * @param mv
	 * @throws SQLException
	 */
	public void insertMenu(MenuVO mv) throws SQLException {
		//item_code, img, menu, info, price
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
		// 1.
		// 2.
			con=getConnection();
		// 3.
			String insertMenu="insert into menu(item_code, menu, price, info, img, inputdate) values(menu_num, ?, ?, ?, ?, sysdate)";
			pstmt=con.prepareStatement(insertMenu);
		// 4.
			pstmt.setString(1, mv.getMenu());
			pstmt.setInt(2, mv.getPrice());
			pstmt.setString(3, mv.getInfo());
			pstmt.setString(4, mv.getImg());
			
			pstmt.executeUpdate();
			
		}finally{
		// 5.
			if (pstmt != null) {
				pstmt.close();
			} // end if

			if (con != null) {
				con.close();
			} // end if
		}
	}// insertMenu

	/**
	 * 그 날 1시까지의 주문 정보를 30초마다 조회하여 실시간 정보를 확인하는 일
	 * 
	 * @return List<OrderAllVO>
	 * @throws SQLException
	 */
	public List<OrderAllVO> selectOrder() throws SQLException {
		List<OrderAllVO> list = new ArrayList<>();
		//item_code, orderName, orderData, quan, price
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		// 1.
		// 2.
			con = getConnection();
		// 3.
			StringBuilder selectOrdering=new StringBuilder();
			selectOrdering
			.append("select m.item_code item_code, m.menu menu,o.order_num order_num ,o.name name, to_char(o.order_date, 'hh24:mi:ss') order_date, o.ipaddr ipaddr, o.quan quan, m.price*o.quan totalprice ")
			.append("from menu m, ordering o ")
			.append("where (o.item_code=m.item_code) ")
			.append("and to_char(order_date, 'hh24')>8 and to_char(order_date, 'hh24')<13 and to_char(order_date, 'yyyymmdd')=to_char(sysdate, 'yyyymmdd')")
			.append(" order by order_date desc ");
		// 4.
			pstmt=con.prepareStatement(selectOrdering.toString());
			rs=pstmt.executeQuery();
			
			OrderAllVO oav = null;
			while (rs.next()) {
				oav = new OrderAllVO();
				oav.setName(rs.getString("name"));
				oav.setItem_code(rs.getString("item_code"));
				oav.setMenu(rs.getString("menu"));
				oav.setOrderDate(rs.getString("order_date"));
				oav.setIp(rs.getString("ipaddr"));
				oav.setQuan(rs.getInt("quan"));
				oav.setTotalPrice(rs.getInt("totalprice"));
				oav.setOrderNum(rs.getInt("order_num"));
				
				list.add(oav);
				
			} // end while
		}finally{
		// 5.
			if (rs != null) {
				rs.close();
			} // end if

			if (pstmt != null) {
				pstmt.close();
			} // end if

			if (con != null) {
				con.close();
			} // end if
		}
		return list;
	}

}// class
