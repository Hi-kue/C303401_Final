package c303.c303_final.repository;

import c303.c303_final.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findBanksByBankName(String bankName);
    Bank findBankByBankId(Long bankId);
}
