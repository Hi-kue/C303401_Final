package c303.c303_final.service;

import c303.c303_final.model.Bank;
import c303.c303_final.repository.BankRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BankService {
    //region DEFAULT(S)
    private static final String BANK_WITH_ID_NOT_FOUND = "Bank with the provided id %s is not found.";
    private static final String BANK_WITH_NAME_NOT_FOUND = "Bank with the provided name %s is not found.";
    //endregion

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    public Bank findBankById(final Long id) {
        return findBankByTypeOrThrowException(id);
    }

    public Bank findBankByBankName(final String bankName) {
        return findBankByTypeOrThrowException(bankName);
    }

    public Bank createBank(final Bank bank) {
        return bankRepository.save(bank);
    }

    public Boolean updateBankByBankId(final Bank bank, final Long id) {
        var bankToUpdate = findBankById(id);

        updateBankAttributes(bankToUpdate, bank);
        bankRepository.save(bankToUpdate);
        return true;
    }

    public Boolean updateBankByBankName(final Bank bank, final String bankName) {
        var bankToUpdate = findBankByBankName(bankName);

        updateBankAttributes(bankToUpdate, bank);
        bankRepository.save(bankToUpdate);
        return true;
    }

    public Boolean deleteBankByBankId(final Long id) {
        var bankToDelete = findBankById(id);

        bankRepository.delete(bankToDelete);
        return true;
    }

    public Boolean deleteBankByBankName(final String bankName) {
        var bankToDelete = findBankByBankName(bankName);

        bankRepository.delete(bankToDelete);
        return true;
    }

    public Boolean patchBankByBankId(final Bank bank, final Long id) {
        var bankToUpdate = findBankById(id);

        updateBankAttributes(bankToUpdate, bank);
        bankRepository.save(bankToUpdate);
        return true;
    }

    public Boolean patchBankByBankName(final Bank bank, final String bankName) {
        var bankToUpdate = findBankByBankName(bankName);

        updateBankAttributes(bankToUpdate, bank);
        bankRepository.save(bankToUpdate);
        return true;
    }

    // region Helpers
    private <T> Bank findBankByTypeOrThrowException(final T type) {
        if (type == null) {
            throw new IllegalArgumentException("'T' type provided cannot be null or empty.");
        }

        if (type instanceof Long) {
            var bankEntity = bankRepository.findBankByBankId((Long) type);

            if (bankEntity == null) {
                throw new EntityNotFoundException(String.format(BANK_WITH_ID_NOT_FOUND, (Long) type));
            }
            return bankEntity;

        } else if (type instanceof String) {
            var bankEntity = bankRepository.findBanksByBankName((String) type);

            if (bankEntity == null) {
                throw new EntityNotFoundException(String.format(BANK_WITH_NAME_NOT_FOUND, (String) type));
            }
            return bankEntity.get(0);

        }

        throw new IllegalArgumentException("Type provided is not supported.");
    }

    private void updateBankAttributes(
            final Bank target,
            final Bank source
    ) {
        target.setBankName(source.getBankName() == null ? target.getBankName() : source.getBankName());
        target.setBankYear(source.getBankYear() == null ? target.getBankYear() : source.getBankYear());
        target.setBankAddress(source.getBankAddress() == null ? target.getBankAddress() : source.getBankAddress());
        target.setBankAtms(source.getBankAtms() == null ? target.getBankAtms() : source.getBankAtms());
        target.setBankBranches(source.getBankBranches() == null ? target.getBankBranches() : source.getBankBranches());
        target.setBankEmployees(source.getBankEmployees() == null ? target.getBankEmployees() : source.getBankEmployees());
        target.setModifiedAt(Instant.now());
    }
    // endregion
}
