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
		FileDialog fdImag=new FileDialog(maf, "���ö� �̹��� ����", FileDialog.LOAD);
		fdImag.setVisible(true);
		
		String path=fdImag.getDirectory();
		String file=fdImag.getFile(); //????????
		if(file != null){
			String validFile="jpg,gif,png,bmp";
			if( !validFile.contains(file.substring(file.lastIndexOf(".")+1)) ){
				JOptionPane.showMessageDialog(maf, "�����Ͻ� ������ �̹����� �ƴմϴ�.");
				return;
			}//end if
			ImageIcon temp=new ImageIcon(path+file);
			maf.getJlPreview().setIcon(temp);
		}//end if
		
	}//setImage
	
	private void addMenu(){
		//C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/ �� ����
		//�̹����� ���
		//�޴��̸�, ����, ����
		ImageIcon icon=(ImageIcon)maf.getJlPreview().getIcon();//�̹���
		File file= new File(icon.toString());
		String tempFile=file.getName();
		if(tempFile.equals("default.jpg")){
			JOptionPane.showMessageDialog(maf, "�⺻ �̹����� ����� �� �����ϴ�.");
			return;
		}//end if
		
		File sFile=new File(file.getParent()+"/s_"+tempFile);
		if(!sFile.exists()){
			JOptionPane.showMessageDialog(maf, "�޴� ���ý� �߰��Ǵ� ������ \"s_"+tempFile+"\"�� �ʿ��մϴ�.");
			return;
		}//end if
		
		//������ �����ġ�� ����.
		//������ ��ġ�� ������ �����ִ� ��ġ�� �ƴ϶�� ���� ����
		if(!file.getParent().equals("C:\\dev\\workspace\\jdbc_prj\\src\\kr\\co\\sist\\menu\\img")){
			
			try {
				//���� ���� ����
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
				
				//�޴����� ���� ����
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
		
		String menu= maf.getJtfMenu().getText();//�޴���
		String price= maf.getJtfPrice().getText();//����
		String info= maf.getJtaMenuInfo().getText();//����
		
		MenuDAO md=MenuDAO.getInstance();
		MenuVO mv=new MenuVO();
		try {
			mv.setImg(tempFile);
			mv.setMenu(menu);
			mv.setPrice(Integer.parseInt(price));
			mv.setInfo(info);
			
			md.insertMenu(mv);
			// ���� �߰� �� �޴� ����Ʈ�� ����
			mfe.setMenu();
			// �����ڿ��� ó������� �����ش�
			JOptionPane.showMessageDialog(maf, menu+"�� ���������� �߰��Ǿ����ϴ�.");
			
			addFormClose();
			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(maf, "������ ���ڸ� �Է� �����մϴ�.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(maf, "������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}//end catch
		
	}//addMenu
	
	private void addFormClose() {
		// �Է�â�� ��� ������ �ʱ�ȭ
		// �̹���, �޴���, ����, ����
		String path = "C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		ImageIcon icon = new ImageIcon(path);
		maf.getJlPreview().setIcon(icon);// �̹���
		maf.getJtfMenu().setText("");// �޴���
		maf.getJtfPrice().setText("");// ����
		maf.getJtaMenuInfo().setText("");// ����
		// �Է�â�� ������ �ʵ��� �����
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
