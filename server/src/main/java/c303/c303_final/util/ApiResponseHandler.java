package c303.c303_final.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

public class ApiResponseHandler {
    private ApiResponseHandler() {
        // Private constructor to prevent class instantiation.
    }

    public static <T> ResponseEntity<ApiResponse <T>> success(final T payload) {
        return payloadSuccess("The Operation was completed successfully", HttpStatus.OK, payload);
    }

    public static <T> ResponseEntity<ApiResponse <T>> payloadSuccess(
            final String message,
            final HttpStatus status,
            final T payload,
            final Object... args // @note: Optional args to pass to the message (e.g., id).
    ) {
        return ResponseEntity
                .status(status == null ? HttpStatus.OK : status)
                .body(ApiResponse.<T>builder()
                        .message(String.format(message, args))
                        .status(status == null ? HttpStatus.OK : status)
                        .payload(payload)
                        .timestamp(Instant.now())
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse <T>> error(
            final String message,
            final HttpStatus status,
            final Object... args // @note: Optional args to pass to the message (e.g., id).
    ) {
        return ResponseEntity
                .status(status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status)
                .body(ApiResponse.<T>builder()
                        .status(status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status)
                        .errorTrace(String.format(message, args))
                        .timestamp(Instant.now())
                        .build());

    }

    public static <T> ResponseEntity<ApiResponse <T>> payloadError(
            final String message,
            final HttpStatus status,
            final T payload,
            final Object... args // @note: Optional args to pass to the message (e.g., id).
    ) {
        return ResponseEntity
                .status(status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status)
                .body(ApiResponse.<T>builder()
                        .status(status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status)
                        .errorTrace(String.format(message, args))
                        .payload(payload)
                        .timestamp(Instant.now())
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse <T>> created(
            final String message,
            final HttpStatus status,
            final T payload,
            final Object... args // @note: Optional args to pass to the message (e.g., id).
    ) {
        if (status.is4xxClientError() || status.is5xxServerError()) {
            return error(message, status, args);
        }

        return payloadSuccess(message, status, payload, args);
    }

    public static <T> ResponseEntity<ApiResponse <List<T>>> collection(
            final String message,
            final HttpStatus status,
            final List<T> payload,
            final Object... args
    ) {
        if (status.is4xxClientError() || status.is5xxServerError()) {
            return error(message, status, args);
        }

        if (args.length == 0) {
            return ResponseEntity
                    .status(status)
                    .body(ApiResponse.<List<T>>builder()
                            .message(message)
                            .status(status)
                            .payload(payload)
                            .timestamp(Instant.now())
                            .build());
        }

        return ResponseEntity
                .status(status)
                .body(ApiResponse.<List<T>>builder()
                        .message(String.format(message, args))
                        .status(status)
                        .payload(payload)
                        .timestamp(Instant.now())
                        .build());
    }
}
