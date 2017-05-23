package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.LoginForm;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.view.MenuForm;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.view.OrderViewForm;
import kr.co.sist.menu.vo.MenuVO;

public class MenuFormEvt extends MouseAdapter implements ActionListener {

	private MenuForm mf;
	private MenuDAO m_dao;
	private OrderViewForm ovf;
	private MenuAddForm maf;
	
	public static boolean ORDER_SELECT=false;

	public MenuFormEvt(MenuForm mf) {
		this.mf = mf;
		m_dao = MenuDAO.getInstance();

		// 메뉴를 조회하여 설정한다.
		setMenu();
	}// MenuDAO

	public void setMenu() {
		try {
			List<MenuVO> lstMenu = m_dao.selectMenuList();
			MenuVO mv = null;

			Object[] rowMenu = null;
			DefaultTableModel dtmMenu = mf.getDtmMenu();
			// setRowCount로 초기화된 후 추가된 데이터가 선택이 안되는 error가 발생하면 setNumRows(행수) method를
			// 사용하여 데이터를 추가하면 선택이 안되는 문제가 해결된다.
			dtmMenu.setRowCount(0);
			
			for (int i = 0; i < lstMenu.size(); i++) {
				mv = lstMenu.get(i);
				rowMenu = new Object[6];
				rowMenu[0] = i + 1;
				rowMenu[1] = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_" + mv.getImg());
				rowMenu[2] = mv.getItem_code();
				rowMenu[3] = mv.getMenu();
				rowMenu[4] = mv.getInfo();
				rowMenu[5] = mv.getPrice();

				dtmMenu.addRow(rowMenu);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mf, "죄송합니다 메뉴를 불러올 수 없습니다.\n잠시 후 다시 시도 하세요");
			e.printStackTrace();
		} // end catch
	}// setMenu

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			//도시락 주문은 1시까지만 가능
			Calendar cal = Calendar.getInstance();
			if(cal.get(Calendar.HOUR_OF_DAY)>12){
				JOptionPane.showMessageDialog(mf, "안도시락의 주문시간은 \"13시\" 이전만 가능합니다.");
				return;
			}//end if
			
			JTable temp = mf.getJtMenu();
			MenuVO mv = new MenuVO();
			int selectRow = temp.getSelectedRow();

			mv.setImg(((ImageIcon) temp.getValueAt(selectRow, 1)).toString());
			mv.setItem_code((String) temp.getValueAt(selectRow, 2));
			mv.setMenu((String) temp.getValueAt(selectRow, 3));
			mv.setInfo((String) temp.getValueAt(selectRow, 4));
			mv.setPrice((Integer) temp.getValueAt(selectRow, 5));
			int flag = JOptionPane.showConfirmDialog(mf, mv.getMenu() + "를 주문하시겠습니끄아아아악??");

			switch (flag) {
			case JOptionPane.OK_OPTION:
				new OrderForm(mf, mv);
			}
		} // end if
	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {

		LoginForm lf = new LoginForm(mf);

		try {
			if (lf.getFlag()) {
				JOptionPane.showMessageDialog(mf, "로그인 성공했습니다.");
				if (ae.getSource() == mf.getJbtOrderList()) {
					if(ovf==null){
						ovf=new OrderViewForm(mf);
					}else{
						ORDER_SELECT=true;
						ovf.setVisible(true);
					}//end else
				} // end if
				if (ae.getSource() == mf.getJbtMenuAdd()) {
					if(maf == null){
						maf=new MenuAddForm(mf, this);
					}else{
						maf.setVisible(true);
					}//end else
					
				} // end if
			}//end if
		} catch (LoginException e) {
			JOptionPane.showMessageDialog(mf, "아이디나 비밀번호를 확인하자 좀 응?");
		}//end else

	}//actionPerformed

	
}// class
