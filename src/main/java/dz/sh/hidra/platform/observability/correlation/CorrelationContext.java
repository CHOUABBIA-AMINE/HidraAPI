/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Holds the correlation and request identifiers for the current request thread.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import dz.sh.hidra.kernel.domain.value.CorrelationId;
import dz.sh.hidra.kernel.domain.value.RequestId;
import java.util.Optional;

public record CorrelationContext(CorrelationId correlationId, RequestId requestId) {

    private static final ThreadLocal<CorrelationContext> CURRENT = new ThreadLocal<>();

    public CorrelationContext {
        if (correlationId == null) {
            throw new IllegalArgumentException("CorrelationId must not be null.");
        }
        if (requestId == null) {
            throw new IllegalArgumentException("RequestId must not be null.");
        }
    }

    public static void set(CorrelationContext context) {
        if (context == null) {
            clear();
            return;
        }
        CURRENT.set(context);
    }

    public static Optional<CorrelationContext> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static CorrelationId currentCorrelationId() {
        return current()
                .map(CorrelationContext::correlationId)
                .orElse(null);
    }

    public static RequestId currentRequestId() {
        return current()
                .map(CorrelationContext::requestId)
                .orElse(null);
    }

    public static void clear() {
        CURRENT.remove();
    }
}
