package c303.c303_final.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    String message;
    private HttpStatus status;

    // @note: This is the payload that will usually be returned
    private T payload;

    // @note: If there is an error, this will be the new payload.
    private String errorTrace;

    //region Timestamp
    private Instant timestamp;
    //endregion
}
