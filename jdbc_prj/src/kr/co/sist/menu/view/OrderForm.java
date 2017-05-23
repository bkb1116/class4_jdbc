package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.OrderFormEvt;
import kr.co.sist.menu.vo.MenuVO;

/**
 * �޴��� ���� �����׸�(�̹���, �޴��ڵ�, �޴���, ����, ����), �ֹ�����(����, �Ѱ���, �ֹ���) �Է���
 * @author user
 */
@SuppressWarnings("serial")
public class OrderForm extends JDialog {
	
	private JTextField jtfItemCode, jtfMenu, jtfPrice, jtfName;
	private JComboBox<Integer> jcbQuan;
	private JButton jbtOrder, jbtClose;
	
	public OrderForm(JFrame jf, MenuVO mv){
		super(jf, "���ö� �ֹ�â", true);
		
		JPanel panel=new JPanel();
		panel.setBorder(new TitledBorder("���ֹ�"));
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		//pack(); // �ڵ� ��ġ�� �����쿡 �� �°� ���������ִ� method 
		
		jtfItemCode=new JTextField();
		jtfMenu=new JTextField();
		jtfPrice=new JTextField();
		jtfName=new JTextField(10);
		
		//�޴��ڵ�, �޴���, ���� �б� ����
		jtfItemCode.setEditable(false);
		jtfMenu.setEditable(false);
		jtfPrice.setEditable(false);
		
		jtfItemCode.setBackground(Color.white);
		jtfMenu.setBackground(Color.white);
		jtfPrice.setBackground(Color.white);
		jtfName.setBackground(Color.white);
		
		Integer[] num={1,2,3,4,5,6,7,8,9,10};
		DefaultComboBoxModel<Integer> dcmb=new DefaultComboBoxModel<Integer>(num);
		
		jcbQuan=new JComboBox<Integer>(dcmb);
		
		jbtOrder=new JButton("�ֹ�");
		jbtClose=new JButton("�ݱ�");
		
		File file=new File(mv.getImg());
		
		ImageIcon ii=new ImageIcon(file.getParent()+"/"+file.getName().substring(2));
		JLabel jlImg=new JLabel(ii);
		
		jlImg.setToolTipText("�� �̹����� �ߺ��̹Ƿ� ���� ��ǰ���� �ټ� ���̰� �������� �ֽ��ϴ�.");
		
		JPanel jpLbl=new JPanel();
		jpLbl.setLayout(new GridLayout(4, 1));
		jpLbl.setBackground(Color.white);
		jpLbl.add(new JLabel("���ö��� :"));
		jpLbl.add(new JLabel("��ǰ�ڵ� :"));
		jpLbl.add(new JLabel("��ǰ���� :"));
		jpLbl.add(new JLabel("��ǰ���� :"));
		
		
		JPanel jpTxt=new JPanel();
		jpTxt.setLayout(new GridLayout(3, 1));
		jpTxt.add(jtfMenu);
		jpTxt.add(jtfItemCode);
		jpTxt.add(jtfPrice);
		
		jtfMenu.setText(mv.getMenu());
		jtfItemCode.setText(mv.getItem_code());
		jtfPrice.setText(String.valueOf(mv.getPrice()));
		
		JTextArea jtaInfo=new JTextArea();
		jtaInfo.setEditable(false);
		jtaInfo.setLineWrap(true);
		jtaInfo.setWrapStyleWord(true);
		jtaInfo.setText(mv.getInfo());
		JScrollPane jspInfo=new JScrollPane(jtaInfo);

		ImageIcon iiAd=new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/ad.gif");
		JLabel jlAd=new JLabel(iiAd);
		
		JPanel jpOrder=new JPanel();
		jpOrder.setBorder(new TitledBorder("�ֹ�����"));
		jpOrder.setBackground(Color.WHITE);
		jpOrder.add(new JLabel("�ֹ��� :"));
		jpOrder.add(jtfName);
		jpOrder.add(new JLabel("���� :"));
		jpOrder.add(jcbQuan);
		
		JPanel jpBtn=new JPanel();
		jpBtn.setBackground(Color.WHITE);
		jpBtn.add(jbtOrder);
		jpBtn.add(jbtClose);
		
		// ��ġ ����
		jlImg.setBounds(10, 20, 244, 220);
		jpLbl.setBounds(270, 20, 70, 120);
		jpTxt.setBounds(340, 20, 210, 90);
		jspInfo.setBounds(340, 115, 210, 100);
		jlAd.setBounds(10, 250, 200, 90);
		jpOrder.setBounds(270, 220, 280, 60);
		jpBtn.setBounds(270, 290, 280, 60);
		
		// ��ġ
		panel.add(jlImg);
		panel.add(jpLbl);
		panel.add(jpTxt);
		panel.add(jspInfo);
		panel.add(jlAd);
		panel.add(jpOrder);
		panel.add(jpBtn);
		
		add("Center", panel);
		
		OrderFormEvt ofe=new OrderFormEvt(this);
		jbtOrder.addActionListener(ofe);
		jbtClose.addActionListener(ofe);
		
		setResizable(false);
		setBounds(jf.getX()+130, jf.getY()+120, 600, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jtfName.requestFocus();
	}//OrderForm

	public JTextField getJtfItemCode() {
		return jtfItemCode;
	}

	public JTextField getJtfMenu() {
		return jtfMenu;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JComboBox<Integer> getJcbQuan() {
		return jcbQuan;
	}

	public JButton getJbtOrder() {
		return jbtOrder;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}
	
}//class
