package c303.c303_final.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Embeddable
public class Employee {
    @NotNull(message = "Employee id is a required field, and cannot be null.")
    @Min(value = 1, message = "Employee id must be greater than or equal to 1.")
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @NotBlank(message = "Employee name is a required field, and cannot be null or empty.")
    @Size(min = 1, max = 255, message = "Employee name must be between 1 and 255 characters long.")
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @NotNull(message = "Employee department is a required field, and cannot be null.")
    @Min(value = 1, message = "Employee department must be greater than or equal to 1.")
    @Column(name = "employee_department", nullable = false)
    private Integer employeeDepartment;

    //region Timestamps
    @Column(name="created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private Instant CreatedAt;

    @Column(name="modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @LastModifiedDate
    private Instant ModifiedAt;
    //endregion
}
