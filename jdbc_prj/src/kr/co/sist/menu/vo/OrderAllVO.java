package kr.co.sist.menu.vo;

public class OrderAllVO {
	private String item_code, menu, name, orderDate, ip;
	private int orderNum, quan, totalPrice;
	
	public OrderAllVO() {

	}

	public OrderAllVO(String item_code, String menu, String name, String orderDate, String ip, int orderNum, int quan,
			int totalPrice) {
		super();
		this.item_code = item_code;
		this.menu = menu;
		this.name = name;
		this.orderDate = orderDate;
		this.ip = ip;
		this.orderNum = orderNum;
		this.quan = quan;
		this.totalPrice = totalPrice;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getQuan() {
		return quan;
	}

	public void setQuan(int quan) {
		this.quan = quan;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
