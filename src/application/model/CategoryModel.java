package application.model;

public class CategoryModel {

	String categoryName, brandName, supplierName, categoryDescription, createdBy, createdAt, updatedAt;
	int activeIndicator, catSlno;

	public CategoryModel() {

	}

	public CategoryModel(int catSlno, String categoryName, String brandName, String supplierName, String categoryDescription, String createdBy, String createdAt, String updatedAt, int activeIndicator) {
		
		this.catSlno = catSlno;
		this.categoryName =categoryName;
		this.brandName = brandName;
		this.supplierName = supplierName;
		this.categoryDescription = categoryDescription;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.activeIndicator = activeIndicator;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(int activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public int getCatSlno() {
		return catSlno;
	}

	public void setCatSlno(int catSlno) {
		this.catSlno = catSlno;
	}

}
