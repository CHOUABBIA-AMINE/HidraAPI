/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedAuthorizationEvidenceReader
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-15
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
        try {
            if (participant == null || bindings == null || companions == null
                    || registry == null || catalog == null) throw invalid();
            var contribution = participant.consumeClaims(credential, request);
            Evidence evidence = contribution.evidence();
            int pid = contribution.backendPid();
            long xid = contribution.transactionId();
            validate(evidence, request, pid, xid);
            coordinates(pid, xid);
            heldClaims();
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
            if (!"LOCAL".equals(original.authenticationType())) {
                var selected = catalog.selectVersioned(original.providerId());
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
            AuthorizationEvidence result = read(evidence, request, new Rows(), qualified);
            coordinates(pid, xid);
            heldClaims();
            return result;
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    private void heldClaims() {
        if (!RELATIONS.stream().map(PREFIX::concat).toList()
                    .equals(jdbc.queryForList(HELD, String.class))) throw invalid();
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        """
                        SELECT EXISTS (SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid()
                            AND relation='public.hidra_identity_external_mapping_generation'::regclass
                            AND granted AND locktype='relation' AND mode='AccessShareLock')
                         AND EXISTS (SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid()
                            AND relation='public.hidra_identity_bound_source_claim_evidence'::regclass
                            AND granted AND locktype='relation' AND mode='ShareLock')
                        """, Boolean.class))) throw invalid();
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

    private AuthorizationEvidence read(Evidence i, AuthorizationEvaluationRequest r, Rows q) {
        return read(i, r, q, null);
    }

    private AuthorizationEvidence read(
            Evidence i, AuthorizationEvaluationRequest r, Rows q, QualifiedClaims verified) {
        Instant at = i.observedAt();
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
        List<Row> rp =
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

    /**
     * One combined owner-row budget, including inactive and future candidates. No per-row query.
     */
    private final class Rows {
        private int count;

        List<Row> get(String table, String columns, String where, Object... args) {
            List<Row> result =
                    jdbc.query(
                            "SELECT "
                                    + columns
                                    + " FROM "
                                    + PREFIX
                                    + table
                                    + " WHERE "
                                    + where
                                    + " ORDER BY id LIMIT 1025",
                            (rs, n) -> {
                                Map<String, Object> fields = new HashMap<>();
                                var meta = rs.getMetaData();
                                for (int c = 1; c <= meta.getColumnCount(); c++)
                                    fields.put(meta.getColumnLabel(c), rs.getObject(c));
                                return new Row(fields);
                            },
                            args);
            count += result.size();
            if (count > 1024) throw invalid();
            return result;
        }

        List<Row> in(String table, String columns, String key, Set<String> values) {
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
