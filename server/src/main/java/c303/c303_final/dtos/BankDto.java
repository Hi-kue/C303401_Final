package c303.c303_final.dtos;

import c303.c303_final.model.Employee;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Value
public class BankDto implements Serializable {
    Long bankId;
    String bankName;
    Date bankYear;
    String bankAddress;
    Integer bankAtms;
    Integer bankBranches;
    List<Employee> bankEmployees;
    Instant CreatedAt;
    Instant ModifiedAt;
}