package application.model;

import java.util.Date;

public class StocksModel {

	int stkSlno, quantity, activeIndicator;
	Double purchaseRate, sellRateUnit;
	String productName, productModel, productCode, partNumber, batchNumber, invoiceNumber, supplierName, brandName, categoryName,
			createdAt, updatedAt;
	Date purchaseDate;
	
	public StocksModel() {
	}

	public StocksModel(int stkSlno, String productName, String productModel, String productCode, String partNumber,
			String batchNumber, int quantity, Double purchaseRate, Double sellRateUnit, String invoiceNumber,
			String supplierName, String brandName, String categoryName, Date purchaseDate) {
		
		this.stkSlno = stkSlno;
		this.productName = productName;
		this.productModel = productModel;
		this.productCode = productCode;
		this.partNumber = partNumber;
		this.batchNumber = batchNumber;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
		this.purchaseRate = purchaseRate;
		this.sellRateUnit = sellRateUnit;
		this.invoiceNumber = invoiceNumber;
		this.supplierName  = supplierName;
		this.brandName = brandName;
		this.categoryName = categoryName;
	}
	
	public StocksModel(int stkSlno, String productName, String productModel, String productCode, String partNumber,
			String batchNumber, int quantity, Double purchaseRate, Double sellRateUnit, String invoiceNumber,
			String supplierName, String brandName, String categoryName) {
		
		this.stkSlno = stkSlno;
		this.productName = productName;
		this.productModel = productModel;
		this.productCode = productCode;
		this.partNumber = partNumber;
		this.batchNumber = batchNumber;
		this.quantity = quantity;
		this.purchaseRate = purchaseRate;
		this.sellRateUnit = sellRateUnit;
		this.invoiceNumber = invoiceNumber;
		this.supplierName  = supplierName;
		this.brandName = brandName;
		this.categoryName = categoryName;

	}
	
	public int getStkSlno() {
		return stkSlno;
	}

	public void setStkSlno(int stkSlno) {
		this.stkSlno = stkSlno;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(int activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public Double getPurchaseRate() {
		return purchaseRate;
	}

	public void setPurchaseRate(Double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public Double getSellRateUnit() {
		return sellRateUnit;
	}

	public void setSellRateUnit(Double sellRateUnit) {
		this.sellRateUnit = sellRateUnit;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}
