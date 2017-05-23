package kr.co.sist.menu.vo;

public class OrderVO {
	private String item_code, name, ipAddr;
	private int quan;
	
	public OrderVO(){
		
	}//OrderVO

	public OrderVO(String item_code, String name, String ipAddr, int quan) {
		this.item_code = item_code;
		this.name = name;
		this.ipAddr = ipAddr;
		this.quan = quan;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getQuan() {
		return quan;
	}

	public void setQuan(int quan) {
		this.quan = quan;
	}

	@Override
	public String toString() {
		return "OrderVO [item_code=" + item_code + ", name=" + name + ", ipAddr=" + ipAddr + ", quan=" + quan + "]";
	}
	
}
