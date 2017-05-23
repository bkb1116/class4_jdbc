package kr.co.sist.menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * ��(�޴�����, �޴��߰�, �ֹ���Ȳ)�� ������ ����ڿ��� �����ִ� Form
 * 
 * @author user
 */
@SuppressWarnings("serial")
public class MenuForm extends JFrame {
	private JTable jtMenu;
	private DefaultTableModel dtmMenu;
	private JTabbedPane jtpTab;
	private JButton jbtOrderList, jbtMenuAdd;
	

	public MenuForm() {
		super("�ȵ��ö� - �ֹ��ý���");

		String[] columnName = { "��ȣ", "�̹���", "�޴��ڵ�", "�޴�", "����", "����" };
		String[][] data = {};
		
		dtmMenu = new DefaultTableModel(data, columnName);

		jtMenu = new JTable(dtmMenu) {
			// �÷��� �̹����� �ֱ� ���� method Override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumClass

			//�÷� ���� �Ұ�
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable

		};
		
		//�÷� ��� �̵� �Ұ�
		jtMenu.getTableHeader().setReorderingAllowed(false);
		//�÷� ���� ����
		jtMenu.setRowHeight(100);
		
		jtMenu.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtMenu.getColumnModel().getColumn(1).setPreferredWidth(150);
		jtMenu.getColumnModel().getColumn(2).setPreferredWidth(80);
		jtMenu.getColumnModel().getColumn(3).setPreferredWidth(120);
		jtMenu.getColumnModel().getColumn(4).setPreferredWidth(430);
		jtMenu.getColumnModel().getColumn(5).setPreferredWidth(80);
		
		JScrollPane jspMenu = new JScrollPane(jtMenu);

		ImageIcon logo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/main_logo.png");
		JLabel jlLogo = new JLabel(logo);
		jlLogo.setToolTipText("��ٷο� ������ �Ը��� ��Ȯ�ϰ� ���ߴ� �ȵ��ö�");

		JPanel jpMenu = new JPanel();
		jpMenu.setLayout(new BorderLayout());
		jpMenu.add("North", jlLogo);
		jpMenu.add("Center", jspMenu);
		jpMenu.setBorder(new TitledBorder("�� ���ö��� ������ �ְ��� �޴�"));

		ImageIcon iiInfo=new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
		String msg="���� �䱸�� �ż��ϰ� ö���ϰ� ������ �� �ִ� (��)ehdgksms 2017�� �����Ͽ� ������ �̷�\n�ְ��� ���ö��� �����ϱ� ���� ��(����)����(����)�� �п��� �������� �������� �ּ��� �������� �𸣰ڽ��ϴٸ�\n�� ��¥ ��ģ�� �̰� ��....";
		JLabel lblInfo=new JLabel(iiInfo);
		
		JPanel jpComInfo=new JPanel();
		jpComInfo.setBackground(Color.WHITE);
		jpComInfo.add(lblInfo);
		jpComInfo.add(new JTextArea(msg));
		
		jbtOrderList=new JButton("�ֹ� ��ȸ");
		jbtMenuAdd=new JButton("�޴� �߰�");
		
		JPanel jpBtn=new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.setBackground(Color.WHITE);
		jpBtn.add(jbtOrderList);
		jpBtn.add(jbtMenuAdd);
		
		jtpTab = new JTabbedPane();
		jtpTab.add("�޴�", jpMenu);
		jtpTab.addTab("�ȵ��ö�����?", jpComInfo);
		
		add("North", jpBtn);
		add("Center", jtpTab);

		jpMenu.setBackground(Color.white);

		MenuFormEvt mfe = new MenuFormEvt(this);
		jtMenu.addMouseListener(mfe);
		jbtMenuAdd.addActionListener(mfe);
		jbtOrderList.addActionListener(mfe);

		setBackground(Color.white);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we){
				dispose();
				System.exit(0);
			}//windowClosing
		});
		setBounds(10, 10, 900, 600);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// MenuForm

	public JButton getJbtOrderList() {
		return jbtOrderList;
	}

	public JButton getJbtMenuAdd() {
		return jbtMenuAdd;
	}

	public JTable getJtMenu() {
		return jtMenu;
	}

	public DefaultTableModel getDtmMenu() {
		return dtmMenu;
	}

	public JTabbedPane getJtpTab() {
		return jtpTab;
	}

}// class
