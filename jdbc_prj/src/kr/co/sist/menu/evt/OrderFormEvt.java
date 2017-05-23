package kr.co.sist.menu.evt;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.vo.OrderVO;

public class OrderFormEvt extends WindowAdapter implements ActionListener {

	private OrderForm of;
	
	public OrderFormEvt(OrderForm of) {
		this.of=of;
	}//OrderFormEvt
	
	private void setOrder(){
		String name=of.getJtfName().getText();
		
		if(name.length()>10){
			JOptionPane.showMessageDialog(of, "입력이름 : "+name+"\n이름이 좀 기네요;; 10자로 줄여주시죠;;;");
			of.getJtfName().setText("");
			of.getJtfName().requestFocus();
			return;
		}
		
		String item_code=of.getJtfItemCode().getText();
		String menu=of.getJtfMenu().getText();
		Integer quan=(Integer)of.getJcbQuan().getSelectedItem();
		String price=of.getJtfPrice().getText();
		String ipAddr="";
		
		try {
			ipAddr=InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}//end catch
		
		StringBuilder order=new StringBuilder();
		order.append(name).append("님의 주문사항\n")
		.append("제품명(코드) : ").append(menu).append("(").append(item_code).append(")")
		.append("\n단가 : ").append(price)
		.append("\n수량 : ").append(quan)
		.append("\n총금액 : ").append(Integer.parseInt(price)*quan)
		.append("원\n주문자 정보 : ").append(ipAddr)
		.append("\n주문하시겠습니까?");
		
		int flag=JOptionPane.showConfirmDialog(of, order.toString());
		switch(flag){
		case JOptionPane.OK_OPTION:
			MenuDAO md=MenuDAO.getInstance();
			
			OrderVO ov=new OrderVO(item_code, name, ipAddr, quan);
			
			try {
				md.insertOrder(ov);
				ImageIcon iiDong=new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
				JLabel lblThank=new JLabel(iiDong);
				JPanel jp=new JPanel();
				jp.setLayout(new BorderLayout());
				jp.add("North", new Label("주문이 성공적으로 되었습니다."));
				lblThank.setToolTipText("주문해주셔서 감사합니다.");
				jp.add("Center", lblThank);
				
				JOptionPane.showMessageDialog(of, jp);
				of.dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}//setOrder()
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==of.getJbtOrder()){
			setOrder();
		}//end if

		if(ae.getSource()==of.getJbtClose()){
			int selectNum=JOptionPane.showConfirmDialog(of, "주문창을 정말 닫으실꺼니?");
			switch(selectNum){
			case JOptionPane.OK_OPTION:
				of.dispose();
			}//end switch
		}//end if

	}//actionPerformed

}//class
