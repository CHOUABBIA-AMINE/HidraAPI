/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureRestrictionResolverTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Exercises exact-scope fail-closed immutable pressure restriction timelines.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.*;
import dz.sh.hidra.modules.topology.domain.service.QualifiedPressureRestrictionResolver.Status;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QualifiedPressureRestrictionResolverTest {
    private static final Instant T0 = Instant.parse("2026-01-01T00:00:00Z");
    private static final Instant T1 = Instant.parse("2026-01-02T00:00:00Z");
    private static final Instant T2 = Instant.parse("2026-01-03T00:00:00Z");
    private final QualifiedPressureRestrictionResolver resolver = new QualifiedPressureRestrictionResolver();
    private static Scope scope() { return new Scope("company-a","pipeline-a",null,"NOMINAL"); }
    private static Evidence evidence(String key) {
        return new Evidence(UUID.nameUUIDFromBytes(key.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                "a".repeat(64), "network-source-v1", "decision-"+key, "audit-"+key,
                "author", "independent-reviewer", T0);
    }
    private static Revision revision(String key, Scope scope, Instant from, Instant until, Instant recorded,
                                     UUID supersedes, String pressure, PressureBasis basis) {
        return new Revision(UUID.nameUUIDFromBytes(key.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                scope, new BigDecimal(pressure), "bar", basis, from, until, recorded, evidence(key), supersedes);
    }
    private static Termination terminate(String key, Revision prior, EventKind kind,
                                          Revision successor, Instant effective, Instant recorded) {
        return new Termination(UUID.nameUUIDFromBytes(key.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                prior.scope(),prior.id(),kind,successor==null?null:successor.id(),
                effective,recorded,evidence(key));
    }
    private QualifiedPressureRestrictionResolver.Resolution resolve(Scope scope, Instant effective, Instant known,
                                                List<Revision> revisions, List<Termination> terminations) {
        return resolver.resolve(scope,effective,known,revisions,terminations);
    }

    @Test void emptyEvidenceNeverProducesApprovedValue() {
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T1,T2,List.of(),List.of()).status());
    }
    @Test void halfOpenIntervalIncludesStartAndExcludesEnd() {
        Revision r=revision("first",scope(),T1,T2,T1,null,"68.000",PressureBasis.GAUGE);
        assertEquals(Status.QUALIFIED,resolve(scope(),T1,T2,List.of(r),List.of()).status());
        assertNull(resolve(scope(),T2,T2,List.of(r),List.of()).restriction());
    }
    @Test void effectiveTimeCannotSeeRevisionNotYetRecorded() {
        Revision r=revision("first",scope(),T0,null,T2,null,"68",PressureBasis.GAUGE);
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T1,T1,List.of(r),List.of()).status());
        assertEquals(Status.QUALIFIED,resolve(scope(),T1,T2,List.of(r),List.of()).status());
    }
    @Test void scopeSectionAndConditionNeverInheritOrLeak() {
        Revision r=revision("first",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        assertEquals(Status.UNAVAILABLE,resolve(new Scope("company-a","pipeline-a","section-1","NOMINAL"),
                T1,T2,List.of(r),List.of()).status());
        assertEquals(Status.UNAVAILABLE,resolve(new Scope("company-b","pipeline-a",null,"NOMINAL"),
                T1,T2,List.of(r),List.of()).status());
        assertEquals(Status.UNAVAILABLE,resolve(new Scope("company-a","pipeline-a",null,"MAINTENANCE"),
                T1,T2,List.of(r),List.of()).status());
    }
    @Test void independentOverlappingApprovalsConflictWithoutExposingCandidates() {
        Revision r1=revision("one",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Revision r2=revision("two",scope(),T0,null,T0,null,"69",PressureBasis.ABSOLUTE);
        var result=resolve(scope(),T1,T2,List.of(r1,r2),List.of());
        assertEquals(Status.CONFLICT,result.status());
        assertNull(result.restriction());
    }
    @Test void successorTakesEffectWithoutErasingPriorHistoricalValue() {
        Revision old=revision("old",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Revision next=revision("new",scope(),T2,null,T2,old.id(),"64",PressureBasis.GAUGE);
        Termination t=terminate("termination",old,EventKind.SUPERSEDE,next,T2,T2);
        assertEquals(old.id(),resolve(scope(),T1,T2,List.of(old,next),List.of(t)).restriction().id());
        assertEquals(next.id(),resolve(scope(),T2,T2,List.of(old,next),List.of(t)).restriction().id());
    }
    @Test void withdrawalNeverResurrectsFormerlyQualifiedRevision() {
        Revision old=revision("old",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Termination t=terminate("withdrawal",old,EventKind.WITHDRAW,null,T2,T2);
        assertEquals(Status.QUALIFIED,resolve(scope(),T1,T2,List.of(old),List.of(t)).status());
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T2,T2,List.of(old),List.of(t)).status());
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T2.plusSeconds(100),T2,List.of(old),List.of(t)).status());
    }
    @Test void unrecordedTerminationCannotRewriteAnEarlierObservation() {
        Revision old=revision("old",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Termination t=terminate("withdrawal",old,EventKind.WITHDRAW,null,T1,T2);
        assertEquals(Status.QUALIFIED,resolve(scope(),T1,T1,List.of(old),List.of(t)).status());
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T1,T2,List.of(old),List.of(t)).status());
    }
    @Test void orphanSupersessionAndOrphanTerminationFailClosed() {
        Revision old=revision("old",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Revision next=revision("next",scope(),T1,null,T1,old.id(),"64",PressureBasis.GAUGE);
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T2,T2,List.of(old,next),List.of()).status());
        Termination t=terminate("withdraw",old,EventKind.WITHDRAW,null,T1,T1);
        assertEquals(Status.UNAVAILABLE,resolve(scope(),T2,T2,List.of(),List.of(t)).status());
    }
    @Test void duplicateTerminationAndDuplicateRevisionAreAmbiguous() {
        Revision old=revision("old",scope(),T0,null,T0,null,"68",PressureBasis.GAUGE);
        Termination t=terminate("withdraw",old,EventKind.WITHDRAW,null,T1,T1);
        assertEquals(Status.CONFLICT,resolve(scope(),T2,T2,List.of(old,old),List.of()).status());
        assertEquals(Status.CONFLICT,resolve(scope(),T2,T2,List.of(old),List.of(t,t)).status());
    }
    @Test void pressureUnitBasisAndIndependentDecisionEvidenceAreMandatory() {
        assertThrows(IllegalArgumentException.class,()->revision("negative",scope(),T0,null,T0,null,
                "-1",PressureBasis.GAUGE));
        assertThrows(IllegalArgumentException.class,()->revision("zero",scope(),T0,null,T0,null,
                "0",PressureBasis.ABSOLUTE));
        assertThrows(IllegalArgumentException.class,()->new Evidence(UUID.randomUUID(),"a".repeat(64),
                "network","action","audit","same","same",T0));
        assertThrows(IllegalArgumentException.class,()->new Evidence(UUID.randomUUID(),"invalid",
                "network","action","audit","author","reviewer",T0));
        Revision abs=revision("abs",scope(),T0,null,T0,null,"68.000",PressureBasis.ABSOLUTE);
        assertEquals(PressureBasis.ABSOLUTE,resolve(scope(),T1,T2,List.of(abs),List.of())
                .restriction().basis());
    }
}
