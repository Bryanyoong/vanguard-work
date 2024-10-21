package com.example.salesApplication.GameSales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface GameSalesRepository extends JpaRepository<GameSalesModel, Integer> {

    @Query(
        value = """
SELECT 
    id, date_of_sale, game_no, sale_price 
FROM 
    game_sales 
WHERE 
    date_of_sale >= :date_of_sale_from AND 
    date_of_sale <= :date_of_sale_to AND 
    sale_price <= :sale_price_less_than;
        """,
        countQuery = """
SELECT 
    COUNT(*) 
FROM 
    game_sales 
WHERE 
    date_of_sale >= :date_of_sale_from AND 
    date_of_sale <= :date_of_sale_to AND 
    sale_price <= :sale_price_less_than;
        """,
        nativeQuery = true
    )
    Page<GameSalesView> findByDateAndSalePriceLessThanEqualWithNativeQuery(
        Pageable pageable,
        @Param("date_of_sale_from") Integer date_of_sale_from,
        @Param("date_of_sale_to") Integer date_of_sale_to,
        @Param("sale_price_less_than") Double sale_price_less_than
    );

    @Query(
        value = """
SELECT 
    id, date_of_sale, game_no, sale_price 
FROM 
    game_sales 
WHERE 
    date_of_sale >= :date_of_sale_from AND 
    date_of_sale <= :date_of_sale_to AND 
    sale_price >= :sale_price_greater_than;
        """,
        countQuery = """
SELECT 
    COUNT(*) 
FROM 
    game_sales 
WHERE 
    date_of_sale >= :date_of_sale_from AND 
    date_of_sale <= :date_of_sale_to AND 
    sale_price >= :sale_price_greater_than;
        """,
        nativeQuery = true
    )
    Page<GameSalesView> findByDateAndSalePriceGreaterThanEqualWithNativeQuery(
        Pageable pageable,
        @Param("date_of_sale_from") Integer date_of_sale_from,
        @Param("date_of_sale_to") Integer date_of_sale_to,
        @Param("sale_price_greater_than") Double sale_price_greater_than
    );

    Page<GameSalesView> findByDateOfSaleGreaterThanEqualAndDateOfSaleLessThanEqual(Pageable pageable, int date_of_sale_from, int date_of_sale_to);

    Page<GameSalesView> findBySalePriceLessThanEqual(Pageable pageable, Double sale_price_less_than);

    Page<GameSalesView> findBySalePriceGreaterThanEqual(Pageable pageable, Double sale_price_more_than);

    @Query(
        value = """
SELECT 
    id, date_of_sale, game_no, sale_price 
FROM 
    game_sales 
        """,
        countQuery = """
SELECT 
    COUNT(*) 
FROM 
    game_sales 
        """,
        nativeQuery = true
    )
    Page<GameSalesView> findAllWithNativeQuery(
        Pageable pageable
    );
}

