package com.example.salesApplication.TotalSalesByGameNo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "total_sales_by_game_no")
public class TotalSalesByGameNoModel {

    @Id
    private String id;
    
    private int game_no;

    private int date_of_sale;

    private int total_sold;
    private BigDecimal total_sale_price;

    public int getDate_of_sale() {
        return this.date_of_sale;
    }

    public void setDate_of_sale(int date_of_sale) {
        this.date_of_sale = date_of_sale;
    }

    public int getTotal_sold() {
        return this.total_sold;
    }

    public void setTotal_sold(int total_sold) {
        this.total_sold = total_sold;
    }

    public BigDecimal getTotal_sale_price() {
        return this.total_sale_price;
    }

    public void setTotal_sale_price(BigDecimal total_sale_price) {
        this.total_sale_price = total_sale_price;
    }

    public int getGame_no() {
        return this.game_no;
    }

    public void setGame_no(int game_no) {
        this.game_no = game_no;
    }
}
