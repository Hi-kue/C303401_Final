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
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // region GET MAPPINGS
    @GetMapping("find/all")
    public ResponseEntity<ApiResponse<List<Bank>>> findAllBanks() {
        var banks = bankService.findAllBanks();

        if (banks.isEmpty()) {
            return ApiResponseHandler.collection(
                    "There are currently no bank entities in the database. Please create one.",
                    HttpStatus.OK,
                    banks);
        }

        return ApiResponseHandler.collection(
                "There are currently %s bank entities in the database.",
                HttpStatus.OK,
                banks,
                banks.size()
        );
    }

    @GetMapping("find/id")
    public ResponseEntity<ApiResponse<Bank>> findBankByBankId(
            @RequestParam(name = "bankId", required = false, defaultValue = "false") Long bankId
    ) {
        var bank = bankService.findBankById(bankId);

        if (bank == null) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(bank);
    }

    @GetMapping("find/name")
    public ResponseEntity<ApiResponse<Bank>> findBankByBankName(
            @RequestParam(name = "bankName", required = false, defaultValue = "false")  String bankName) {
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
    @PutMapping("find/update/id")
    public ResponseEntity<ApiResponse<Boolean>> updateBankByBankId(
            @RequestParam(name = "bankId", required = false, defaultValue = "false") Long bankId,
            @RequestBody Bank bank
    ) {
        var updatedBank = bankService.updateBankByBankId(bank, bankId);

        if (!updatedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(updatedBank);
    }

    @PostMapping("find/update/name")
    public ResponseEntity<ApiResponse<Boolean>> updateBankByBankName(
            @RequestParam(name = "bankName", required = false, defaultValue = "false") String bankName,
            @RequestBody Bank bank
    ) {
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
    @PatchMapping("find/patch/id")
    public ResponseEntity<ApiResponse<Boolean>> patchBankByBankId(
            @RequestParam(name = "bankId", required = false, defaultValue = "false") Long bankId,
            @RequestBody Bank bank
    ) {
        var patchedBank = bankService.patchBankByBankId(bank, bankId);

        if (!patchedBank) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(patchedBank);
    }

    @PatchMapping("find/patch/name")
    public ResponseEntity<ApiResponse<Boolean>> patchBankByBankName(
            @RequestParam(name = "bankName", required = false, defaultValue = "false") String bankName,
            @RequestBody Bank bank
    ) {
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
    @DeleteMapping("find/delete/id")
    public ResponseEntity<ApiResponse<Boolean>> deleteBankByBankId(
            @RequestParam(name = "bankId", required = false, defaultValue = "false") Long bankId
    ) {
        var deleted = bankService.deleteBankByBankId(bankId);

        if (!deleted) {
            return ApiResponseHandler
                    .error(String.format("Bank with id %s not found.", bankId),
                            HttpStatus.NOT_FOUND);
        }

        return ApiResponseHandler.success(deleted);
    }

    @DeleteMapping("find/delete/name")
    public ResponseEntity<ApiResponse<Boolean>> deleteBankByBankName(
            @RequestParam(name = "bankName", required = false, defaultValue = "false") String bankName
    ) {
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
