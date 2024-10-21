package com.example.salesApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.salesApplication.GameSales.GameSalesView;
import com.example.salesApplication.GameSales.GameSalesService;
import com.example.salesApplication.TotalSales.TotalSalesService;
import com.example.salesApplication.TotalSalesByGameNo.TotalSalesByGameNoView;
import com.example.salesApplication.TotalSalesByGameNo.TotalSalesByGameNoService;

import org.springframework.data.domain.Page;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

@RestController
public class MainController {

    @Autowired
	private GameSalesService gameSalesService;

	@Autowired
	private TotalSalesService totalSalesService;

	@Autowired
	private TotalSalesByGameNoService totalSalesByGameNoService;

	@GetMapping("/createGameSales")
	public ResponseEntity<String> createGameSales(@RequestParam(name = "size", required = true, defaultValue = "1") int size) throws IOException {

		gameSalesService.createGameSales(size);

		return ResponseEntity.ok("Game Sales Created Successfully!");
	}

	@PostMapping("/import")
	public ResponseEntity<String> importGameSales(
		@RequestParam(name = "file", required = true) MultipartFile file
	) throws IOException {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

			String line;

			List<String> list = new ArrayList<>(1000000);

			while ((line = br.readLine()) != null) {
				list.add(line);
			}

			gameSalesService.importGameSales(list);

		} catch (Exception ex) {
			return ResponseEntity.internalServerError().body(ex.getMessage());
		}

		return ResponseEntity.ok("Success!");
	}

	@GetMapping("/getGameSales")
	public Page<GameSalesView> getGameSales(
		@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
        @RequestParam(name = "pageSize", required = false, defaultValue = "100") int pageSize,
		@RequestParam(name = "date_of_sale_from", required = false) Integer date_of_sale_from,
		@RequestParam(name = "date_of_sale_to", required = false) Integer date_of_sale_to,
		@RequestParam(name = "sale_price_less_than", required = false) Double sale_price_less_than,
		@RequestParam(name = "sale_price_greater_than", required = false) Double sale_price_greater_than
    ) {

		try {
			return gameSalesService.getGameSales(
				pageNumber - 1, 
				pageSize, 
				date_of_sale_from,
				date_of_sale_to,
				sale_price_less_than, 
				sale_price_greater_than
			);
		} catch (Exception ex) {
			throw new ResponseStatusException(
           		HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
    }

	@GetMapping("/getTotalSales")
	public TotalSalesByGameNoView getTotalSales(
		@RequestParam(name = "date_of_sale_from", required = false) Integer date_of_sale_from,
		@RequestParam(name = "date_of_sale_to", required = false) Integer date_of_sale_to,
		@RequestParam(name = "game_no", required = false) Integer game_no
    ) {
		if (game_no == null) {
			return totalSalesService.getTotalSales(
				date_of_sale_from,
				date_of_sale_to
			);
		}

		return totalSalesByGameNoService.getTotalSalesByGameNo(
			date_of_sale_from,
			date_of_sale_to,
			game_no
		);
    }
}
