package c303.c303_final.controller;

import c303.c303_final.model.Bank;
import c303.c303_final.service.BankService;
import c303.c303_final.util.ApiResponse;
import c303.c303_final.util.ApiResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/bank/")
@RestController
public class BankController {
    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // region GET MAPPINGS
    @GetMapping("find/all")
    public ResponseEntity<ApiResponse<List<Bank>>> findAllBanks() {
        var banks = bankService.findAllBanks();

        if (banks.isEmpty()) {
            return ApiResponseHandler
                    .error("No Bank entities currently exist, please create Banks entity.",
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(banks);
    }

    @GetMapping("find/id/{bankId}")
    public ResponseEntity<ApiResponse<Bank>> findBankByBankId(@PathVariable("bankId") Long bankId) {
        var bank = bankService.findBankById(bankId);

        if (bank == null) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(bank);
    }

    @GetMapping("find/name/{bankName}")
    public ResponseEntity<ApiResponse<Bank>> findBankByBankName(@PathVariable("bankName") String bankName) {
        var bank = bankService.findBankByBankName(bankName);

        if (bank == null) {
            return ApiResponseHandler
                    .error(String.format("Bank with name %s not found.", bankName),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(bank);
    }
    // endregion

    // region POST MAPPING(S)
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Bank>> addBank(@RequestBody Bank bank) {
        var bankToAdd = bankService.createBank(bank);

        if (bankToAdd == null) {
            return ApiResponseHandler
                    .error("Something went wrong with adding a new Bank entity.",
                            HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ApiResponseHandler.success(bankToAdd);
    }
    // endregion

    // region PUT MAPPINGS
    @PutMapping("find/update/id/{bankId}")
    public ResponseEntity<ApiResponse<Boolean>> updateBankByBankId(@PathVariable("bankId") Long bankId, @RequestBody Bank bank) {
        var updatedBank = bankService.updateBankByBankId(bank, bankId);

        if (!updatedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(updatedBank);
    }

    @PostMapping("find/update/name/{bankName}")
    public ResponseEntity<ApiResponse<Boolean>> updateBankByBankName(@PathVariable("bankName") String bankName, @RequestBody Bank bank) {
        var updatedBank = bankService.updateBankByBankName(bank, bankName);

        if (!updatedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with name %s not found.", bankName),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(updatedBank);
    }
    // endregion

    // region PATCH MAPPINGS
    @PatchMapping("find/patch/id/{bankId}")
    public ResponseEntity<ApiResponse<Boolean>> patchBankByBankId(@PathVariable("bankId") Long bankId, @RequestBody Bank bank) {
        var patchedBank = bankService.patchBankByBankId(bank, bankId);

        if (!patchedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(patchedBank);
    }

    @PatchMapping("find/patch/name/{bankName}")
    public ResponseEntity<ApiResponse<Boolean>> patchBankByBankName(@PathVariable("bankName") String bankName, @RequestBody Bank bank) {
        var patchedBank = bankService.patchBankByBankName(bank, bankName);

        if (!patchedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with name %s not found.", bankName),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(patchedBank);
    }
    // endregion

    // region DELETE MAPPINGS
    @DeleteMapping("find/delete/id/{bankId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteBankByBankId(@PathVariable("bankId") Long bankId) {
        var deleted = bankService.deleteBankByBankId(bankId);

        if (!deleted) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(deleted);
    }

    @DeleteMapping("find/delete/name/{bankName}")
    public ResponseEntity<ApiResponse<Boolean>> deleteBankByBankName(@PathVariable("bankName") String bankName) {
        var deleted = bankService.deleteBankByBankName(bankName);

        if (!deleted) {
            return ApiResponseHandler
                    .error(String.format("Bank with name %s not found.", bankName),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(deleted);
    }
    // endregion
}
