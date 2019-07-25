package application.model;

import java.sql.Date;

public class ProuctAndServiceModel {
	
	private String productName=null, productModel=null, brandName=null, categoryName=null;
	private int quantity=0;
	private Date updatedAt;
	
	public ProuctAndServiceModel() {}
	
	public ProuctAndServiceModel(String model, String brand, String category, int count, Date date) {
		this.productModel = model;		
		this.brandName = brand;
		this.categoryName = category;
		this.quantity = count;
		this.updatedAt = date;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
