package com.example.salesApplication.GameSales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "game_sales")
public class GameSalesModel {
	@Id
	private int id;

	@Column(name = "game_no")
	private int gameNo;

	private String game_name;
	private String game_code;
	private int type;
	private BigDecimal cost_price;
	private BigDecimal tax;

	@Column(name = "sale_price")
	private BigDecimal salePrice;

	@Column(name = "date_of_sale")
	private int dateOfSale;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setGameNo(int gameNo) {
		this.gameNo = gameNo;
	}

	public int getGameNo() {
		return gameNo;
	}

	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}

	public String getGame_name() {
		return game_name;
	}

	public void setGame_code(String game_code) {
		this.game_code = game_code;
	}

	public String getGame_code() {
		return game_code;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}

	public BigDecimal getCost_price() {
		return cost_price;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setDateOfSale(int dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	public int getDateOfSale() {
		return dateOfSale;
	}
	
}
