package c303.c303_final.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @NotBlank(message = "Bank name is a required field, and cannot be null or empty.")
    @Size(min = 1, max = 255, message = "Bank name must be between 1 and 255 characters long.")
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull(message = "Bank year is a required field, and cannot be null.")
    @Past(message = "Bank year must be provided in the past, cannot be present or future.")
    @Column(name = "bank_year", nullable = false)
    private Date bankYear;

    @NotBlank(message = "Bank address is a required field, and cannot be null or empty.")
    @Size(min = 20, max = 255, message = "Bank address must be between 20 and 255 characters long.")
    @Column(name = "bank_address", nullable = false)
    private String bankAddress;

    @NotNull(message = "Bank atms is a required field, and cannot be null.")
    @Min(value = 1, message = "Bank atms must be greater than or equal to 1.")
    @Column(name = "bank_atms", nullable = false)
    private Integer bankAtms;

    @NotNull(message = "Bank branches is a required field, and cannot be null.")
    @Min(value = 1, message = "Bank branches must be greater than or equal to 1.")
    @Column(name = "bank_branches", nullable = false)
    private Integer bankBranches;


    @NotNull(message = "Bank employees is a required field, and cannot be null.")
    @Min(value = 1, message = "Bank employees must contain at lest one employee.")
    @Column(name = "bank_employees", nullable = false)
    private Integer bankEmployees;


    //region Timestamps
    @Column(name="created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private Instant CreatedAt;

    @Column(name="modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @LastModifiedDate
    private Instant ModifiedAt;
    //endregion
}
