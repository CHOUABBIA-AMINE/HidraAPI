/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkbenchResource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Requires module-owned safe projections and principal-scoped workbench reads.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;

/**
 * Only explicitly approved implementations may be registered as Spring beans.
 * There are no production registrations initially. Module adapters own business row scope.
 */
public interface WorkbenchResource {
    WorkbenchResourceDefinition definition();

    /**
     * Resolve an immutable reader bound to this principal's permitted row scope, or deny.
     * This method must not read operational rows or counts. Administrator access still
     * requires a scoped reader. Both search rows and totals must apply the same scope.
     */
    Optional<ScopedReader> scope(Authentication authentication);

    interface ScopedReader {
        Page search(OperationalSearchRequest validatedRequest);

        /** An inaccessible identifier must return empty, just like an absent record. */
        Optional<Map<String, Object>> detail(String id);
    }

    record Page(List<Map<String, Object>> rows, long total) {
        public Page {
            if (rows == null || total < 0 || total < rows.size()) {
                throw new IllegalStateException("Invalid workbench page.");
            }
            rows = List.copyOf(rows);
        }
    }
}
