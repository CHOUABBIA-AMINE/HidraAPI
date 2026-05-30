/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.result
 *
 * @Description : Immutable generic result wrapper for application operations.
 *
 */
package dz.sh.hidra.kernel.application.result;

import java.util.Optional;

public record OperationResult<T>(ResultStatus status, T value, String message, String errorCode) {

    public OperationResult {
        if (status == null) {
            throw new IllegalArgumentException("Result status must not be null.");
        }
        if (message != null) {
            message = message.trim();
            if (message.isBlank()) {
                message = null;
            }
        }
        if (errorCode != null) {
            errorCode = errorCode.trim();
            if (errorCode.isBlank()) {
                errorCode = null;
            }
        }
        if (status == ResultStatus.SUCCESS && (message != null || errorCode != null)) {
            throw new IllegalArgumentException("Successful results must not include failure metadata.");
        }
        if (status != ResultStatus.SUCCESS && message == null) {
            throw new IllegalArgumentException("Failure results must include a message.");
        }
    }

    public static <T> OperationResult<T> success(T value) {
        return new OperationResult<>(ResultStatus.SUCCESS, value, null, null);
    }

    public static OperationResult<Void> success() {
        return new OperationResult<>(ResultStatus.SUCCESS, null, null, null);
    }

    public static <T> OperationResult<T> failure(String message) {
        return failure(ResultStatus.FAILURE, message, null);
    }

    public static <T> OperationResult<T> failure(ResultStatus status, String message) {
        return failure(status, message, null);
    }

    public static <T> OperationResult<T> failure(ResultStatus status, String message, String errorCode) {
        if (status == ResultStatus.SUCCESS) {
            throw new IllegalArgumentException("Failure status must not be SUCCESS.");
        }
        return new OperationResult<>(status, null, message, errorCode);
    }

    public boolean successful() {
        return status == ResultStatus.SUCCESS;
    }

    public boolean failed() {
        return !successful();
    }

    public Optional<T> optionalValue() {
        return Optional.ofNullable(value);
    }

    public Optional<String> optionalMessage() {
        return Optional.ofNullable(message);
    }

    public Optional<String> optionalErrorCode() {
        return Optional.ofNullable(errorCode);
    }
}
