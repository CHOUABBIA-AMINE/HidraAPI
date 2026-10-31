/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedAuthorizationEvidenceReader
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Loads bounded fresh fenced Identity evidence for synchronous assessment without authority export.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract.Evidence;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationExpressionStabilityCompiler;
import dz.sh.hidra.modules.identity.domain.service.ExternalClaimMatchEvaluator;
import dz.sh.hidra.modules.identity.application.port.out.BoundSourceClaimEvidenceRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant;
import dz.sh.hidra.modules.identity.infrastructure.security.TrustedAuthenticationVerificationRegistry;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.value.BoundSourceClaimEvidence;
import dz.sh.hidra.modules.identity.domain.value.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

/**
 * Diagnostic inputs are untrusted. Only a later actual F-to-reader owner invocation can establish
 * authority.
 */
@Repository
public class JdbcProtectedAuthorizationEvidenceReader {
    private static final String PREFIX = "public.hidra_identity_";
    private static final List<String> RELATIONS =
            List.of(
                    "attribute_definition",
                    "authorization_policy",
                    "authorization_policy_rule",
                    "authorization_policy_version",
                    "external_group_mapping",
                    "external_identity",
                    "external_permission_mapping",
                    "external_role_mapping",
                    "group",
                    "group_role_grant",
                    "local_credential",
                    "login_session",
                    "operation_binding",
                    "permission",
                    "provider",
                    "role",
                    "role_permission_grant",
                    "subject_security_attribute",
                    "user",
                    "user_group_membership",
                    "user_permission_grant",
                    "user_role_grant");
    private static final String HELD =
            "SELECT n.nspname || '.' || c.relname FROM pg_locks l JOIN pg_class c ON"
                + " c.oid=l.relation JOIN pg_namespace n ON n.oid=c.relnamespace WHERE"
                + " l.pid=pg_backend_pid() AND l.locktype='relation' AND l.granted AND"
                + " l.mode='ShareRowExclusiveLock' AND n.nspname='public' AND c.relname IN ("
                    + String.join(
                            ",", RELATIONS.stream().map(v -> "'hidra_identity_" + v + "'").toList())
                    + ") ORDER BY 1";
    /** One fresh lock image per invocation; no context or authority result is cached. */
    private static final String QUALIFIED_PHYSICAL_FENCES =
            "WITH identity_claim_locks AS MATERIALIZED ("
                + " SELECT pid,locktype,granted,relation,mode FROM pg_catalog.pg_locks"
                + " WHERE pid=pg_catalog.pg_backend_pid() AND granted AND locktype='relation')"
                + " SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?"
                + " AND current_setting('transaction_isolation')='read committed'"
                + " AND current_setting('transaction_read_only')='off'"
                + " AND ARRAY(" + HELD.replace("pg_locks l", "identity_claim_locks l") + ")=ARRAY["
                + String.join(",", RELATIONS.stream().map(v -> "'" + PREFIX + v + "'").toList()) + "]::text[]"
                + " AND EXISTS(SELECT 1 FROM identity_claim_locks"
                + " WHERE relation='public.hidra_identity_external_mapping_generation'::regclass AND mode='AccessShareLock')"
                + " AND EXISTS(SELECT 1 FROM identity_claim_locks"
                + " WHERE relation='public.hidra_identity_bound_source_claim_evidence'::regclass AND mode='ShareLock')";
    private static final Set<String> CODES =
            Set.of(
                    "author",
                    "publish",
                    "read-current",
                    "read-history",
                    "list-history",
                    "withdraw",
                    "reactivate",
                    "transfer");
    private final JdbcTemplate jdbc;
    private final ProtectedOperationIdentityParticipant participant;
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final BoundSourceClaimEvidenceRepositoryPort companions;
    private final ClaimNameCatalogPort catalog;
    private final TrustedAuthenticationVerificationRegistry registry;
    private final AuthorizationExpressionStabilityCompiler syntax =
            new AuthorizationExpressionStabilityCompiler();
    private final ExternalClaimMatchEvaluator match = new ExternalClaimMatchEvaluator();

    /** Existing one-argument constructor remains for conservative diagnostic unit fixtures. */
    public JdbcProtectedAuthorizationEvidenceReader(JdbcTemplate jdbc) {
        this(jdbc, null, null, null, null, null);
    }

