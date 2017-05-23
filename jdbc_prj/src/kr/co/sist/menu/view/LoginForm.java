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
		super(mf, "안도시락 관리자 로그인", true);
		
		this.mf=mf;
		
		ImageIcon icon=new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/login_bg.jpg");
		JLabel backgroundImg=new JLabel(icon);
		
		setLayout(null);
		
		backgroundImg.setBounds(0, 0, 500, 441);
		
		JPanel panelLogin=new JPanel();
		panelLogin.setLayout(new GridLayout(3, 1));
		panelLogin.setBackground(Color.white);
		panelLogin.setBorder(new TitledBorder("로그인"));
		
		jtfId=new JTextField();
		jtfId.setBorder(new TitledBorder("아이디"));
		jpfPass=new JPasswordField();
		jpfPass.setBorder(new TitledBorder("비밀번호"));
		
		jbtLogin=new JButton("로그인");
		jbtClose=new JButton("닫기");
		
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
	
	//LoginFormEvt에서 로그인 결과 설정
	public void setFlag(boolean flag, int cnt){
		this.flag=flag;
		this.cnt=cnt;
	}
	
	//LoginFormEvt에서 로그인 결과를 얻어간다.
	public boolean getFlag()throws LoginException{
		//로그인을 수행하였으나 아이디나 비밀번호가 틀려서 LoginException 강제 발생 
		if(cnt==1 && !flag){
			throw new LoginException();
		}
		//로그인을 수행하지 않고 닫기를 클릭하면 무조건 false가 반환
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
