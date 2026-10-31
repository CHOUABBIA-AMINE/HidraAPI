/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationManufacturerCharacteristicAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.simulation
 *
 * @Description : Opaque native Identity authority for exact manufacturer-source operations.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.simulation;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Descriptors do not constitute permission, and contributions are not transferable authorizations. */
public interface SimulationManufacturerCharacteristicAuthorizationContract {
    String RESOURCE_TYPE = "MANUFACTURER_CHARACTERISTIC_REVISION";
    enum Operation { SAVE, READ_REVISION, LIST_HISTORY, READ_CERTIFICATE, QUALIFY }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    static String permissionCode(Operation operation) {
        return switch (Objects.requireNonNull(operation)) {
            case SAVE -> "simulation:manufacturer-characteristic:save";
            case READ_REVISION -> "simulation:manufacturer-characteristic:read";
            case LIST_HISTORY -> "simulation:manufacturer-characteristic:history";
            case READ_CERTIFICATE -> "simulation:manufacturer-certificate:read";
            case QUALIFY -> "simulation:manufacturer-characteristic:qualify";
        };
    }
    record Request(Operation operation, String companyPartyId, String sourceId, String revisionId) {
        public Request {
            Objects.requireNonNull(operation);
            exact(companyPartyId); exact(sourceId);
            if (operation != Operation.LIST_HISTORY) exact(revisionId);
            else if (revisionId != null) throw new IllegalArgumentException("History is source-scoped");
        }
        @Override public String toString() { return "ManufacturerAuthorizationRequest[redacted]"; }
    }
    record Contribution(Request request, Decision decision, String bindingId, String sessionId, String userId,
                        Instant observedAt, Instant validUntil, int backendPid, long transactionId, String databaseRole) {
        public Contribution {
            Objects.requireNonNull(request); Objects.requireNonNull(decision);
            exact(bindingId); exact(sessionId); exact(userId); exact(databaseRole);
            Objects.requireNonNull(observedAt); Objects.requireNonNull(validUntil);
            if (!observedAt.isBefore(validUntil) || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid original-transaction Identity result");
        }
        @Override public String toString() { return "ManufacturerNativeContribution[redacted]"; }
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared, Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    private static void exact(String s) {
        if (s == null || s.isBlank() || !s.equals(s.strip()) || s.codePointCount(0,s.length()) > 80
                || s.codePoints().anyMatch(c -> Character.isISOControl(c) || c >= 0xd800 && c <= 0xdfff))
            throw new IllegalArgumentException("Noncanonical Identity resource identity");
    }
}
