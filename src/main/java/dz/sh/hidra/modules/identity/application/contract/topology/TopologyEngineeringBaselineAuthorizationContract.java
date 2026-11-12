/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyEngineeringBaselineAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Original native baseline authority and independently checked existing source permissions.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/** Scalar descriptors request authority; only issuer-private opaque admissions establish it. */
public interface TopologyEngineeringBaselineAuthorizationContract {
    String RESOURCE_TYPE = "ENGINEERING_BASELINE_DRAFT";
    enum Action { CREATE, READ, UPDATE, VALIDATE, SUBMIT, REVIEW, PUBLISH }
    enum SourceKind { NETWORK_PUBLICATION, STATION_CONFIGURATION, INSTALLED_UNIT,
        CHARACTERISTIC_LINK, MANUFACTURER_CHARACTERISTIC, PRODUCT_PUBLICATION }
    record Request(Action action, String companyPartyId, String pipelineSystemId) {
        public Request { Objects.requireNonNull(action); exact(companyPartyId); exact(pipelineSystemId); }
        @Override public String toString() { return "BaselineAuthorizationRequest[redacted]"; }
    }
    /** Manufacturer references use the existing native source-scope SHA256, not a new permission namespace. */
    record Source(SourceKind kind, String referenceId, List<String> referencedUnitIds) {
        public Source(SourceKind kind, String referenceId) { this(kind,referenceId,List.of()); }
        public Source {
            Objects.requireNonNull(kind); exact(referenceId);
            if (kind == SourceKind.MANUFACTURER_CHARACTERISTIC && !referenceId.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Exact native manufacturer source scope required");
            referencedUnitIds=List.copyOf(Objects.requireNonNull(referencedUnitIds));
            if(referencedUnitIds.size()>TopologyStationConfigurationAuthorizationContract.MAX_REFERENCED_UNITS)
                throw new IllegalArgumentException("Bounded original unit references required");
            for(String unit:referencedUnitIds)exact(unit);
            if(referencedUnitIds.stream().distinct().count()!=referencedUnitIds.size())
                throw new IllegalArgumentException("Duplicate original unit reference");
            if(kind==SourceKind.CHARACTERISTIC_LINK ? referencedUnitIds.size()!=1
                    : kind!=SourceKind.STATION_CONFIGURATION && !referencedUnitIds.isEmpty())
                throw new IllegalArgumentException("Source-specific original unit references required");
            referencedUnitIds=referencedUnitIds.stream().sorted().toList();
        }
        @Override public String toString() { return "BaselineSourceAuthorization[redacted]"; }
    }
    record Origin(String bindingId, String sessionId, String userId, Instant sampledAt, Instant validUntil,
                  int backendPid, long transactionId, String databaseRole) {
        public Origin {
            exact(bindingId); exact(sessionId); exact(userId); exact(databaseRole);
            Objects.requireNonNull(sampledAt); Objects.requireNonNull(validUntil);
            if (!sampledAt.isBefore(validUntil) || backendPid < 1 || transactionId < 1)
                throw new IllegalArgumentException("Invalid native baseline provenance");
        }
        @Override public String toString() { return "BaselineOrigin[redacted]"; }
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared, Request request);
    Origin contribute(Admission admission);
    void requireSourceRead(Admission admission, Source source);
    void requireCurrent(Admission admission);
    void requireCurrentReviewer(Admission admission, String reviewerId);
    List<ProtectedRelationFence> finalFences(Admission admission);
    /** SQL-free marker; the Topology owner must call this only after its actual last-statement guard. */
    void markFinal(Admission admission);
    static String permission(Action action) {
        return "topology:engineering-baseline:" + Objects.requireNonNull(action).name().toLowerCase(Locale.ROOT);
    }
    static String manufacturerScope(String company, String source, String revision) {
        exact(company); exact(source); exact(revision);
        String value = "HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|" + company.length() + ":" + company
                + "|" + source.length() + ":" + source + "|" + revision.length() + ":" + revision;
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
        catch (NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }
    private static void exact(String value) {
        if (value == null || value.isBlank() || !value.equals(value.strip()) || value.codePointCount(0,value.length()) > 80
                || value.codePoints().anyMatch(c -> Character.isISOControl(c) || c >= 0xd800 && c <= 0xdfff))
            throw new IllegalArgumentException("Exact bounded native identity required");
    }
}
