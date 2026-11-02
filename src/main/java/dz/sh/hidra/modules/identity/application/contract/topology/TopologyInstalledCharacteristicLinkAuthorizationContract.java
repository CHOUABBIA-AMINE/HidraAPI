/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledCharacteristicLinkAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Genuine original-transaction Identity authority for a draft characteristic link.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Separate Topology link, unit and Simulation source authorizations must all pass. */
public interface TopologyInstalledCharacteristicLinkAuthorizationContract {
    enum Operation { SAVE_DRAFT, READ_CURRENT, READ_REVISION, LIST_HISTORY, APPROVE, READ_APPROVAL, LIST_APPROVAL_HISTORY }
    String LINK_RESOURCE = "INSTALLED_CHARACTERISTIC_LINK";
    String UNIT_RESOURCE = "INSTALLED_UNIT";
    String SOURCE_RESOURCE = "MANUFACTURER_CHARACTERISTIC_REVISION";
    record Request(Operation operation, String companyPartyId, String installedUnitId, String linkId) {
        public Request {
            Objects.requireNonNull(operation);
            exact(companyPartyId); exact(installedUnitId); exact(linkId);
        }
        @Override public String toString() { return "CharacteristicAuthorizationRequest[redacted]"; }
    }
    record Source(String companyPartyId, String sourceId, String revisionId) {
        public Source {
            exact(companyPartyId); exact(sourceId); exact(revisionId);
        }
        @Override public String toString() { return "CharacteristicSource[redacted]"; }
    }
    record Origin(String bindingId, String sessionId, String userId, Instant observedAt, Instant validUntil,
                  int backendPid, long transactionId, String databaseRole) {
        public Origin {
            exact(bindingId); exact(sessionId); exact(userId); exact(databaseRole);
            Objects.requireNonNull(observedAt); Objects.requireNonNull(validUntil);
            if(!observedAt.isBefore(validUntil) || backendPid < 1 || transactionId < 1)
                throw new IllegalArgumentException("Invalid native provenance");
        }
        @Override public String toString() { return "CharacteristicOrigin[opaque]"; }
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared, Request request);
    Origin contribute(Admission admitted);
    /** Same authenticated PID/XID/role, source-specific read grant; never a separate JWT. */
    void requireSourceRead(Admission admitted, Source source);
    void requireCurrent(Admission admitted);
    List<ProtectedRelationFence> finalFences(Admission admitted);
    /** Called only after the physical protected SQL final guard succeeds. */
    void markFinal(Admission admitted);
    static String permission(Operation operation) {
        return "topology:installed-characteristic:" + switch(operation) {
            case SAVE_DRAFT -> "save";
            case APPROVE -> "approve";
            case READ_CURRENT, READ_REVISION, READ_APPROVAL -> "read";
            case LIST_HISTORY, LIST_APPROVAL_HISTORY -> "history";
        };
    }
    private static void exact(String value) {
        if(value==null||value.isBlank()||!value.equals(value.strip())
                ||value.codePointCount(0,value.length())>80
                ||value.codePoints().anyMatch(c->Character.isISOControl(c)||c>=0xd800&&c<=0xdfff))
            throw new IllegalArgumentException("Exact bounded identity required");
    }
}
