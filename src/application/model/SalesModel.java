package application.model;

public class SalesModel {

	int id;
	String commadityNameSales, productCodeSales, productModelSales, productBrandSales;
	Double price, amount;
	int productQuantity;

	public SalesModel() {}

	public SalesModel(int id, String commadityNameSales, String productModelSales, String productCodeSales,
			String productBrandSales, int productQuantity, Double price, Double amount) {

		this.id = id;
		this.commadityNameSales = commadityNameSales;
		this.productBrandSales = productBrandSales;
		this.productModelSales = productModelSales;
		this.productCodeSales = productCodeSales;
		this.price = price;
		this.amount = amount;
		this.productQuantity = productQuantity;
	}

	public String getCommadityNameSales() {
		return commadityNameSales;
	}

	public void setCommadityNameSales(String commadityNameSales) {
		this.commadityNameSales = commadityNameSales;
	}

	public String getProductCodeSales() {
		return productCodeSales;
	}

	public void setProductCodeSales(String productCodeSales) {
		this.productCodeSales = productCodeSales;
	}

	public String getProductModelSales() {
		return productModelSales;
	}

	public void setProductModelSales(String productModelSales) {
		this.productModelSales = productModelSales;
	}

	public String getProductBrandSales() {
		return productBrandSales;
	}

	public void setProductBrandSales(String productBrandSales) {
		this.productBrandSales = productBrandSales;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}
