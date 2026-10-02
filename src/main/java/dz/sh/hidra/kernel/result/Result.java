/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Result
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.result
 *
 * @Description : Represents a framework-neutral application result.
 *
 */
package dz.sh.hidra.kernel.result;

import java.util.Objects;

/**
 * Standard application result wrapper independent from HTTP.
 *
 * @param status result status
 * @param value successful result payload
 * @param message optional human-readable message
 * @param errorCode machine-readable error code for failures
 * @param <T> payload type
 */
public record Result<T>(
        ResultStatus status,
        T value,
        String message,
        String errorCode
) {

    public Result {
        Objects.requireNonNull(status, "Result status must not be null.");
        message = normalize(message);
        errorCode = normalize(errorCode);
    }

    /**
     * Creates a successful result.
     *
     * @param value result payload
     * @param <T> payload type
     * @return successful result
     */
    public static <T> Result<T> success(T value) {
        return new Result<>(ResultStatus.SUCCESS, value, null, null);
    }

    /**
     * Creates a successful result with a message.
     *
     * @param value result payload
     * @param message optional message
     * @param <T> payload type
     * @return successful result
     */
    public static <T> Result<T> success(T value, String message) {
        return new Result<>(ResultStatus.SUCCESS, value, message, null);
    }

    /**
     * Creates a failure result.
     *
     * @param status failure status
     * @param message failure message
     * @param errorCode machine-readable error code
     * @param <T> payload type
     * @return failure result
     */
    public static <T> Result<T> failure(ResultStatus status, String message, String errorCode) {
        if (status == ResultStatus.SUCCESS) {
            throw new IllegalArgumentException("Failure result must not use SUCCESS status.");
        }
        return new Result<>(status, null, message, errorCode);
    }

    /**
     * Indicates whether the result is successful.
     *
     * @return true when status is SUCCESS
     */
    public boolean isSuccess() {
        return status == ResultStatus.SUCCESS;
    }

    private static String normalize(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        return text.trim();
    }
}
