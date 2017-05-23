package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;

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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.MenuAddFormEvt;
import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * 메뉴를 추가하기 위한 폼
 * @author user
 */
@SuppressWarnings("serial")
public class MenuAddForm extends JDialog {
	private MenuForm mf;
	
	private JTextField jtfMenu, jtfPrice;
	private JTextArea jtaMenuInfo;
	private JButton jbtAdd, jbtClose, jbtImg;
	private JLabel jlPreview;
	private ImageIcon noImage;
	
	public MenuAddForm(MenuForm mf, MenuFormEvt mfe){
		super(mf,"안도시락 메뉴추가",true);
		this.mf=mf;
		
		jtfMenu=new JTextField();
		jtfPrice=new JTextField();
		jtaMenuInfo=new JTextArea();
		jbtAdd=new JButton("메뉴추가");
		jbtClose=new JButton("닫기");
		jbtImg=new JButton("이미지선택");
		
		String path="C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		noImage = new ImageIcon(path);
		
//		try {
//			웹서버에서 이미지 가져오기
//			noImage=new ImageIcon(new URL("http://www.sist.co.kr/default.jpg"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//end catch		
		setLayout(null);
		jlPreview=new JLabel(noImage);
		jlPreview.setBorder(new TitledBorder("도시락 이미지"));
		
		
		JPanel panelLbl=new JPanel();
		panelLbl.setLayout(new GridLayout(3, 1));
		panelLbl.add(new JLabel("제품명 : "));
		panelLbl.add(new JLabel("가 격 : "));
		panelLbl.add(new JLabel("제품설명 : "));
		
		JPanel panelTxt=new JPanel();
		panelTxt.setLayout(new GridLayout(2, 1));
		
		panelTxt.add(jtfMenu);
		panelTxt.add(jtfPrice);
		
		jtfMenu.setToolTipText("도시락의 이름을 입력합니다.");
		jtfMenu.setToolTipText("도시락의 가격을 입력합니다.");
		jtaMenuInfo.setBorder(new LineBorder(Color.DARK_GRAY));
		
		
		JScrollPane jspMenuInfo=new JScrollPane(jtaMenuInfo);
		jtaMenuInfo.setWrapStyleWord(true);
		jtaMenuInfo.setLineWrap(true);
		
		JPanel panelBtn=new JPanel();
		panelBtn.add(jbtAdd);
		panelBtn.add(jbtClose);
		
		panelBtn.setBounds(310, 150, 190, 35);
		jbtImg.setBounds(50, 240, 150, 30);
		jspMenuInfo.setBounds(330, 90, 150, 60);
		panelTxt.setBounds(330, 25, 150, 60);
		panelLbl.setBounds(260, 25, 70, 90);
		jlPreview.setBounds(10, 20, 244, 220);
		
		add(panelBtn);
		add(jbtImg);
		add(jlPreview);
		add(panelLbl);
		add(panelTxt);
		add(jspMenuInfo);
		
		MenuAddFormEvt mafe=new MenuAddFormEvt(this, mfe);
		addWindowListener(mafe);
		
		jbtAdd.addActionListener(mafe);
		jbtClose.addActionListener(mafe);
		jbtImg.addActionListener(mafe);
		
		setBounds(mf.getX()+100, mf.getY()+200, 500, 350);
		setVisible(true);
		
	}//MenuAddForm

	public MenuForm getMf() {
		return mf;
	}

	public JTextField getJtfMenu() {
		return jtfMenu;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextArea getJtaMenuInfo() {
		return jtaMenuInfo;
	}

	public JButton getJbtAdd() {
		return jbtAdd;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public JButton getJbtImg() {
		return jbtImg;
	}

	public JLabel getJlPreview() {
		return jlPreview;
	}

	public ImageIcon getNoImage() {
		return noImage;
	}

}//class
