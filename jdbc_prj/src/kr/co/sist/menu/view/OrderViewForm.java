package kr.co.sist.menu.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;
import kr.co.sist.menu.evt.OrderViewFormEvt;

/**
 * 실행되는 날짜의 12시까지의 주문현황을 30초단위로 갱신하여 보여주는 폼
 * 
 * @author user
 */
@SuppressWarnings("serial")
public class OrderViewForm extends JDialog {
	private MenuForm mf;
	private JTable jtOrder;
	private DefaultTableModel dtmOrder;
	private JButton btnClose;
	private JLabel orderResult;

	public OrderViewForm(MenuForm mf) {
		super(mf, "오늘의 주문 리스트", false);
		this.mf = mf;

		String[] columnNames = { "번호", "주문번호", "도시락명", "도시락코드", "주문자명", "수량", "가격", "주문일시" };
		String[][] data = {};

		dtmOrder = new DefaultTableModel(data, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable

		};// DefaultTableModel
		jtOrder = new JTable(dtmOrder);
		btnClose = new JButton("닫기");
		orderResult = new JLabel("주문결과");

		JScrollPane jspOrder = new JScrollPane(jtOrder);
		jspOrder.setBorder(new TitledBorder("주문현황"));

		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.add(orderResult);
		jpBtn.add(btnClose);

		// 컬럼 위치, 크기를 조작하지 못하게 막음
		jtOrder.getTableHeader().setReorderingAllowed(false);//
		// 컬럼 크기설정
		jtOrder.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtOrder.getColumnModel().getColumn(1).setPreferredWidth(60);
		jtOrder.getColumnModel().getColumn(2).setPreferredWidth(140);
		jtOrder.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(5).setPreferredWidth(60);
		jtOrder.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(7).setPreferredWidth(100);

		add("Center", jspOrder);
		add("South", jpBtn);

		OrderViewFormEvt ovfe = new OrderViewFormEvt(this);
		// 쓰레드 처리 - 주문현황이 계속 갱신된다.
		MenuFormEvt.ORDER_SELECT = true;
		Thread t = new Thread(ovfe);
		t.start();

		addWindowListener(ovfe);
		btnClose.addActionListener(ovfe);

		setBounds(mf.getX() + 100, mf.getY() + 100, 700, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// OrderViewForm

	public JLabel getOrderResult() {
		return orderResult;
	}

	public JTable getJtOrder() {
		return jtOrder;
	}

	public DefaultTableModel getDtmOrder() {
		return dtmOrder;
	}

}// class
