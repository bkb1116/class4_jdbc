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

		// �޴��� ��ȸ�Ͽ� �����Ѵ�.
		setMenu();
	}// MenuDAO

	public void setMenu() {
		try {
			List<MenuVO> lstMenu = m_dao.selectMenuList();
			MenuVO mv = null;

			Object[] rowMenu = null;
			DefaultTableModel dtmMenu = mf.getDtmMenu();
			// setRowCount�� �ʱ�ȭ�� �� �߰��� �����Ͱ� ������ �ȵǴ� error�� �߻��ϸ� setNumRows(���) method��
			// ����Ͽ� �����͸� �߰��ϸ� ������ �ȵǴ� ������ �ذ�ȴ�.
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
			JOptionPane.showMessageDialog(mf, "�˼��մϴ� �޴��� �ҷ��� �� �����ϴ�.\n��� �� �ٽ� �õ� �ϼ���");
			e.printStackTrace();
		} // end catch
	}// setMenu

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			//���ö� �ֹ��� 1�ñ����� ����
			Calendar cal = Calendar.getInstance();
			if(cal.get(Calendar.HOUR_OF_DAY)>12){
				JOptionPane.showMessageDialog(mf, "�ȵ��ö��� �ֹ��ð��� \"13��\" ������ �����մϴ�.");
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
			int flag = JOptionPane.showConfirmDialog(mf, mv.getMenu() + "�� �ֹ��Ͻðڽ��ϲ��ƾƾƾ�??");

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
				JOptionPane.showMessageDialog(mf, "�α��� �����߽��ϴ�.");
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
			JOptionPane.showMessageDialog(mf, "���̵� ��й�ȣ�� Ȯ������ �� ��?");
		}//end else

	}//actionPerformed

	
}// class
