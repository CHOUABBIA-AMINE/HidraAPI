/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyProtectedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Defines the trusted paired Identity contract with opaque preparation and scalar exports.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;

import java.time.Instant;
import java.util.Objects;

/** Identity contribution only; the trusted coordinator owns all other owner and commit gates. */
public interface TopologyProtectedAuthorizationContract {
    Prepared prepare();

    Contribution contribute(Prepared prepared, Request request);

    /** A marker is not proof. Only the issuing adapter's private implementation is accepted. */
    interface Prepared {}

    enum Decision {
        PERMIT,
        DENY,
        INDETERMINATE
    }

    static String permissionCode(Operation operation) {
        Objects.requireNonNull(operation, "operation");
        return "topology:station-revision:"
                + switch (operation) {
                    case AUTHOR -> "author";
                    case PUBLISH -> "publish";
                    case READ_CURRENT -> "read-current";
                    case READ_HISTORY -> "read-history";
                    case LIST_HISTORY -> "list-history";
                    case WITHDRAW -> "withdraw";
                    case REACTIVATE -> "reactivate";
                    case TRANSFER -> "transfer";
                };
    }

    static Decision combine(Decision company, Decision facility) {
        Objects.requireNonNull(company, "companyDecision");
        Objects.requireNonNull(facility, "facilityDecision");
        if (company == Decision.DENY || facility == Decision.DENY) return Decision.DENY;
        if (company == Decision.INDETERMINATE || facility == Decision.INDETERMINATE)
            return Decision.INDETERMINATE;
        return Decision.PERMIT;
    }

    /** Immutable scalar data, never a caller-supplied bearer attestation or global permit. */
    record Contribution(
            String bindingId,
            String sessionId,
            String userId,
            Operation operation,
            String permissionCode,
            String companyPartyId,
            String facilityId,
            Decision companyDecision,
            Decision facilityDecision,
            Decision combinedDecision,
            Instant sampledAt,
            Instant validUntil,
            int backendPid,
            long transactionId) {
        public Contribution {
            for (String value :
                    new String[] {bindingId, sessionId, userId, companyPartyId, facilityId})
                if (value == null || value.isBlank() || !value.equals(value.strip()))
                    throw new IllegalArgumentException("Invalid paired contribution reference.");
            if (operation == null
                    || !TopologyProtectedAuthorizationContract.permissionCode(operation)
                            .equals(permissionCode)
                    || combinedDecision != combine(companyDecision, facilityDecision)
                    || sampledAt == null
                    || validUntil == null
                    || !validUntil.isAfter(sampledAt)
                    || backendPid <= 0
                    || transactionId <= 0)
                throw new IllegalArgumentException("Invalid paired contribution.");
        }

        @Override
        public String toString() {
            return "Contribution[paired Identity scalars]";
        }
    }
}
