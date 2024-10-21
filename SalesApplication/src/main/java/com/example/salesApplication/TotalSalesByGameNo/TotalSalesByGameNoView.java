package com.example.salesApplication.TotalSalesByGameNo;

import java.math.BigDecimal;

public interface TotalSalesByGameNoView {

    BigDecimal getTotal_sold();
    BigDecimal getTotal_sale_price();
    Integer getGame_no();
}
