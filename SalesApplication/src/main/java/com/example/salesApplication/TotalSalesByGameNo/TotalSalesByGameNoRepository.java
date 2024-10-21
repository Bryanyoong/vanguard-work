package com.example.salesApplication.TotalSalesByGameNo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface TotalSalesByGameNoRepository extends JpaRepository<TotalSalesByGameNoModel, Integer> {

    @Query(
        value = """
SELECT 
	COALESCE(SUM(total_sold), 0) AS total_sold, 
    COALESCE(SUM(total_sale_price), 0) AS total_sale_price, 
    game_no 
FROM 
	total_sales_by_game_no 
WHERE
	date_of_sale >= :date_of_sale_from AND 
    date_of_sale <= :date_of_sale_to AND 
    game_no = :game_no;
        """, 
        nativeQuery = true
    )
    TotalSalesByGameNoView findWithNativeQuery(
        @Param("date_of_sale_from") int date_of_sale_from,
        @Param("date_of_sale_to") int date_of_sale_to,
        @Param("game_no") int game_no
    );

    @Transactional
    @Modifying
    @Query(
        value = """
INSERT INTO total_sales_by_game_no (id, game_no, date_of_sale, total_sale_price, total_sold)
SELECT 
    CONCAT(game_no, '_', game_sales_aggregate.date_of_sale) as id,  
    game_no, 
    game_sales_aggregate.date_of_sale, 
    SUM(game_sales_aggregate.sale_price) as total_sale_price, 
    COUNT(*) AS total_sold
FROM 
    ( 
        SELECT 
            game_no, 
            UNIX_TIMESTAMP(CONVERT(FROM_UNIXTIME(date_of_sale), DATE)) AS date_of_sale, 
            sale_price 
        FROM 
            game_sales 
    ) AS game_sales_aggregate 
GROUP BY 
    game_no, 
    game_sales_aggregate.date_of_sale 
ORDER BY 
    game_no ASC, 
    game_sales_aggregate.date_of_sale ASC;
        """,
        nativeQuery = true
    )
    void insertWithNativeQuery ();
}
