/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Classifies publication failures without exposing storage or native authority details.
 *
 */
package dz.sh.hidra.modules.topology.application.model;

import java.util.Objects;

public final class EngineeringRevisionPublicationFailure extends RuntimeException {
    public enum Reason { INVALID, UNAUTHENTICATED, DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE, INDETERMINATE }
    private final Reason reason;
    public EngineeringRevisionPublicationFailure(Reason reason) { this(reason, null); }
    public EngineeringRevisionPublicationFailure(Reason reason, Throwable cause) {
        super("Publication operation could not be completed", cause);
        this.reason = Objects.requireNonNull(reason);
    }
    public Reason reason() { return reason; }
}
