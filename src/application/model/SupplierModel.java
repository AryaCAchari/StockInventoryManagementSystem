package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SupplierModel {
	
	int slNum;
	String supplierName, name, mobileNumber, landlineNumber, address, currentDate;
	
	public SupplierModel() {}
	
	public SupplierModel(String supplierName) {
		
		this.supplierName = supplierName;
	}
	
	public SupplierModel(int slNum, String name, String mobileNumber, String landlineNumber, String address) {
		
		this.slNum = slNum;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.landlineNumber = landlineNumber;
		this.address = address;
	}
	
	private IntegerProperty id = new SimpleIntegerProperty();
	
	
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
//		this.id = id;
		this.id.set(id);
		System.out.println("SupplierModel: " +id);
	}

	public int getSlNum() {
		return slNum;
	}

	public void setSlNum(int slNum) {
		this.slNum = slNum;
		System.out.println("Setter for Slnum: " +slNum);
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
		System.out.println(currentDate);
	}
	
}
