package com.example.salesApplication.TotalSales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.salesApplication.TotalSalesByGameNo.TotalSalesByGameNoView;

import org.springframework.scheduling.annotation.Async;

@Service
public class TotalSalesService {

    private static final String ONE_APR_2024 = "1711929600"; // Monday, April 1, 2024 12:00:00 AM GMT+00:00
    private static final String THIRTY_APR_2024 = "1714521599"; // Tuesday, April 30, 2024 11:59:59 PM GMT+00:00

    @Autowired
    private TotalSalesRepository totalSalesRepository;

    public TotalSalesByGameNoView getTotalSales(
        Integer date_of_sale_from,
        Integer date_of_sale_to
    ) {

		if (date_of_sale_from == null) {
			date_of_sale_from = Integer.parseInt(ONE_APR_2024);
		}

		if (date_of_sale_to == null) {
			date_of_sale_to = Integer.parseInt(THIRTY_APR_2024);
		}

        return totalSalesRepository.findWithNativeQuery(
            date_of_sale_from,
            date_of_sale_to
        );
    }

    @Async
    public void calculateTotalSales() {

        totalSalesRepository.insertWithNativeQuery();
    }
}
