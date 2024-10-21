package com.example.salesApplication.GameSales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.salesApplication.TotalSalesByGameNo.TotalSalesByGameNoService;
import com.example.salesApplication.TotalSales.TotalSalesService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

@Service
public class GameSalesService {

	private static final Double TAX = (double) 9;

    private static final String ONE_APR_2024 = "1711929600"; // Monday, April 1, 2024 12:00:00 AM GMT+00:00
    private static final String THIRTY_APR_2024 = "1714521599"; // Tuesday, April 30, 2024 11:59:59 PM GMT+00:00

	private static final int CHAR_LOWERCASE_A = 97; // a
	private static final int CHAR_LOWERCASE_z = 122; // z

    @Autowired
    private GameSalesRepository gameSalesRepository;

	@Autowired
	private TotalSalesService totalSalesService;

	@Autowired
	private TotalSalesByGameNoService totalSalesByGameNoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createGameSales(int size) throws IOException {
        String filePath = "game_sales_" + size + ".csv";

		Path path = Paths.get(filePath);

		try {
			Files.createFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int ONE_APR_2024_value = Integer.parseInt(ONE_APR_2024);
		int THIRTY_APR_2024_value = Integer.parseInt(THIRTY_APR_2024);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			for (int i = 1; i <= size; i++) {

				String id_text = Integer.toString(i);

				Long game_no = Math.round(Math.random() * (100 - 1)) + 1;
				String game_no_text = game_no.toString();

				int game_name_length = (int) (Math.round(Math.random() * (20 - 1)) + 1);
				int leftLimit = CHAR_LOWERCASE_A;
				int rightLimit = CHAR_LOWERCASE_z;
				Random random = new Random();
				StringBuilder game_name_buffer = new StringBuilder(game_name_length);
				for (int j = 0; j < game_name_length; j++) {
					int randomLimitedInt = leftLimit + (int) 
					  (random.nextFloat() * (rightLimit - leftLimit + 1));
					game_name_buffer.append((char) randomLimitedInt);
				}
				String game_name = game_name_buffer.toString();

				int game_code_length = (int) (Math.round(Math.random() * (5 - 1)) + 1);
				StringBuilder game_code_buffer = new StringBuilder(game_code_length);
				for (int j = 0; j < game_code_length; j++) {
					int randomLimitedInt = leftLimit + (int) 
					  (random.nextFloat() * (rightLimit - leftLimit + 1));
					game_code_buffer.append((char) randomLimitedInt);
				}
				String game_code = game_code_buffer.toString();

				Double cost_price = Math.random() * 100;
				BigDecimal cost_price_value = new BigDecimal(cost_price).setScale(2, RoundingMode.DOWN);
				String cost_price_text = cost_price_value.toString();

				BigDecimal tax_price = cost_price_value.multiply(BigDecimal.valueOf(TAX / 100));
				BigDecimal tax_price_value = tax_price.setScale(2, RoundingMode.DOWN);

				BigDecimal sale_price = cost_price_value.add(tax_price_value);
				String sale_price_text = sale_price.toString();

				String date_of_sale_text = Long.toString(Math.round(Math.random() * (THIRTY_APR_2024_value - ONE_APR_2024_value)) + ONE_APR_2024_value);

				writer.write(String.format("%s,%s,\"%s\",\"%s\",2,%s,%s,%s,%s\n", id_text, game_no_text, game_name, game_code, cost_price_text, tax_price_value, sale_price_text, date_of_sale_text));
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void importGameSales(List<String> list) throws Exception {
		try {
			// to-do: delete game_sales where id matches

			int listSize = list.size();

			if (listSize == 0) {
				throw new Exception("Error, file is empty.");
			}

			StringBuilder stringBuilder = new StringBuilder(99999999); // 99 MB

			Iterator<String> it = list.iterator();
			while (it.hasNext()) {
				if (stringBuilder.length() == 0) {
					stringBuilder.append("INSERT INTO game_sales (id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale) values ");
				} else {
					stringBuilder.append(",");
				}
				stringBuilder.append(String.format("(%s)", it.next()));
			}

			jdbcTemplate.execute(stringBuilder.toString());

			// to-do: delete total_sales_by_game_no where game_no matches or date_of_sale matches
			
			totalSalesByGameNoService.calculateTotalSalesByGameNo();

			// to-do: delete total_sales where date_of_sale matches

			totalSalesService.calculateTotalSales();

		} catch (Exception ex) {
			System.out.println(ex.toString());

			throw new Exception("Error, please look for the administrator for more information.");
		}
    }

    public Page<GameSalesView> getGameSales(
        int pageNumber, 
        int pageSize, 
        Integer date_of_sale_from,
        Integer date_of_sale_to,
        Double sale_price_less_than, 
        Double sale_price_greater_than
    ) throws Exception {

		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);

			if (date_of_sale_from != null && date_of_sale_to != null && sale_price_less_than != null) {
				return gameSalesRepository.findByDateAndSalePriceLessThanEqualWithNativeQuery(
					pageable,
					date_of_sale_from,
					date_of_sale_to,
					sale_price_less_than
				);
			}

			if (date_of_sale_from != null && date_of_sale_to != null && sale_price_greater_than != null) {
				return gameSalesRepository.findByDateAndSalePriceGreaterThanEqualWithNativeQuery(
					pageable,
					date_of_sale_from,
					date_of_sale_to,
					sale_price_greater_than
				);
			}

			if (date_of_sale_from != null && date_of_sale_to != null) {
				return gameSalesRepository.findByDateOfSaleGreaterThanEqualAndDateOfSaleLessThanEqual(pageable, date_of_sale_from, date_of_sale_to);
			}

			if (sale_price_less_than != null) {
				return gameSalesRepository.findBySalePriceLessThanEqual(pageable, sale_price_less_than);
			}

			if (sale_price_greater_than != null) {
				return gameSalesRepository.findBySalePriceGreaterThanEqual(pageable, sale_price_greater_than);
			}
			
			return gameSalesRepository.findAllWithNativeQuery(pageable);

		} catch (Exception ex) {
			System.out.println(ex.toString());

			throw new Exception("Error, please look for the administrator for more information.");
		}
    }
}
