package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SellerSumProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT SELLER.name, SUM(SALES.amount) as total " +
            "FROM TB_SALES AS SALES " +
            "INNER JOIN TB_SELLER AS SELLER ON SELLER.id = SALES.seller_id " +
            "WHERE SALES.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY SELLER.name")
    List<SellerSumProjection> searchSummaryByDate(LocalDate minDate, LocalDate maxDate);

    @Query("SELECT obj FROM Sale obj " +
            "WHERE obj.date >= :minDate AND obj.date < :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name,'%'))")
    Page<Sale> searchReportByDateAndName(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

}
