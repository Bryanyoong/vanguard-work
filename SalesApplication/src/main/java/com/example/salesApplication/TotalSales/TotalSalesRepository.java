package com.example.salesApplication.TotalSales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import com.example.salesApplication.TotalSalesByGameNo.TotalSalesByGameNoView;

import jakarta.transaction.Transactional;

public interface TotalSalesRepository extends JpaRepository<TotalSalesModel, Integer> {

    @Query(
        value = """
SELECT
    COALESCE(SUM(total_sold), 0) AS total_sold, 
    COALESCE(SUM(total_sale_price), 0) AS total_sale_price 
FROM 
    total_sales 
WHERE
    date_of_sale >= :date_of_sale_from AND
    date_of_sale <= :date_of_sale_to;
        """,
        nativeQuery = true
    )
    TotalSalesByGameNoView findWithNativeQuery(
        @Param("date_of_sale_from") int date_of_sale_from,
        @Param("date_of_sale_to") int date_of_sale_to
    );

    @Transactional
    @Modifying
    @Query(
        value = """
INSERT INTO total_sales (date_of_sale, total_sale_price, total_sold)
SELECT 
	date_of_sale,
    SUM(total_sale_price),
    SUM(total_sold)
FROM 
	total_sales_by_game_no
GROUP BY
	date_of_sale
        """,
        nativeQuery = true
    )
    void insertWithNativeQuery ();
}

