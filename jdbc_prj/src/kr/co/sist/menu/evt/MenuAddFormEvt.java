package kr.co.sist.menu.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.view.MenuForm;
import kr.co.sist.menu.vo.MenuVO;

public class MenuAddFormEvt extends WindowAdapter implements ActionListener {

	private MenuAddForm maf;
	private MenuFormEvt mfe;
	
	public MenuAddFormEvt(MenuAddForm maf, MenuFormEvt mfe) {
		this.maf=maf;
		this.mfe=mfe;
	}//MenuAddFormEvt
	
	@Override
	public void windowClosing(WindowEvent e) {
		maf.setVisible(false);
	}//windowClosing
	
	private void setImg(){
		FileDialog fdImag=new FileDialog(maf, "도시락 이미지 선택", FileDialog.LOAD);
		fdImag.setVisible(true);
		
		String path=fdImag.getDirectory();
		String file=fdImag.getFile(); //????????
		if(file != null){
			String validFile="jpg,gif,png,bmp";
			if( !validFile.contains(file.substring(file.lastIndexOf(".")+1)) ){
				JOptionPane.showMessageDialog(maf, "선택하신 파일은 이미지가 아닙니다.");
				return;
			}//end if
			ImageIcon temp=new ImageIcon(path+file);
			maf.getJlPreview().setIcon(temp);
		}//end if
		
	}//setImage
	
	private void addMenu(){
		//C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/ 에 복사
		//이미지의 경로
		//메뉴이름, 가격, 설명
		ImageIcon icon=(ImageIcon)maf.getJlPreview().getIcon();//이미지
		File file= new File(icon.toString());
		String tempFile=file.getName();
		if(tempFile.equals("default.jpg")){
			JOptionPane.showMessageDialog(maf, "기본 이미지는 사용할 수 없습니다.");
			return;
		}//end if
		
		File sFile=new File(file.getParent()+"/s_"+tempFile);
		if(!sFile.exists()){
			JOptionPane.showMessageDialog(maf, "메뉴 선택시 추가되는 파일인 \"s_"+tempFile+"\"이 필요합니다.");
			return;
		}//end if
		
		//파일을 사용위치에 복붙.
		//선택한 위치가 파일을 보여주는 위치가 아니라면 복붙 실행
		if(!file.getParent().equals("C:\\dev\\workspace\\jdbc_prj\\src\\kr\\co\\sist\\menu\\img")){
			
			try {
				//원본 파일 복붙
				FileInputStream fis=new FileInputStream(file);
				FileOutputStream fos=new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/"+file.getName());
				
				byte[] temp=new byte[512];
				
				int readData=0;
				while( ( readData=fis.read(temp) ) != -1 ){
					fos.write(temp,0,readData);
				}//end while
				fos.flush();
				
				if( fis != null ){ fis.close(); }//end if
				if( fos != null ){ fos.close(); }//end if
				
				//메뉴선택 파일 복붙
				fis=new FileInputStream(file.getParent()+"/s_"+file.getName());
				fos=new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_"+file.getName());
				
				while ( (readData=fis.read(temp) ) != -1) {
					fos.write(temp,0,readData);
				}//end while
				
				fos.flush();
				
				if( fis != null ){ fis.close(); }//end if
				if( fos != null ){ fos.close(); }//end if
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}//end catch
			
		}//end if
		
		String menu= maf.getJtfMenu().getText();//메뉴명
		String price= maf.getJtfPrice().getText();//가격
		String info= maf.getJtaMenuInfo().getText();//내용
		
		MenuDAO md=MenuDAO.getInstance();
		MenuVO mv=new MenuVO();
		try {
			mv.setImg(tempFile);
			mv.setMenu(menu);
			mv.setPrice(Integer.parseInt(price));
			mv.setInfo(info);
			
			md.insertMenu(mv);
			// 정상 추가 후 메뉴 리스트를 갱신
			mfe.setMenu();
			// 관리자에게 처리결과를 보여준다
			JOptionPane.showMessageDialog(maf, menu+"가 정상적으로 추가되었습니다.");
			
			addFormClose();
			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(maf, "가격은 숫자만 입력 가능합니다.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(maf, "문제가 발생했습니다.");
			e.printStackTrace();
		}//end catch
		
	}//addMenu
	
	private void addFormClose() {
		// 입력창의 모든 내용을 초기화
		// 이미지, 메뉴명, 가격, 내용
		String path = "C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		ImageIcon icon = new ImageIcon(path);
		maf.getJlPreview().setIcon(icon);// 이미지
		maf.getJtfMenu().setText("");// 메뉴명
		maf.getJtfPrice().setText("");// 가격
		maf.getJtaMenuInfo().setText("");// 내용
		// 입력창을 보이지 않도록 숨긴다
		maf.setVisible(false);

	}// addFormClose
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == maf.getJbtAdd()) {
			addMenu();
		} // end if
		if (ae.getSource() == maf.getJbtClose()) {
			addFormClose();
		}//end if
		if (ae.getSource() == maf.getJbtImg()) {
			setImg();
		} // end if

	}// actionPerformed

}//class
