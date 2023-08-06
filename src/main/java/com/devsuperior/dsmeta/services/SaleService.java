package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SellerSumProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SellerSumDTO> searchSummaryByDate(String minDate, String maxDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<SellerSumProjection> list;
		List<SellerSumDTO> result;

		if (minDate.equals("") && maxDate.equals("")) {

			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate minDateMinusYear = today.minusYears(1L);
			list = repository.searchSummaryByDate(minDateMinusYear, today);
			result = list.stream().map(x-> new SellerSumDTO(x)).collect(Collectors.toList());

		} else if (maxDate.equals("")){

			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate dataMin = LocalDate.parse(minDate, formatter);
			list = repository.searchSummaryByDate(dataMin, today);
			result = list.stream().map(x-> new SellerSumDTO(x)).collect(Collectors.toList());

		} else if (minDate.equals("")) {

			LocalDate dataMax = LocalDate.parse(maxDate, formatter);
			LocalDate minDateMinusYear = dataMax.minusYears(1L);
			list = repository.searchSummaryByDate(minDateMinusYear, dataMax);
			result = list.stream().map(x-> new SellerSumDTO(x)).collect(Collectors.toList());

		} else {

			LocalDate dataMin = LocalDate.parse(minDate, formatter);
			LocalDate dataMax = LocalDate.parse(maxDate, formatter);
			list = repository.searchSummaryByDate(dataMin, dataMax);
			result = list.stream().map(x-> new SellerSumDTO(x)).collect(Collectors.toList());

		}

		return result;
	}

	public Page<SaleDTO> searchReportByDateAndName(String minDate, String maxDate, String name, Pageable pageable) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Page<Sale> result = null;

		if (minDate.equals("") && maxDate.equals("")) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate minDateMinusYear = today.minusYears(1L);
			result = repository.searchReportByDateAndName(minDateMinusYear, today, name, pageable);

		} else if (maxDate.equals("")) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate dataMin = LocalDate.parse(minDate, formatter);
			result = repository.searchReportByDateAndName(dataMin, today, name, pageable);

		} else if (minDate.equals("")) {
			LocalDate dataMax = LocalDate.parse(maxDate, formatter);
			LocalDate minDateMinusYear = dataMax.minusYears(1L);
			result = repository.searchReportByDateAndName(minDateMinusYear, dataMax, name, pageable);

		} else {
			LocalDate dataMin = LocalDate.parse(minDate, formatter);
			LocalDate dataMax = LocalDate.parse(maxDate, formatter);
			result = repository.searchReportByDateAndName(dataMin, dataMax, name, pageable);
		}

		return result.map(x -> new SaleDTO(x));
	}


}
