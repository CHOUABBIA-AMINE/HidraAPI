/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionAuditEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.topology
 *
 * @Description : Records and verifies exact pressure restriction publication witnesses.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.topology;

import java.time.Instant;
import java.util.UUID;

public interface PressureRestrictionAuditEvidenceContract {
    enum Kind {
        REVISION("APPROVE", "APPROVED"), SUPERSEDE("SUPERSEDE", "SUPERSEDED"), WITHDRAW("WITHDRAW", "WITHDRAWN");
        private final String verb;
        private final String outcome;

        Kind(String verb, String outcome) {
            this.verb = verb;
            this.outcome = outcome;
        }

        public String actionCode() { return verb + "_OPERATING_PRESSURE_RESTRICTION"; }
        public String typeId() { return "t38_pressure_" + name().toLowerCase(java.util.Locale.ROOT); }
        public String eventCode() { return "OPERATING_PRESSURE_RESTRICTION_" + outcome; }
    }

    record Witness(Kind kind, UUID candidate, String reviewer, String instance, String task, String action,
                   Instant actedAt) { }

    String appendPressureWitness(Witness witness);
    boolean matchesPressureWitness(String eventId, Witness witness);
}
