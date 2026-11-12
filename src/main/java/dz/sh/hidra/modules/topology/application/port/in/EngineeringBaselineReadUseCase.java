/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineReadUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Queries immutable baseline publications under exact protected owner scope.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.Receipt;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface EngineeringBaselineReadUseCase {
    enum Action { EXACT, CURRENT, EFFECTIVE, HISTORY, COMPARE }

    record Query(Action action, String company, String pipeline, UUID receiptId,
                 UUID otherReceiptId, Instant asOf, int limit, String cursor) {
        public Query {
            Objects.requireNonNull(action);
            company = EngineeringBaselineManifest.identity(company);
            pipeline = EngineeringBaselineManifest.identity(pipeline);
            switch (action) {
                case EXACT -> {
                    Objects.requireNonNull(receiptId);
                    if (otherReceiptId != null || asOf != null || limit != 0 || cursor != null)
                        throw new IllegalArgumentException("Invalid exact baseline query");
                }
                case CURRENT -> {
                    if (receiptId != null || otherReceiptId != null || asOf != null || limit != 0 || cursor != null)
                        throw new IllegalArgumentException("Invalid current baseline query");
                }
                case EFFECTIVE -> {
                    Objects.requireNonNull(asOf);
                    if (receiptId != null || otherReceiptId != null || limit != 0 || cursor != null)
                        throw new IllegalArgumentException("Invalid effective baseline query");
                }
                case HISTORY -> {
                    Objects.requireNonNull(asOf);
                    if (receiptId != null || otherReceiptId != null || limit < 1 || limit > 50
                            || cursor != null && !cursor.matches("[1-9][0-9]{0,18}:[1-9][0-9]{0,18}"))
                        throw new IllegalArgumentException("Invalid bounded history query");
                }
                case COMPARE -> {
                    Objects.requireNonNull(receiptId);
                    Objects.requireNonNull(otherReceiptId);
                    if (asOf != null || limit != 0 || cursor != null)
                        throw new IllegalArgumentException("Invalid baseline comparison");
                }
            }
        }
    }

    record Result(Receipt primary, Receipt secondary, List<Receipt> history, String nextCursor) {
        public Result { history = List.copyOf(Objects.requireNonNull(history)); }
    }

    Result read(Query query);
}
