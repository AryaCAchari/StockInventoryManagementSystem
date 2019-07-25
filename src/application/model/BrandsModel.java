package application.model;

public class BrandsModel {
	
	int brnd_slno, active_indicator;
	String brand_name, sup_name, created_at, updated_at;
	
	public BrandsModel() {}
	
	public BrandsModel(int brnd_slno, int active_indicator, String brand_name, String sup_name, String created_at, String updated_at) {
		
		this.brnd_slno = brnd_slno;
		this.brand_name = brand_name;
		this.sup_name = sup_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.active_indicator = active_indicator;
	}
	public int getBrnd_slno() {
		return brnd_slno;
	}
	public void setBrnd_slno(int brnd_slno) {
		this.brnd_slno = brnd_slno;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public int getActive_indicator() {
		return active_indicator;
	}
	public void setActive_indicator(int active_indicator) {
		this.active_indicator = active_indicator;
	}
	
	

}
