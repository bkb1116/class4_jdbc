package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.LoginFormEvt;

@SuppressWarnings("serial")
public class LoginForm extends JDialog {
	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbtLogin, jbtClose;
	
	private MenuForm mf;
	private LoginFormEvt lfe;
	private boolean flag;
	private int cnt;
	public LoginForm(MenuForm mf){
		super(mf, "�ȵ��ö� ������ �α���", true);
		
		this.mf=mf;
		
		ImageIcon icon=new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/login_bg.jpg");
		JLabel backgroundImg=new JLabel(icon);
		
		setLayout(null);
		
		backgroundImg.setBounds(0, 0, 500, 441);
		
		JPanel panelLogin=new JPanel();
		panelLogin.setLayout(new GridLayout(3, 1));
		panelLogin.setBackground(Color.white);
		panelLogin.setBorder(new TitledBorder("�α���"));
		
		jtfId=new JTextField();
		jtfId.setBorder(new TitledBorder("���̵�"));
		jpfPass=new JPasswordField();
		jpfPass.setBorder(new TitledBorder("��й�ȣ"));
		
		jbtLogin=new JButton("�α���");
		jbtClose=new JButton("�ݱ�");
		
		JPanel panelBtn=new JPanel();
		panelBtn.setBackground(Color.white);
		panelLogin.add(jtfId);
		panelLogin.add(jpfPass);

		panelBtn.add(jbtLogin);
		panelBtn.add(jbtClose);
		
		panelLogin.add(panelBtn);
		
		panelLogin.setBounds(320, 200, 160, 150);
		
		add(panelLogin);
		add(backgroundImg);
		
//		lfe=new LoginFormEvt(this);
		jpfPass.addActionListener(lfe);
		jbtLogin.addActionListener(lfe);
		jbtClose.addActionListener(lfe);
		
		setBounds(mf.getX()+100, mf.getY()+50, 500, 441);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}//LoginForm
	
	//LoginFormEvt���� �α��� ��� ����
	public void setFlag(boolean flag, int cnt){
		this.flag=flag;
		this.cnt=cnt;
	}
	
	//LoginFormEvt���� �α��� ����� ����.
	public boolean getFlag()throws LoginException{
		//�α����� �����Ͽ����� ���̵� ��й�ȣ�� Ʋ���� LoginException ���� �߻� 
		if(cnt==1 && !flag){
			throw new LoginException();
		}
		//�α����� �������� �ʰ� �ݱ⸦ Ŭ���ϸ� ������ false�� ��ȯ
		return flag;
	}
	
	public JTextField getJtfId() {
		return jtfId;
	}
	public JPasswordField getJpfPass() {
		return jpfPass;
	}
	public JButton getJbtLogin() {
		return jbtLogin;
	}
	public JButton getJbtClose() {
		return jbtClose;
	}
}//class