    @Autowired
    public JdbcProtectedAuthorizationEvidenceReader(
            JdbcTemplate jdbc,
            ProtectedOperationIdentityParticipant participant,
            OperationAuthenticationBindingRepositoryPort bindings,
            BoundSourceClaimEvidenceRepositoryPort companions,
            ClaimNameCatalogPort catalog,
            TrustedAuthenticationVerificationRegistry registry) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.participant = participant;
        this.bindings = bindings;
        this.companions = companions;
        this.catalog = catalog;
        this.registry = registry;
    }

    private record QualifiedClaims(ClaimCoverage coverage, String type) {}

    /**
     * The only claim-capable overload accepts an opaque issuer- and transaction-bound entry.
     * It must not delegate to the public, structural diagnostic overload.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public AuthorizationEvidence loadQualified(
            ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request) {
        return loadClaims(credential, request, false);
    }

    private AuthorizationEvidence loadClaims(
            ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request, boolean organizationRecheck) {
        return loadClaims(credential, request, organizationRecheck, new Rows());
    }

    private AuthorizationEvidence loadClaims(
            ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request, boolean organizationRecheck, Rows rows) {
        return loadClaims(credential,request,organizationRecheck,rows,false);
    }

    private AuthorizationEvidence loadClaims(
            ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request, boolean organizationRecheck, Rows rows, boolean productRecheck) {
        return loadClaims(credential,request,organizationRecheck,rows,productRecheck,null);
    }

    private AuthorizationEvidence loadClaims(
            ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request, boolean organizationRecheck, Rows rows,
            boolean productRecheck, Instant productSample) {
        return loadClaims(credential,request,organizationRecheck,rows,productRecheck,productSample,false);
    }
    private AuthorizationEvidence loadClaims(ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request,boolean organizationRecheck,Rows rows,
            boolean productRecheck,Instant productSample,boolean fluidFamily) {
        return loadClaims(credential,request,organizationRecheck,rows,productRecheck,productSample,fluidFamily,false);
    }
    private AuthorizationEvidence loadClaims(ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request,boolean organizationRecheck,Rows rows,
            boolean productRecheck,Instant productSample,boolean fluidFamily,boolean publicationFamily) {
        return loadClaims(credential,request,organizationRecheck,rows,productRecheck,productSample,fluidFamily,publicationFamily,false);
    }
    private AuthorizationEvidence loadClaims(ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request,boolean organizationRecheck,Rows rows,
            boolean productRecheck,Instant productSample,boolean fluidFamily,boolean publicationFamily,boolean installedUnitRecheck) {
        return loadClaims(credential,request,organizationRecheck,rows,productRecheck,productSample,
                fluidFamily,publicationFamily,installedUnitRecheck,false);
    }
    private AuthorizationEvidence loadClaims(ProtectedOperationIdentityParticipant.QualifiedClaimRead credential,
            AuthorizationEvaluationRequest request,boolean organizationRecheck,Rows rows,
            boolean productRecheck,Instant productSample,boolean fluidFamily,boolean publicationFamily,
            boolean installedUnitRecheck,boolean stationConfigurationRecheck) {
        try {
            rows.beginGraph();
            if (participant == null || bindings == null || companions == null
                    || registry == null || catalog == null) throw invalid();
            var contribution = stationConfigurationRecheck ? participant.currentStationConfigurationClaims(credential,request) : installedUnitRecheck ? participant.currentInstalledUnitClaims(credential,request) : productRecheck ? (publicationFamily ? participant.currentProductPublicationClaims(credential,request) : fluidFamily ? participant.currentSuppliedFluidClaims(credential,request) : participant.currentProductClaims(credential, request))
                    : organizationRecheck ? participant.currentOrganizationClaims(credential, request)
                    : participant.consumeClaims(credential, request);
            Evidence evidence = contribution.evidence();
            int pid = contribution.backendPid();
            long xid = contribution.transactionId();
            validate(evidence, request, pid, xid, participant.engineeringDraftCredential(credential),
                    participant.organizationCredential(credential), participant.productDraftCredential(credential),participant.suppliedFluidCredential(credential),participant.productPublicationCredential(credential),participant.installedUnitCredential(credential),participant.stationConfigurationCredential(credential));
            qualifiedPhysicalFences(pid, xid);
            OperationAuthenticationBinding original =
                    bindings.findByNativeIssuerAndJti(evidence.issuer(), evidence.jti())
                            .orElseThrow(JdbcProtectedAuthorizationEvidenceReader::invalid);
            registry.validatePersistentOrigin(original);
            if (!original.bindingId().equals(evidence.bindingId())
                    || !original.sessionId().equals(evidence.sessionId())
                    || !original.userId().equals(evidence.userId())
                    || !original.providerId().equals(evidence.providerId())
                    || !original.authenticationType().equals(evidence.authenticationType())
                    || original.providerSecurityVersion() != evidence.providerGeneration()
                    || !Objects.equals(original.externalIdentityId(), evidence.externalIdentityId())
                    || !Objects.equals(original.externalSecurityVersion(), evidence.externalGeneration())
                    || !Objects.equals(original.credentialId(), evidence.credentialId())
                    || !Objects.equals(original.credentialSecurityVersion(), evidence.credentialGeneration())
                    || !original.expiresAt().equals(evidence.expiresAt())
                    || !Objects.equals(original.sourceExpiresAt(), evidence.sourceExpiresAt()))
                throw invalid();
            QualifiedClaims qualified = null;
            ClaimNameCatalogPort.VersionedSelection selected = null;
            if (!"LOCAL".equals(original.authenticationType())) {
                selected = catalog.selectVersioned(original.providerId());
                var companion = companions.findByBindingId(original.bindingId());
                if (companion.isPresent()) {
                    BoundSourceClaimEvidence proof = companion.orElseThrow();
                    registry.validatePersistentSourceClaimOrigin(original, proof);
                    if (proof.evidenceFormat() != 1
                            || proof.mappingGeneration() != selected.generation()
                            || !proof.coverage().entries().keySet()
                                    .equals(new TreeSet<>(selected.selection().names()))
                            || !evidence.observedAt().isBefore(original.expiresAt())
                            || (original.sourceExpiresAt() != null
                                && !evidence.observedAt().isBefore(original.sourceExpiresAt())))
                        throw invalid();
                    qualified = new QualifiedClaims(proof.coverage(), original.authenticationType());
                }
            }
            Instant productAt = (participant.productDraftCredential(credential) || participant.suppliedFluidCredential(credential) || participant.productPublicationCredential(credential))
                    ? Objects.requireNonNull(productSample) : evidence.observedAt();
            if(productAt.isBefore(evidence.observedAt()) || !productAt.isBefore(contribution.validUntil())) throw invalid();
            AuthorizationEvidence result = read(evidence, request, rows, qualified, productAt);
            // Recheck actual origin and claim generation after the complete graph load.
            participant.requireCurrentClaims(credential, request);
            if (selected != null && !selected.equals(catalog.selectVersioned(original.providerId())))
                throw invalid();
            qualifiedPhysicalFences(pid, xid);
            // The final catalog query and lock checks also cross adapter boundaries.
            // Reconfirm the genuine caller after them, without replaying a consumed handle.
            participant.requireCurrentClaims(credential, request);
            return result;
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    /** Both graphs are loaded before any result is returned, at one F sample and physical XID. */
    @Transactional(propagation = Propagation.MANDATORY)
    public PairedAuthorizationEvidence loadQualifiedPair(
            ProtectedOperationIdentityParticipant.QualifiedClaimPair pair, Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.consumePair(pair, request);
            var company = loadQualified(reads.company(), reads.companyRequest());
            var facility = loadQualified(reads.facility(), reads.facilityRequest());
            participant.requireCurrentPair(pair, request);
            return new PairedAuthorizationEvidence(
                    reads.companyRequest(),
                    company,
                    reads.facilityRequest(),
                    facility,
                    reads.contribution());
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence loadQualifiedDraftPair(
            ProtectedOperationIdentityParticipant.DraftClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request){
        try {
            if(participant==null)throw invalid();
            var reads=participant.consumeDraftPair(pair,request);
            var company=loadQualified(reads.company(),reads.companyRequest());
            var network=loadQualified(reads.facility(),reads.facilityRequest());
            participant.requireCurrentDraftPair(pair,request);
            return new PairedAuthorizationEvidence(reads.companyRequest(),company,reads.facilityRequest(),network,reads.contribution());
        }catch(RuntimeException failure){throw invalid();}
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence loadQualifiedProductPair(
            ProtectedOperationIdentityParticipant.ProductClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Request request) {
        return productGraphs(pair,request,false);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence recheckProductPair(
            ProtectedOperationIdentityParticipant.ProductClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Request request) {
        return productGraphs(pair,request,true);
    }
    private PairedAuthorizationEvidence productGraphs(
            ProtectedOperationIdentityParticipant.ProductClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Request request,
            boolean recheck) {
        try {
            if(participant==null)throw invalid();
            var nativeRead=recheck?participant.currentProductReads(pair,request):participant.consumeProductPair(pair,request);
            var reads=nativeRead.reads();
            Instant at=nativeRead.sampledAt();
            // Invocation-local reuse only: fenced owner rows cannot change between these scopes.
            // Rechecks allocate a fresh map; every graph still charges all rows, including hits.
            var rows=new Rows(true);
            var company=loadClaims(reads.company(),reads.companyRequest(),false,rows,recheck,at);
            var product=loadClaims(reads.facility(),reads.facilityRequest(),false,rows,recheck,at);
            participant.requireCurrentProductPair(pair,request);
            return new PairedAuthorizationEvidence(reads.companyRequest(),company,reads.facilityRequest(),product,reads.contribution());
        }catch(RuntimeException failure){throw invalid();}
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence loadQualifiedSuppliedFluidPair(
            ProtectedOperationIdentityParticipant.SuppliedFluidClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract.Request request) {
        return suppliedFluidGraphs(pair,request,false);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence recheckSuppliedFluidPair(
            ProtectedOperationIdentityParticipant.SuppliedFluidClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract.Request request) {
        return suppliedFluidGraphs(pair,request,true);
    }
    private PairedAuthorizationEvidence suppliedFluidGraphs(
            ProtectedOperationIdentityParticipant.SuppliedFluidClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract.Request request,
            boolean recheck) {
        try {
            if(participant==null)throw invalid();
            var nativeRead=recheck?participant.currentSuppliedFluidReads(pair,request):participant.consumeSuppliedFluidPair(pair,request);
            var reads=nativeRead.reads();
            Instant at=nativeRead.sampledAt();
            // Invocation-local reuse only: fenced owner rows cannot change between these scopes.
            // Rechecks allocate a fresh map; every graph still charges all rows, including hits.
            var rows=new Rows(true);
            var company=loadClaims(reads.company(),reads.companyRequest(),false,rows,recheck,at,true);
            var product=loadClaims(reads.facility(),reads.facilityRequest(),false,rows,recheck,at,true);
            participant.requireCurrentSuppliedFluidPair(pair,request);
            return new PairedAuthorizationEvidence(reads.companyRequest(),company,reads.facilityRequest(),product,reads.contribution());
        }catch(RuntimeException failure){throw invalid();}
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence loadQualifiedProductPublicationPair(
            ProtectedOperationIdentityParticipant.ProductPublicationClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract.Request request) {
        return productPublicationGraphs(pair,request,false);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public PairedAuthorizationEvidence recheckProductPublicationPair(
            ProtectedOperationIdentityParticipant.ProductPublicationClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract.Request request) {
        return productPublicationGraphs(pair,request,true);
    }
    private PairedAuthorizationEvidence productPublicationGraphs(
            ProtectedOperationIdentityParticipant.ProductPublicationClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract.Request request,
            boolean recheck) {
        try {
            if(participant==null)throw invalid();
            var nativeRead=recheck?participant.currentProductPublicationReads(pair,request):participant.consumeProductPublicationPair(pair,request);
            var reads=nativeRead.reads();
            Instant at=nativeRead.sampledAt();
            // Invocation-local reuse only: fenced owner rows cannot change between these scopes.
            // Rechecks allocate a fresh map; every graph still charges all rows, including hits.
            var rows=new Rows(true);
            var company=loadClaims(reads.company(),reads.companyRequest(),false,rows,recheck,at,false,true);
            var product=loadClaims(reads.facility(),reads.facilityRequest(),false,rows,recheck,at,false,true);
            participant.requireCurrentProductPublicationPair(pair,request);
            return new PairedAuthorizationEvidence(reads.companyRequest(),company,reads.facilityRequest(),product,reads.contribution());
        }catch(RuntimeException failure){throw invalid();}
    }

    /** Synchronous Identity-private inputs only, not an exported permission. */
    public record PairedAuthorizationEvidence(
            AuthorizationEvaluationRequest companyRequest,
            AuthorizationEvidence company,
            AuthorizationEvaluationRequest facilityRequest,
            AuthorizationEvidence facility,
            ProtectedOperationIdentityParticipant.Contribution contribution) {}

    /** Only a private native group may load this family. All children precede disclosure. */
    public OrganizationAuthorizationEvidence loadQualifiedOrganizationGroup(
            ProtectedOperationIdentityParticipant.OrganizationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.consumeOrganizationGroup(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), false, rows));
            }
            participant.requireCurrentOrganizationGroup(group, request);
            return new OrganizationAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateOrganizationGroup(group);
            throw invalid();
        }
    }

    /** Re-read under retained fences without issuing or consuming another credential/sample. */
    public OrganizationAuthorizationEvidence recheckOrganizationGroup(
            ProtectedOperationIdentityParticipant.OrganizationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.currentOrganizationReads(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), true, rows));
            }
            participant.requireCurrentOrganizationGroup(group, request);
            return new OrganizationAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateOrganizationGroup(group);
            throw invalid();
        }
    }

    public record OrganizationAuthorizationEvidence(List<AuthorizationEvaluationRequest> requests,
            List<AuthorizationEvidence> evidence, ProtectedOperationIdentityParticipant.Contribution contribution) {
        public OrganizationAuthorizationEvidence {
            requests = List.copyOf(requests); evidence = List.copyOf(evidence);
            if (requests.isEmpty() || requests.size() != evidence.size()) throw invalid();
            Objects.requireNonNull(contribution);
        }
    }

    /** Only a private native group may load this family. All children precede disclosure. */
    public InstalledUnitAuthorizationEvidence loadQualifiedInstalledUnitGroup(
            ProtectedOperationIdentityParticipant.InstalledUnitClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.consumeInstalledUnitGroup(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), false, rows));
            }
            participant.requireCurrentInstalledUnitGroup(group, request);
            return new InstalledUnitAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateInstalledUnitGroup(group);
            throw invalid();
        }
    }

    /** Re-read under retained fences without issuing or consuming another credential/sample. */
    public InstalledUnitAuthorizationEvidence recheckInstalledUnitGroup(
            ProtectedOperationIdentityParticipant.InstalledUnitClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.currentInstalledUnitReads(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), false, rows, false, null, false, false, true));
            }
            participant.requireCurrentInstalledUnitGroup(group, request);
            return new InstalledUnitAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateInstalledUnitGroup(group);
            throw invalid();
        }
    }

    public record InstalledUnitAuthorizationEvidence(List<AuthorizationEvaluationRequest> requests,
            List<AuthorizationEvidence> evidence, ProtectedOperationIdentityParticipant.Contribution contribution) {
        public InstalledUnitAuthorizationEvidence {
            requests = List.copyOf(requests); evidence = List.copyOf(evidence);
            if (requests.isEmpty() || requests.size() != evidence.size()) throw invalid();
            Objects.requireNonNull(contribution);
        }
    }

    /** Only a private native group may load this family. All children precede disclosure. */
    public StationConfigurationAuthorizationEvidence loadQualifiedStationConfigurationGroup(
            ProtectedOperationIdentityParticipant.StationConfigurationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.consumeStationConfigurationGroup(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), false, rows));
            }
            participant.requireCurrentStationConfigurationGroup(group, request);
            return new StationConfigurationAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateStationConfigurationGroup(group);
            throw invalid();
        }
    }

    /** Re-read under retained fences without issuing or consuming another credential/sample. */
    public StationConfigurationAuthorizationEvidence recheckStationConfigurationGroup(
            ProtectedOperationIdentityParticipant.StationConfigurationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract.Request request) {
        try {
            if (participant == null) throw invalid();
            var reads = participant.currentStationConfigurationReads(group, request);
            var evidence = new java.util.ArrayList<AuthorizationEvidence>();
            // Private to this synchronous read: retained owner fences exclude other writers.
            // A final recheck always creates a fresh Rows, including after own-transaction writes.
            var rows = new Rows(true);
            for (int i = 0; i < reads.credentials().size(); i++) {
                evidence.add(loadClaims(reads.credentials().get(i), reads.requests().get(i), false, rows, false, null, false, false, false, true));
            }
            participant.requireCurrentStationConfigurationGroup(group, request);
            return new StationConfigurationAuthorizationEvidence(reads.requests(), evidence, reads.contribution());
        } catch (RuntimeException failure) {
            if (participant != null) participant.invalidateStationConfigurationGroup(group);
            throw invalid();
        }
    }

    public record StationConfigurationAuthorizationEvidence(List<AuthorizationEvaluationRequest> requests,
            List<AuthorizationEvidence> evidence, ProtectedOperationIdentityParticipant.Contribution contribution) {
        public StationConfigurationAuthorizationEvidence {
            requests = List.copyOf(requests); evidence = List.copyOf(evidence);
            if (requests.isEmpty() || requests.size() != evidence.size()) throw invalid();
            Objects.requireNonNull(contribution);
        }
    }

    private void qualifiedPhysicalFences(int pid, long xid) {
        if (!Boolean.TRUE.equals(jdbc.queryForObject(QUALIFIED_PHYSICAL_FENCES, Boolean.class, pid, xid)))
            throw invalid();
    }

    /**
     * The manufacturer family is exact, native-only and denied by default.
     * Called solely after a genuine one-use ProtectedOperationIdentityParticipant admission
     * has fixed the same physical PID/XID; no policy or grant is synthesized.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public AuthorizationEvidence loadManufacturer(
            Evidence origin, AuthorizationEvaluationRequest request, int backendPid, long transactionId) {
        try {
            if(origin==null || request==null || request.scope()==null
                || !origin.userId().equals(request.userId())
                || !"MANUFACTURER_CHARACTERISTIC_REVISION".equals(request.resourceType())
                || request.resourceReferenceId()==null
                || !request.resourceReferenceId().matches("[0-9a-f]{64}")
                || request.scope().scopeType()!=dz.sh.hidra.modules.identity.domain.value.ScopeType.CUSTOM
                || request.scope().scopeCodeSnapshot()!=null
                || request.scope().scopeReferenceId()==null
                || !(request.scope().scopeReferenceId().startsWith("simulation-company:")
                     || request.scope().scopeReferenceId().equals(
                           "simulation-source:"+request.resourceReferenceId()))
                || !java.util.Set.of(
                       "simulation:manufacturer-characteristic:save",
                       "simulation:manufacturer-characteristic:read",
                       "simulation:manufacturer-characteristic:history",
                       "simulation:manufacturer-certificate:read",
                       "simulation:manufacturer-characteristic:qualify").contains(request.permissionCode())
                || !java.util.Set.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC").contains(origin.authenticationType())
                || origin.observedAt()==null || origin.expiresAt()==null
                || !origin.observedAt().isBefore(origin.expiresAt()))
                 throw invalid();
            coordinates(backendPid,transactionId);
            qualifiedPhysicalFences(backendPid,transactionId);
            if(!RELATIONS.stream().map(PREFIX::concat).toList().equals(
                    jdbc.queryForList(HELD,String.class)))throw invalid();
            var graph=read(origin,request,new Rows());
            coordinates(backendPid,transactionId);
            return graph;
        }catch(RuntimeException failure) {throw invalid();}
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AuthorizationEvidence load(
            Evidence identity,
            AuthorizationEvaluationRequest request,
            int backendPid,
            long transactionId) {
        try {
            validate(identity, request, backendPid, transactionId);
            coordinates(backendPid, transactionId);
            if (!RELATIONS.stream()
                    .map(PREFIX::concat)
                    .toList()
                    .equals(jdbc.queryForList(HELD, String.class))) throw invalid();
            AuthorizationEvidence result = read(identity, request, new Rows());
            coordinates(backendPid, transactionId);
            return result;
        } catch (RuntimeException failure) {
            // No SQL, driver values, claims or private causes cross this boundary.
            throw invalid();
        }
    }

    private void coordinates(int pid, long xid) {
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        "SELECT pg_backend_pid()=? AND txid_current()=? "
                                + "AND current_setting('transaction_isolation')='read committed' "
                                + "AND current_setting('transaction_read_only')='off'",
                        Boolean.class,
                        pid,
                        xid))) throw invalid();
    }

    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid) {
        validate(i,r,pid,xid,false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft) {
        validate(i, r, pid, xid, draft, false, false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft, boolean organization, boolean product) {
        validate(i,r,pid,xid,draft,organization,product,false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft,boolean organization,boolean product,boolean fluid) {
        validate(i,r,pid,xid,draft,organization,product,fluid,false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft,boolean organization,boolean product,boolean fluid,boolean publicationFamily) {
        validate(i,r,pid,xid,draft,organization,product,fluid,publicationFamily,false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft,boolean organization,boolean product,boolean fluid,boolean publicationFamily,boolean installedUnitFamily) {
        validate(i,r,pid,xid,draft,organization,product,fluid,publicationFamily,installedUnitFamily,false);
    }
    private void validate(Evidence i, AuthorizationEvaluationRequest r, int pid, long xid,boolean draft,boolean organization,boolean product,boolean fluid,boolean publicationFamily,boolean installedUnitFamily,boolean stationConfigurationFamily) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || i == null
                || r == null
                || pid <= 0
                || xid <= 0
                || !finite(i.observedAt())
                || !finite(i.expiresAt())
                || !i.observedAt().isBefore(i.expiresAt())
                || (i.sourceExpiresAt() != null
                        && (!finite(i.sourceExpiresAt())
                                || !i.observedAt().isBefore(i.sourceExpiresAt())))
                || !uuid(i.bindingId())
                || !uuid(i.sessionId())
                || !uuid(i.jti())
                || !text(i.userId(), 80)
                || !text(i.providerId(), 80)
                || !text(i.issuer(), 512)
                || i.issuer().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 512
                || i.providerGeneration() <= 0
                || !Set.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")
                        .contains(Objects.toString(i.authenticationType(), ""))) throw invalid();
        boolean local = "LOCAL".equals(i.authenticationType());
        if (local
                ? (!text(i.credentialId(), 80)
                        || i.credentialGeneration() == null
                        || i.credentialGeneration() <= 0
                        || i.externalIdentityId() != null
                        || i.externalGeneration() != null
                        || i.sourceExpiresAt() != null)
                : (i.credentialId() != null
                        || i.credentialGeneration() != null
                        || !text(i.externalIdentityId(), 80)
                        || i.externalGeneration() == null
                        || i.externalGeneration() <= 0
                        || ("OIDC".equals(i.authenticationType())
                                != (i.sourceExpiresAt() != null)))) throw invalid();
        if (stationConfigurationFamily) {
            if (draft || organization || product || fluid || publicationFamily || installedUnitFamily
                    || !i.userId().equals(r.userId()) || !text(r.resourceReferenceId(),80)
                    || r.scope()==null || r.scope().scopeCodeSnapshot()!=null) throw invalid();
            boolean configuration="STATION_INSTALLED_CONFIGURATION".equals(r.resourceType());
            boolean unit="INSTALLED_UNIT".equals(r.resourceType());
            if (!(configuration && Set.of("topology:installed-configuration:save","topology:installed-configuration:read",
                    "topology:installed-configuration:history").contains(r.permissionCode())
                    || unit && "topology:installed-unit:read".equals(r.permissionCode()))) throw invalid();
            String ref=r.scope().scopeReferenceId();
            if (r.scope().scopeType()==ScopeType.CUSTOM) {
                if (ref==null || !ref.startsWith("topology-company:") || !text(ref.substring(17),80)) throw invalid();
            } else if (!configuration || r.scope().scopeType()!=ScopeType.FACILITY
                    || !r.resourceReferenceId().equals(ref) || !text(ref,80)) throw invalid();
        } else if (installedUnitFamily) {
            if (draft || organization || product || fluid || publicationFamily || !i.userId().equals(r.userId())
                    || !Set.of("INSTALLED_UNIT","INSTALLED_UNIT_INSTALLATION").contains(r.resourceType())
                    || !text(r.resourceReferenceId(),80) || r.scope()==null || r.scope().scopeCodeSnapshot()!=null
                    || java.util.Arrays.stream(dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Operation.values())
                        .noneMatch(op -> (op==dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Operation.REPLACE
                            || ((op==dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Operation.REGISTER_UNIT
                                || op==dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Operation.READ_UNIT)
                                =="INSTALLED_UNIT".equals(r.resourceType())))
                            && dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.permissionCode(op,r.resourceType()).equals(r.permissionCode()))) throw invalid();
            String ref=r.scope().scopeReferenceId();
            if (r.scope().scopeType()==ScopeType.CUSTOM) {
                if (ref==null || !ref.startsWith("topology-company:") || !text(ref.substring(17),80)) throw invalid();
            } else if (r.scope().scopeType()!=ScopeType.FACILITY || !text(ref,80) || "INSTALLED_UNIT".equals(r.resourceType())) throw invalid();
        } else if (publicationFamily) {
            if(draft || organization || product || fluid || !i.userId().equals(r.userId()) || !"PRODUCT_ENGINEERING_PUBLICATION".equals(r.resourceType())
                || !text(r.resourceReferenceId(),80) || r.scope()==null || r.scope().scopeType()!=ScopeType.CUSTOM
                || r.scope().scopeCodeSnapshot()!=null
                || !Set.of("custody:engineering-product-publication:read","custody:engineering-product-publication:prepare","custody:engineering-product-publication:publish","custody:engineering-product-publication:withdraw","custody:engineering-product-publication:reactivate").contains(r.permissionCode()))throw invalid();
            String ref=r.scope().scopeReferenceId();
            if(ref==null || !(ref.equals("custody-product:"+r.resourceReferenceId())
                || (ref.startsWith("custody-company:") && text(ref.substring("custody-company:".length()),80))))throw invalid();
        } else if (fluid) {
            if(draft || organization || product || !i.userId().equals(r.userId()) || !"PRODUCT_ENGINEERING_PROPERTIES".equals(r.resourceType())
                || !text(r.resourceReferenceId(),80) || r.scope()==null || r.scope().scopeType()!=ScopeType.CUSTOM
                || r.scope().scopeCodeSnapshot()!=null
                || !Set.of("custody:engineering-product-properties:read","custody:engineering-product-properties:write").contains(r.permissionCode()))throw invalid();
            String ref=r.scope().scopeReferenceId();
            if(ref==null || !(ref.equals("custody-product:"+r.resourceReferenceId())
                || (ref.startsWith("custody-company:") && text(ref.substring("custody-company:".length()),80))))throw invalid();
        } else if (product) {
            if(draft || organization || !i.userId().equals(r.userId()) || !"PRODUCT_ENGINEERING_DRAFT".equals(r.resourceType())
                || !text(r.resourceReferenceId(),80) || r.scope()==null || r.scope().scopeType()!=ScopeType.CUSTOM
                || r.scope().scopeCodeSnapshot()!=null
                || !Set.of("custody:engineering-product:create","custody:engineering-product:read","custody:engineering-product:update").contains(r.permissionCode()))throw invalid();
            String ref=r.scope().scopeReferenceId();
            if(ref==null || !(ref.equals("custody-product:"+r.resourceReferenceId())
                || (ref.startsWith("custody-company:") && text(ref.substring(15),80))))throw invalid();
        } else if (organization) {
            if (draft || !i.userId().equals(r.userId()) || r.scope() == null
                    || !text(r.resourceReferenceId(), 80)
                    || !r.resourceReferenceId().equals(r.scope().scopeReferenceId())
                    || r.scope().scopeCodeSnapshot() != null) throw invalid();
            boolean exact = false;
            for (var action : dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.values()) {
                for (var kind : dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.values()) {
                    if (action == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.READ_POLICY
                            && kind == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.EMPLOYEE) continue;
                    var type = kind == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.UNIT
                            ? ScopeType.ORGANIZATION_UNIT : ScopeType.CUSTOM;
                    if (kind.resourceType().equals(r.resourceType()) && type == r.scope().scopeType()
                            && dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.permissionCode(action, kind).equals(r.permissionCode())) exact = true;
                }
            }
            if (!exact) throw invalid();
        } else if(draft){
            boolean publication=Set.of("topology:engineering-publication:publish","topology:engineering-publication:withdraw",
                "topology:engineering-publication:reactivate","topology:engineering-publication:read").contains(r.permissionCode());
            if(!i.userId().equals(r.userId()) || !(publication?"ENGINEERING_NETWORK_PUBLICATION":"ENGINEERING_NETWORK_DRAFT").equals(r.resourceType())
                || !text(r.resourceReferenceId(),80) || r.scope()==null || r.scope().scopeType()!=ScopeType.CUSTOM
                || !Set.of("topology:engineering-draft:create","topology:engineering-draft:read",
                    "topology:engineering-draft:update","topology:engineering-draft:validate",
                    "topology:engineering-publication:publish","topology:engineering-publication:withdraw",
                    "topology:engineering-publication:reactivate","topology:engineering-publication:read").contains(r.permissionCode()))throw invalid();
            String ref=r.scope().scopeReferenceId();
            if(ref==null || !(ref.equals("topology-network:"+r.resourceReferenceId())
                || (ref.startsWith("topology-company:") && text(ref.substring(17),80))))throw invalid();
        }else{
        if (!i.userId().equals(r.userId())
                || !"STATION_GRAPH_REVISION".equals(r.resourceType())
                || !text(r.resourceReferenceId(), 120)
                || r.scope() == null
                || r.permissionCode() == null
                || !r.permissionCode().startsWith("topology:station-revision:")
                || !CODES.contains(
                        r.permissionCode().substring("topology:station-revision:".length())))
            throw invalid();
        String ref = r.scope().scopeReferenceId();
        if (r.scope().scopeType() == ScopeType.FACILITY) {
            if (!r.resourceReferenceId().equals(ref)) throw invalid();
        } else if (r.scope().scopeType() != ScopeType.CUSTOM
                || ref == null
                || !ref.startsWith("topology-company:")
                || !text(ref.substring(17), 80)) throw invalid();
        }
    }

    private AuthorizationEvidence read(Evidence i, AuthorizationEvaluationRequest r, Rows q) {
        return read(i, r, q, null);
    }

    private AuthorizationEvidence read(
            Evidence i, AuthorizationEvaluationRequest r, Rows q, QualifiedClaims verified) {
        return read(i,r,q,verified,i.observedAt());
    }

    private AuthorizationEvidence read(Evidence i, AuthorizationEvaluationRequest r, Rows q,
            QualifiedClaims verified, Instant at) {
        Row user = one(q.get("user", "id,user_type,status,locked_until", "id=?", i.userId()));
        Row provider = one(q.get("provider", "id,status", "id=?", i.providerId()));
        if (!active(user, UserStatus.class)
                || (user.time("locked_until") != null && at.isBefore(user.time("locked_until"))))
            return failure("USER_INELIGIBLE");
        if (!active(provider, IdentityProviderStatus.class))
            return failure("EXTERNAL_IDENTITY_INELIGIBLE");
        List<Row> links =
                q.get(
                        "external_identity",
                        "id,user_id,identity_provider_id,status",
                        "user_id=?",
                        i.userId());
        if (i.externalIdentityId() != null) {
            Row link = find(links, i.externalIdentityId());
            if (!state(link, "status", ExternalIdentityStatus.class, "LINKED")
                    || !i.providerId().equals(link.s("identity_provider_id")))
                return failure("EXTERNAL_IDENTITY_INELIGIBLE");
        }
        List<Row> ps =
                q.get(
                        "permission",
                        "id,code,permission_domain,resource_type,action,status",
                        "code=?",
                        r.permissionCode());
        if (ps.size() != 1) return failure("PERMISSION_INELIGIBLE");
        Row p = ps.getFirst();
        if (!active(p, PermissionStatus.class) || !r.resourceType().equals(p.s("resource_type")))
            return failure("PERMISSION_INELIGIBLE");
        List<Row> direct =
                q.get(
                        "user_permission_grant",
                        "id,permission_id,effect,scope_type,scope_reference_id,status,valid_from,valid_to",
                        "user_id=? AND permission_id=?",
                        i.userId(),
                        p.s("id"));
        List<Row> ur =
                q.get(
                        "user_role_grant",
                        "id,role_id,scope_type,scope_reference_id,status,valid_from,valid_to",
                        "user_id=?",
                        i.userId());
        List<Row> members =
                q.get(
                        "user_group_membership",
                        "id,group_id,membership_type,source_provider_id,source_mapping_id,status,valid_from,valid_to",
                        "user_id=?",
                        i.userId());
        Set<String> mapIds = ids(members, "source_mapping_id");
        List<Row> gm =
                q.getIn(
                        "external_group_mapping",
                        "id,identity_provider_id,group_id,mapping_mode,status,claim_name,"
                          + "external_group_id,external_group_name,external_group_dn",
                        "id", mapIds, " OR identity_provider_id=?", i.providerId());
        List<Row> rm =
                q.get(
                        "external_role_mapping",
                        "id,identity_provider_id,role_id,external_role_code,claim_name,"
                          + "mapping_mode,status,scope_type,scope_reference_id",
                        "identity_provider_id=?", i.providerId());
        List<Row> pm =
                q.get(
                        "external_permission_mapping",
                        "id,identity_provider_id,permission_id,external_permission_code,"
                          + "claim_name,effect,mapping_mode,status",
                        "identity_provider_id=?", i.providerId());
        Set<String> groupIds = ids(members, "group_id");
        groupIds.addAll(ids(gm, "group_id"));
        List<Row> groups = q.in("group", "id,status", "id", groupIds);
        List<Row> gr =
                q.in(
                        "group_role_grant",
                        "id,group_id,role_id,scope_type,scope_reference_id,status,valid_from,valid_to",
                        "group_id", groupIds);
        Set<String> roleIds = ids(ur, "role_id");
        roleIds.addAll(ids(gr, "role_id"));
        roleIds.addAll(ids(rm, "role_id"));
        List<Row> roles = q.in("role", "id,status", "id", roleIds);
        List<Row> rp = roleIds.isEmpty() ? List.of() :
                q.getIn(
                        "role_permission_grant",
                        "id,role_id,permission_id,effect,condition_expression::text AS"
                            + " condition_expression,status,valid_from,valid_to",
                        "role_id", roleIds, " AND permission_id=?", p.s("id"));
        Set<String> subjects = new TreeSet<>(Set.of(i.userId()));
        subjects.addAll(ids(members, "group_id"));
        // Include referenced assertion targets before the qualified matching pass.
        // Attributes are applied only after a source-qualified group is eligible.
        subjects.addAll(ids(gm, "group_id"));
        if (i.externalIdentityId() != null) subjects.add(i.externalIdentityId());
        List<Row> attrs =
                q.in(
                        "subject_security_attribute",
                        "id,subject_type,subject_id,attribute_definition_id,attribute_value,source_provider_id,status,valid_from,valid_to",
                        "subject_id",
                        subjects);
        List<Row> defs =
                q.in(
                        "attribute_definition",
                        "id,code,attribute_target,data_type,multi_valued,status",
                        "id",
                        ids(attrs, "attribute_definition_id"));
        Set<String> providers = ids(members, "source_provider_id");
        providers.addAll(ids(attrs, "source_provider_id"));
        List<Row> sources = q.in("provider", "id,status", "id", providers);
        List<Row> policies =
                q.get(
                        "authorization_policy",
                        "id,status",
                        "policy_domain=?",
                        p.s("permission_domain"));
        List<Row> versions =
                q.in(
                        "authorization_policy_version",
                        "id,policy_id,status,effective_from,effective_to",
                        "policy_id",
                        ids(policies, "id"));
        List<Row> rules =
                q.in(
                        "authorization_policy_rule",
                        "id,policy_version_id,priority,effect,status,subject_expression::text AS"
                            + " subject_expression,resource_expression::text AS"
                            + " resource_expression,action_expression::text AS"
                            + " action_expression,context_expression::text AS"
                            + " context_expression,obligation_expression::text AS"
                            + " obligation_expression",
                        "policy_version_id",
                        ids(versions, "id"));
        // Missing referenced rows are storage corruption, not a silent absence of DENY.
        requireRefs(ur, "role_id", roles);
        requireRefs(members, "group_id", groups);
        requireRefs(gm, "group_id", groups);
        requireRefs(rm, "role_id", roles);
        requireRefs(gr, "role_id", roles);
        requireRefs(members, "source_mapping_id", gm);
        requireRefs(attrs, "attribute_definition_id", defs);
        requireRefs(members, "source_provider_id", sources);
        requireRefs(attrs, "source_provider_id", sources);
        List<AuthorizationEvidence.Grant> grants = new ArrayList<>();
        boolean unresolved = false;
        // The legacy diagnostic path is always unqualified: never interpret a caller
        // supplied claim, missing companion or ordinary assertion DTO as authority.
        for (Row m : gm)
            if (i.providerId().equals(m.s("identity_provider_id"))
                    && active(m, ExternalMappingStatus.class)
                    && mode(m, "ASSERTION_ONLY") && verified == null) unresolved = true;
        for (Row m : rm)
            if (active(m, ExternalMappingStatus.class) && mode(m, "DIRECT_GRANT")
                    && verified == null && covers(m, r)) unresolved = true;
        for (Row m : pm)
            if (active(m, ExternalMappingStatus.class) && mode(m, "DIRECT_GRANT")
                    && verified == null) unresolved = true;
        // Never silently drop an active unsupported mapping mode.
        for (Row m : gm)
            if (i.providerId().equals(m.s("identity_provider_id"))
                    && active(m, ExternalMappingStatus.class)
                    && !mode(m, "ASSERTION_ONLY") && !mode(m, "SYNC_MEMBERSHIP"))
                unresolved = true;
        for (Row m : rm)
            if (active(m, ExternalMappingStatus.class) && !mode(m, "DIRECT_GRANT"))
                unresolved = true;
        for (Row m : pm)
            if (active(m, ExternalMappingStatus.class)
                    && p.s("id").equals(m.s("permission_id"))
                    && !mode(m, "DIRECT_GRANT")) unresolved = true;
        for (Row g : direct)
            if (active(g, GrantStatus.class) && window(g, at) && covers(g, r))
                grants.add(grant(0, g, List.of(g.s("id")), List.of()));
        for (Row g : ur)
            if (active(g, GrantStatus.class) && window(g, at) && covers(g, r))
                addRole(grants, g.s("role_id"), roles, rp, at, 1, List.of(g.s("id")), List.of());
        Set<String> eligibleGroups = new TreeSet<>();
        for (Row m : members) {
            if (!active(m, MembershipStatus.class)
                    || !window(m, at)
                    || !sourceEligible(m, gm, sources, links)) continue;
            if (!active(find(groups, m.s("group_id")), GroupStatus.class)) continue;
            eligibleGroups.add(m.s("group_id"));
            List<String> chain =
                    m.s("source_mapping_id") == null
                            ? List.of(m.s("id"))
                            : List.of(m.s("id"), m.s("source_mapping_id"));
            List<Map<String, String>> proof =
                    m.s("source_mapping_id") == null
                            ? List.of()
                            : List.of(
                                    Map.of(
                                            "providerId",
                                            m.s("source_provider_id"),
                                            "mappingId",
                                            m.s("source_mapping_id"),
                                            "source",
                                            "SYNC_MEMBERSHIP"));
            for (Row g : gr)
                if (m.s("group_id").equals(g.s("group_id"))
                        && active(g, GrantStatus.class)
                        && window(g, at)
                        && covers(g, r))
                    addRole(
                            grants,
                            g.s("role_id"),
                            roles,
                            rp,
                            at,
                            2,
                            append(chain, g.s("id")),
                            proof);
        }
        // Positive source observations only. Never coerce LDAP/AD incomplete
        // membership to absence or allow unknown potential DENY to be ignored.
        if (verified != null) {
            for (Row m : gm) {
                if (!i.providerId().equals(m.s("identity_provider_id"))
                        || !active(m, ExternalMappingStatus.class)
                        || !mode(m, "ASSERTION_ONLY")) continue;
                List<String> candidates =
                        Arrays.stream(new String[] {
                            m.s("external_group_id"), m.s("external_group_name"),
                            m.s("external_group_dn") })
                        .filter(Objects::nonNull).distinct().toList();
                var result = match.match(verified.coverage(), verified.type(),
                        m.s("claim_name"), candidates);
                if (result == ExternalClaimMatchEvaluator.Outcome.INDETERMINATE) {
                    unresolved = true;
                    continue;
                }
                if (result != ExternalClaimMatchEvaluator.Outcome.MATCH) continue;
                String groupId = m.s("group_id");
                if (!active(find(groups, groupId), GroupStatus.class)) continue;
                eligibleGroups.add(groupId);
                for (Row g : gr) {
                    if (!groupId.equals(g.s("group_id"))
                            || !active(g, GrantStatus.class) || !window(g, at)
                            || !covers(g, r)) continue;
                    addRole(grants, g.s("role_id"), roles, rp, at, 3,
                            List.of(m.s("id"), g.s("id")), List.of(
                                    Map.of("providerId", i.providerId(),
                                            "mappingId", m.s("id"), "source", "BOUND_CLAIM")));
                }
            }
            for (Row m : rm) {
                if (!active(m, ExternalMappingStatus.class)
                        || !mode(m, "DIRECT_GRANT") || !covers(m, r)) continue;
                var result = match.match(verified.coverage(), verified.type(),
                        m.s("claim_name"), List.of(m.s("external_role_code")));
                if (result == ExternalClaimMatchEvaluator.Outcome.INDETERMINATE)
                    unresolved = true;
                else if (result == ExternalClaimMatchEvaluator.Outcome.MATCH)
                    addRole(grants, m.s("role_id"), roles, rp, at, 4,
                            List.of(m.s("id")), List.of(Map.of(
                                    "providerId", i.providerId(),
                                    "mappingId", m.s("id"), "source", "BOUND_CLAIM")));
            }
            for (Row m : pm) {
                if (!active(m, ExternalMappingStatus.class)
                        || !mode(m, "DIRECT_GRANT")
                        || !p.s("id").equals(m.s("permission_id"))) continue;
                var result = match.match(verified.coverage(), verified.type(),
                        m.s("claim_name"), List.of(m.s("external_permission_code")));
                if (result == ExternalClaimMatchEvaluator.Outcome.INDETERMINATE)
                    unresolved = true;
                else if (result == ExternalClaimMatchEvaluator.Outcome.MATCH)
                    grants.add(new AuthorizationEvidence.Grant(
                            5, GrantEffect.valueOf(m.s("effect")), List.of(m.s("id")),
                            null, List.of(Map.of("providerId", i.providerId(),
                                    "mappingId", m.s("id"), "source", "BOUND_CLAIM"))));
            }
        }
        Map<String, Object> facts = new TreeMap<>();
        facts.put("subject.id", i.userId());
        facts.put("subject.type", UserType.valueOf(user.s("user_type")).name());
        facts.put("subject.status", "ACTIVE");
        facts.put("subject.groupIds", List.copyOf(eligibleGroups));
        facts.put("action.permissionCode", p.s("code"));
        facts.put("action.name", p.s("action"));
        facts.put("resource.type", r.resourceType());
        facts.put("resource.referenceId", r.resourceReferenceId());
        facts.put("context.evaluatedAt", at.toString());
        facts.put("context.scopeType", r.scope().scopeType().name());
        facts.put("context.scopeReferenceId", r.scope().scopeReferenceId());
        for (Row a : attrs) {
            String type = a.s("subject_type");
            SubjectAttributeOwnerType.valueOf(type);
            if (!(type.equals("USER") && i.userId().equals(a.s("subject_id")))
                    && !(type.equals("GROUP") && eligibleGroups.contains(a.s("subject_id")))
                    && !(type.equals("EXTERNAL_IDENTITY")
                            && Objects.equals(i.externalIdentityId(), a.s("subject_id")))) continue;
            if (!active(a, PermissionStatus.class) || !attributeWindow(a, at)) continue;
            Row d = find(defs, a.s("attribute_definition_id"));
            if (!active(d, PermissionStatus.class) || !type.equals(d.s("attribute_target")))
                continue;
            try {
                if (a.s("source_provider_id") != null
                        && !active(
                                find(sources, a.s("source_provider_id")),
                                IdentityProviderStatus.class)) throw invalid();
                Object value = immutable(typed(d, a.s("attribute_value")));
                if (value == null) throw invalid();
                String key = "subject.attributes." + d.s("code");
                Object old = facts.putIfAbsent(key, value);
                if (old != null && !old.equals(value)) {
                    facts.remove(key);
                    unresolved = true;
                }
            } catch (RuntimeException malformed) {
                unresolved = true;
            }
        }
        List<AuthorizationEvidence.Rule> normalized = new ArrayList<>();
        for (Row policy : policies) {
            if (!active(policy, PolicyStatus.class)) continue;
            List<Row> selected =
                    versions.stream()
                            .filter(
                                    v ->
                                            policy.s("id").equals(v.s("policy_id"))
                                                    && active(v, PolicyVersionStatus.class)
                                                    && timeWindow(
                                                            v,
                                                            "effective_from",
                                                            "effective_to",
                                                            at,
                                                            false))
                            .toList();
            if (selected.size() != 1) {
                unresolved = true;
                continue;
            }
            for (Row rule : rules)
                if (selected.getFirst().s("id").equals(rule.s("policy_version_id"))
                        && active(rule, PolicyRuleStatus.class)) {
                    List<String> expressions =
                            Arrays.asList(
                                    rule.s("subject_expression"),
                                    rule.s("resource_expression"),
                                    rule.s("action_expression"),
                                    rule.s("context_expression"));
                    boolean supported = expressions.stream().allMatch(this::supported);
                    if (!supported || rule.s("obligation_expression") != null) unresolved = true;
                    normalized.add(
                            new AuthorizationEvidence.Rule(
                                    rule.s("id"),
                                    ((Number) rule.values().get("priority")).intValue(),
                                    PolicyRuleEffect.valueOf(rule.s("effect")),
                                    expressions,
                                    rule.s("obligation_expression")));
                }
        }
        for (var g : grants) if (!supported(g.condition())) unresolved = true;
        return new AuthorizationEvidence(
                unresolved ? "EVIDENCE_INDETERMINATE" : null, grants, normalized, facts);
    }

    private boolean supported(String expression) {
        return syntax.compile(expression).classification()
                == AuthorizationExpressionStabilityCompiler.Classification.SUPPORTED_STATIC_SYNTAX;
    }

    private boolean sourceEligible(
            Row m, List<Row> mappings, List<Row> providers, List<Row> links) {
        MembershipType type = MembershipType.valueOf(m.s("membership_type"));
        if (type == MembershipType.EXTERNAL_ASSERTION) return false;
        if (m.s("source_provider_id") == null)
            return m.s("source_mapping_id") == null && type != MembershipType.EXTERNAL_SYNC;
        Row source = find(providers, m.s("source_provider_id")),
                mapping = find(mappings, m.s("source_mapping_id"));
        return active(source, IdentityProviderStatus.class)
                && active(mapping, ExternalMappingStatus.class)
                && mode(mapping, "SYNC_MEMBERSHIP")
                && source.s("id").equals(mapping.s("identity_provider_id"))
                && m.s("group_id").equals(mapping.s("group_id"))
                && links.stream()
                        .anyMatch(
                                e ->
                                        source.s("id").equals(e.s("identity_provider_id"))
                                                && state(
                                                        e,
                                                        "status",
                                                        ExternalIdentityStatus.class,
                                                        "LINKED"));
    }

    private void addRole(
            List<AuthorizationEvidence.Grant> out,
            String id,
            List<Row> roles,
            List<Row> rp,
            Instant at,
            int order,
            List<String> chain,
            List<Map<String, String>> proof) {
        if (!active(find(roles, id), RoleStatus.class)) return;
        for (Row g : rp)
            if (id.equals(g.s("role_id")) && active(g, GrantStatus.class) && window(g, at))
                out.add(grant(order, g, append(chain, g.s("id")), proof));
    }

    private AuthorizationEvidence.Grant grant(
            int order, Row g, List<String> chain, List<Map<String, String>> proof) {
        return new AuthorizationEvidence.Grant(
                order,
                GrantEffect.valueOf(g.s("effect")),
                chain,
                g.s("condition_expression"),
                proof);
    }

    private static boolean covers(Row g, AuthorizationEvaluationRequest r) {
        String type = g.s("scope_type"), ref = g.s("scope_reference_id");
        if (type == null || ScopeType.valueOf(type) == ScopeType.GLOBAL) return ref == null;
        return ref != null
                && r.scope().scopeType().name().equals(type)
                && ref.equals(r.scope().scopeReferenceId());
    }

    private static boolean mode(Row r, String value) {
        return ExternalMappingMode.valueOf(r.s("mapping_mode")).name().equals(value);
    }

    private static <E extends Enum<E>> boolean active(Row r, Class<E> type) {
        return state(r, "status", type, "ACTIVE");
    }

    private static <E extends Enum<E>> boolean state(
            Row r, String key, Class<E> type, String value) {
        return r != null && Enum.valueOf(type, r.s(key)).name().equals(value);
    }

    private static boolean window(Row r, Instant at) {
        return timeWindow(r, "valid_from", "valid_to", at, false);
    }

    private static boolean attributeWindow(Row r, Instant at) {
        return timeWindow(r, "valid_from", "valid_to", at, true);
    }

    private static boolean timeWindow(Row r, String from, String to, Instant at, boolean nullable) {
        Instant a = r.time(from), b = r.time(to);
        if ((a == null && !nullable)
                || (a != null && !finite(a))
                || (b != null && (!finite(b) || (a != null && a.isAfter(b))))) throw invalid();
        return (a == null || !at.isBefore(a)) && (b == null || at.isBefore(b));
    }

    private static Object typed(Row d, String value) {
        if (value == null || value.length() > 8192) throw invalid();
        AttributeDataType type = AttributeDataType.valueOf(d.s("data_type"));
        if (Boolean.TRUE.equals(d.values().get("multi_valued")) || type == AttributeDataType.LIST) {
            Object parsed = AuthorizationJson.parse(value);
            if (!(parsed instanceof List<?> list)
                    || list.stream()
                            .anyMatch(
                                    v ->
                                            !(v instanceof String
                                                    || v instanceof Boolean
                                                    || v instanceof BigDecimal))) throw invalid();
            return list;
        }
        return switch (type) {
            case STRING -> value;
            case NUMBER -> {
                BigDecimal n = new BigDecimal(value).stripTrailingZeros();
                if (Math.abs((long) n.scale()) > 1000) throw invalid();
                yield n;
            }
            case BOOLEAN -> {
                if (!value.equals("true") && !value.equals("false")) throw invalid();
                yield Boolean.valueOf(value);
            }
            case DATE -> LocalDate.parse(value).toString();
            case DATETIME -> Instant.parse(value).toString();
            case JSON -> AuthorizationJson.parse(value);
            case LIST -> throw invalid();
        };
    }

    private static Object immutable(Object value) {
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> copy = new TreeMap<>();
            map.forEach((k, v) -> copy.put((String) k, immutable(v)));
            return Collections.unmodifiableMap(copy);
        }
        if (value instanceof List<?> list)
            return Collections.unmodifiableList(
                    list.stream()
                            .map(JdbcProtectedAuthorizationEvidenceReader::immutable)
                            .toList());
        return value;
    }

    private static AuthorizationEvidence failure(String value) {
        return new AuthorizationEvidence(value, List.of(), List.of(), Map.of());
    }

    private static Row one(List<Row> rows) {
        if (rows.size() != 1) throw invalid();
        return rows.getFirst();
    }

    private static Row find(List<Row> rows, String id) {
        return rows.stream().filter(r -> Objects.equals(id, r.s("id"))).findFirst().orElse(null);
    }

    private static void requireRefs(List<Row> from, String key, List<Row> targets) {
        for (Row r : from) if (r.s(key) != null && find(targets, r.s(key)) == null) throw invalid();
    }

    private static Set<String> ids(List<Row> rows, String key) {
        Set<String> ids = new TreeSet<>();
        for (Row r : rows) if (r.s(key) != null) ids.add(r.s(key));
        return ids;
    }

    private static List<String> append(List<String> values, String id) {
        List<String> copy = new ArrayList<>(values);
        copy.add(id);
        return List.copyOf(copy);
    }

    private static boolean finite(Instant at) {
        return at != null
                && at.getEpochSecond() >= -210866803200L
                && at.getEpochSecond() < 9224318016000L;
    }

    private static boolean uuid(String value) {
        try {
            return value != null && UUID.fromString(value).toString().equals(value);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean text(String value, int max) {
        if (value == null
                || value.isBlank()
                || !value.equals(value.strip())
                || !value.equals(value.trim())
                || value.codePointCount(0, value.length()) > max) return false;
        for (int n = 0; n < value.length(); n++) {
            char c = value.charAt(n);
            if (c == 0) return false;
            if (Character.isHighSurrogate(c)) {
                if (++n >= value.length() || !Character.isLowSurrogate(value.charAt(n)))
                    return false;
            } else if (Character.isLowSurrogate(c)) return false;
        }
        return true;
    }

    private static IllegalStateException invalid() {
        return new IllegalStateException(
                "Protected authorization evidence is unavailable or corrupt.");
    }

    private record Row(Map<String, Object> values) {
        String s(String key) {
            Object v = values.get(key);
            return v == null ? null : v.toString();
        }

        Instant time(String key) {
            Object value = values.get(key);
            return value == null ? null : ((Timestamp) value).toInstant();
        }
    }

    private record RowQuery(String sql, List<Object> parameters) {}

    /**
     * One combined owner-row budget per graph, including inactive and future candidates.
     * Group-local reuse never survives a reader invocation or bypasses this budget.
     */
    private final class Rows {
        private int count;
        private final Map<RowQuery, List<Row>> shared;

        Rows() { this(false); }
        Rows(boolean share) { shared = share ? new HashMap<>() : null; }

        // Cache hits still count toward each graph's original 1024-row limit.
        void beginGraph() { count = 0; }

        List<Row> get(String table, String columns, String where, Object... args) {
            String sql = "SELECT " + columns + " FROM " + PREFIX + table + " WHERE "
                    + where + " ORDER BY id LIMIT 1025";
            var key = new RowQuery(sql,
                    Collections.unmodifiableList(new ArrayList<>(Arrays.asList(args))));
            List<Row> result = shared == null ? null : shared.get(key);
            if (result == null) {
                result = jdbc.query(sql,
                            (rs, n) -> {
                                Map<String, Object> fields = new HashMap<>();
                                var meta = rs.getMetaData();
                                for (int c = 1; c <= meta.getColumnCount(); c++)
                                    fields.put(meta.getColumnLabel(c), rs.getObject(c));
                                return new Row(fields);
                            },
                            args);
                if (shared != null) shared.put(key, List.copyOf(result));
            }
            count += result.size();
            if (count > 1024) throw invalid();
            return result;
        }

        List<Row> in(String table, String columns, String key, Set<String> values) {
            // No referenced IDs means this conjunctive query can return no rows.
            // Do not apply this shortcut to getIn: its OR suffix may still match.
            if (values.isEmpty()) return List.of();
            return getIn(table, columns, key, values, "");
        }

        List<Row> getIn(
                String table,
                String columns,
                String key,
                Set<String> values,
                String suffix,
                Object... extra) {
            List<Object> args = new ArrayList<>(values);
            args.addAll(Arrays.asList(extra));
            String where =
                    values.isEmpty()
                            ? "false"
                            : key
                                    + " IN ("
                                    + String.join(",", Collections.nCopies(values.size(), "?"))
                                    + ")";
            return get(table, columns, "(" + where + ")" + suffix, args.toArray());
        }
    }
}

