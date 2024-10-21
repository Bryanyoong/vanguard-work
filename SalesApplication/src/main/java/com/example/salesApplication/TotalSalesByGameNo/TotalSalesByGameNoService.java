package com.example.salesApplication.TotalSalesByGameNo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class TotalSalesByGameNoService {
    
    private static final String ONE_APR_2024 = "1711929600"; // Monday, April 1, 2024 12:00:00 AM GMT+00:00
    private static final String THIRTY_APR_2024 = "1714521599"; // Tuesday, April 30, 2024 11:59:59 PM GMT+00:00

    @Autowired
    private TotalSalesByGameNoRepository totalSalesByGameNoRepository;

    public TotalSalesByGameNoView getTotalSalesByGameNo(
        Integer date_of_sale_from,
        Integer date_of_sale_to,
        Integer game_no
    ) {

		if (date_of_sale_from == null) {
			date_of_sale_from = Integer.parseInt(ONE_APR_2024);
		}

		if (date_of_sale_to == null) {
			date_of_sale_to = Integer.parseInt(THIRTY_APR_2024);
		}

        return totalSalesByGameNoRepository.findWithNativeQuery(
            date_of_sale_from,
            date_of_sale_to,
            game_no
        );
    }

    @Async
    public void calculateTotalSalesByGameNo() {

        totalSalesByGameNoRepository.insertWithNativeQuery();
    }
}
