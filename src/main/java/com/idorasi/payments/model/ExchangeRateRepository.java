package com.idorasi.payments.model;

import com.idorasi.payments.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {

    List<ExchangeRate> findBySourceCurrencyAndTargetCurrencyOrderByDateDesc(Long baseId,Long pairId);

}
