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
 * 메뉴에 대한 세부항목(이미지, 메뉴코드, 메뉴명, 설명, 가격), 주문사항(수량, 총가격, 주문자) 입력폼
 * @author user
 */
@SuppressWarnings("serial")
public class OrderForm extends JDialog {
	
	private JTextField jtfItemCode, jtfMenu, jtfPrice, jtfName;
	private JComboBox<Integer> jcbQuan;
	private JButton jbtOrder, jbtClose;
	
	public OrderForm(JFrame jf, MenuVO mv){
		super(jf, "도시락 주문창", true);
		
		JPanel panel=new JPanel();
		panel.setBorder(new TitledBorder("상세주문"));
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		//pack(); // 자동 배치시 윈도우에 딱 맞게 디자인해주는 method 
		
		jtfItemCode=new JTextField();
		jtfMenu=new JTextField();
		jtfPrice=new JTextField();
		jtfName=new JTextField(10);
		
		//메뉴코드, 메뉴명, 가격 읽기 전용
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
		
		jbtOrder=new JButton("주문");
		jbtClose=new JButton("닫기");
		
		File file=new File(mv.getImg());
		
		ImageIcon ii=new ImageIcon(file.getParent()+"/"+file.getName().substring(2));
		JLabel jlImg=new JLabel(ii);
		
		jlImg.setToolTipText("위 이미지는 견본이므로 실제 제품과는 다소 차이가 있을수도 있습니다.");
		
		JPanel jpLbl=new JPanel();
		jpLbl.setLayout(new GridLayout(4, 1));
		jpLbl.setBackground(Color.white);
		jpLbl.add(new JLabel("도시락명 :"));
		jpLbl.add(new JLabel("제품코드 :"));
		jpLbl.add(new JLabel("제품가격 :"));
		jpLbl.add(new JLabel("제품설명 :"));
		
		
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
		jpOrder.setBorder(new TitledBorder("주문정보"));
		jpOrder.setBackground(Color.WHITE);
		jpOrder.add(new JLabel("주문자 :"));
		jpOrder.add(jtfName);
		jpOrder.add(new JLabel("수량 :"));
		jpOrder.add(jcbQuan);
		
		JPanel jpBtn=new JPanel();
		jpBtn.setBackground(Color.WHITE);
		jpBtn.add(jbtOrder);
		jpBtn.add(jbtClose);
		
		// 배치 설정
		jlImg.setBounds(10, 20, 244, 220);
		jpLbl.setBounds(270, 20, 70, 120);
		jpTxt.setBounds(340, 20, 210, 90);
		jspInfo.setBounds(340, 115, 210, 100);
		jlAd.setBounds(10, 250, 200, 90);
		jpOrder.setBounds(270, 220, 280, 60);
		jpBtn.setBounds(270, 290, 280, 60);
		
		// 배치
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
