/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcIdentityTemporalEventCollector
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Collects bounded fenced Identity temporal candidates without granting authority.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.domain.service.AuthorizationExpressionStabilityCompiler;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/** Identity-private diagnostics; a native storage tuple is not verified producer origin or a permit. */
@Repository
public class JdbcIdentityTemporalEventCollector {
    public record Candidate(Instant sampledAt, Instant earliestFuture, int backendPid, long transactionId,
                            List<Contributor> contributors, List<String> referencedAttributes,
                            int temporalInputs, int expressionRows) {
        public Candidate {
            Objects.requireNonNull(sampledAt);
            Objects.requireNonNull(earliestFuture);
            if (!sampledAt.isBefore(earliestFuture) || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid temporal candidate coordinates");
            contributors = List.copyOf(contributors);
            referencedAttributes = List.copyOf(referencedAttributes);
        }
    }
    private record Sample(Instant at, int pid, long xid) {}
    private record Binding(Instant issued, Instant expires, Instant sourceExpires) {}
    private record Projection(String table, String key, String kind, String from, String to, String suffix) {}
    private static final String PREFIX = "public.hidra_identity_";
    private static final String LOCKS =
            "LOCK TABLE public.hidra_identity_authorization_policy_rule,"
            + "public.hidra_identity_authorization_policy_version,public.hidra_identity_group_role_grant,"
            + "public.hidra_identity_login_session,public.hidra_identity_operation_binding,"
            + "public.hidra_identity_role_permission_grant,public.hidra_identity_subject_security_attribute,"
            + "public.hidra_identity_user,public.hidra_identity_user_group_membership,"
            + "public.hidra_identity_user_permission_grant,public.hidra_identity_user_role_grant"
            + " IN SHARE ROW EXCLUSIVE MODE";
    private static final List<Projection> PROJECTIONS = List.of(
            new Projection("authorization_policy_version","id","POLICY_VERSION","effective_from","effective_to",""),
            new Projection("group_role_grant","id","GRANT_OR_MEMBERSHIP","valid_from","valid_to",""),
            new Projection("login_session","id","ATTRIBUTE","started_at","expires_at",""),
            new Projection("operation_binding","binding_id","DIRECT_GRANT","issued_at","expires_at",""),
            new Projection("operation_binding","binding_id","ATTRIBUTE","NULL::timestamptz","source_expires_at",":source"),
            new Projection("role_permission_grant","id","GRANT_OR_MEMBERSHIP","valid_from","valid_to",""),
            new Projection("subject_security_attribute","id","ATTRIBUTE","valid_from","valid_to",""),
            new Projection("user","id","LOCKED_UNTIL","NULL::timestamptz","locked_until",""),
            new Projection("user_group_membership","id","GRANT_OR_MEMBERSHIP","valid_from","valid_to",""),
            new Projection("user_permission_grant","id","DIRECT_GRANT","valid_from","valid_to",""),
            new Projection("user_role_grant","id","GRANT_OR_MEMBERSHIP","valid_from","valid_to",""));
    private static final String TEMPORAL_SQL = temporalSql();
    private static final String EXPRESSION_SQL = """
        SELECT source_id, subject_expression, resource_expression, action_expression,
               context_expression, obligation_expression FROM (
          SELECT 'authorization_policy_rule:' || id AS source_id,
                 subject_expression::text, resource_expression::text, action_expression::text,
                 context_expression::text, obligation_expression::text
            FROM public.hidra_identity_authorization_policy_rule
          UNION ALL
          SELECT 'role_permission_grant:' || id, condition_expression::text,
                 NULL::text,NULL::text,NULL::text,NULL::text
            FROM public.hidra_identity_role_permission_grant
        ) expressions ORDER BY source_id LIMIT 1025
        """;
    private final JdbcTemplate jdbc;
    private final AuthorizationExpressionStabilityCompiler syntax = new AuthorizationExpressionStabilityCompiler();
    private final AuthorizationTemporalBoundaryCompiler boundaries = new AuthorizationTemporalBoundaryCompiler();
    public JdbcIdentityTemporalEventCollector(JdbcTemplate jdbc) { this.jdbc = Objects.requireNonNull(jdbc); }

    @Transactional(propagation = Propagation.MANDATORY)
    public Candidate collect(String bindingId) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw invalid("Writable participating transaction required");
        if (bindingId == null || !bindingId.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
            throw invalid("Exact binding identity required");
        Boolean compatible = jdbc.queryForObject(
                "SELECT current_setting('transaction_isolation')='read committed'"
                + " AND current_setting('transaction_read_only')='off'", Boolean.class);
        if (!Boolean.TRUE.equals(compatible)) throw invalid("Incompatible physical transaction");
        // No owner data or clock sample precedes the fixed temporal/expression fences.
        jdbc.execute(LOCKS);
        Sample sample = jdbc.queryForObject("SELECT clock_timestamp(),pg_backend_pid(),txid_current()",
                (r,n) -> new Sample(r.getTimestamp(1).toInstant(),r.getInt(2),r.getLong(3)));
        List<Binding> selected = jdbc.query(
                "SELECT issued_at,expires_at,source_expires_at FROM public.hidra_identity_operation_binding WHERE binding_id=?",
                (r,n) -> new Binding(instant(r.getTimestamp(1)),instant(r.getTimestamp(2)),instant(r.getTimestamp(3))),
                bindingId);
        if (sample == null || selected.size()!=1) throw invalid("Missing temporal binding");
        Binding binding=selected.getFirst();
        if (binding.issued()==null || binding.issued().isAfter(sample.at())) throw invalid("Future or absent binding issue time");
        List<BoundaryInput> inputs = new ArrayList<>(jdbc.query(TEMPORAL_SQL,
                (r,n) -> new BoundaryInput(r.getString(1),Kind.valueOf(r.getString(2)),
                        instant(r.getTimestamp(3)),instant(r.getTimestamp(4)))));
        inputs.add(new BoundaryInput("selected:"+bindingId+":expiry",Kind.B1_EXPIRY,null,binding.expires()));
        if (binding.sourceExpires()!=null)
            inputs.add(new BoundaryInput("selected:"+bindingId+":source",Kind.B1_EXPIRY,null,binding.sourceExpires()));
        if(inputs.size()>1024) throw invalid("Temporal census exceeds reviewed bound");
        List<List<String>> expressions = jdbc.query(EXPRESSION_SQL, (r,n) -> {
            if(r.getString(6)!=null) throw invalid("Unsupported obligation");
            return Arrays.asList(r.getString(2),r.getString(3),r.getString(4),r.getString(5));
        });
        if(expressions.size()>1024) throw invalid("Expression census exceeds reviewed bound");
        Set<String> dependencies=new TreeSet<>();
        for(List<String> row:expressions) for(String expression:row) {
            var compiled=syntax.compile(expression);
            if(compiled.classification()!=AuthorizationExpressionStabilityCompiler.Classification.SUPPORTED_STATIC_SYNTAX)
                throw invalid("Unsupported protected expression");
            dependencies.addAll(compiled.referencedAttributes());
        }
        var compiled=boundaries.compile(sample.at(),inputs);
        if(compiled.disposition()!=Disposition.BOUNDED_CANDIDATE) throw invalid("Unsupported temporal census");
        // The selected B1 deadline forces a finite candidate; this still establishes no eligibility.
        return new Candidate(sample.at(),compiled.earliestFuture().orElseThrow(),sample.pid(),sample.xid(),
                compiled.contributors(),List.copyOf(dependencies),inputs.size(),expressions.size());
    }
    private static Instant instant(Timestamp value) { return value==null?null:value.toInstant(); }
    private static IllegalStateException invalid(String reason) { return new IllegalStateException(reason); }
    private static String temporalSql() {
        List<String> branches=new ArrayList<>();
        for(Projection p:PROJECTIONS) branches.add(
                "SELECT '"+p.table()+":' || "+p.key()+" || '"+p.suffix()+"' AS source_id,"
                + "'"+p.kind()+"'::text AS kind,"+p.from()+" AS from_at,"+p.to()+" AS to_at FROM "+PREFIX+p.table());
        return "SELECT source_id,kind,from_at,to_at FROM ("+String.join(" UNION ALL ",branches)
                + ") temporal ORDER BY source_id,kind LIMIT 1025";
    }
}
