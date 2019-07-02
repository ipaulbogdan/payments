package com.idorasi.payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstBySourceCurrencyAndTargetCurrencyOrderByDateDesc(Long baseId, Long pairId);

}
