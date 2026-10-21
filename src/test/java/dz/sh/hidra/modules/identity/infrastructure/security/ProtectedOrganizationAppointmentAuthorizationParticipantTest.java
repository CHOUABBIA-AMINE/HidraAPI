/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOrganizationAppointmentAuthorizationParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Tests the finite appointment request and observation contract before native integration.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** These are scalar contract cases, not evidence of native credentials or database admission. */
class ProtectedOrganizationAppointmentAuthorizationParticipantTest {
    @Test void participantRejectsFabricatedTicketsAndPoisonsCurrentHolder() {
        try (var fixture = new Mechanics()) {
            assertThrows(RuntimeException.class, () -> fixture.service.acquire(new Prepared(){}, fixture.request));
            assertTrue(fixture.holder.isRollbackOnly());
            var synchronizations = org.springframework.transaction.support.TransactionSynchronizationManager.getSynchronizations();
            assertFalse(synchronizations.isEmpty());
            int jdbcCalls = org.mockito.Mockito.mockingDetails(fixture.jdbc).getInvocations().size();
            assertThrows(org.springframework.security.access.AccessDeniedException.class,
                    () -> synchronizations.forEach(s -> s.beforeCommit(false)));
            assertEquals(jdbcCalls, org.mockito.Mockito.mockingDetails(fixture.jdbc).getInvocations().size());
            org.mockito.Mockito.verifyNoInteractions(fixture.reader);
        }
        try (var fixture = new Mechanics()) {
            assertThrows(RuntimeException.class, () -> fixture.service.contribute(new Admission(){}));
            assertTrue(fixture.holder.isRollbackOnly());
        }
    }

    @Test void participantPreparationCannotBeReplayedOrConsumedByAnotherIssuer() {
        try (var fixture = new Mechanics()) {
            fixture.service.acquire(fixture.prepared, fixture.request);
            assertThrows(RuntimeException.class, () -> fixture.service.acquire(fixture.prepared, fixture.request));
            assertTrue(fixture.holder.isRollbackOnly());
        }
        try (var fixture = new Mechanics()) {
            var other = new ProtectedOrganizationAppointmentAuthorizationParticipant(fixture.identity, fixture.reader, fixture.jdbc);
            assertThrows(RuntimeException.class, () -> other.acquire(fixture.prepared, fixture.request));
            assertTrue(fixture.holder.isRollbackOnly());
        }
    }

    @Test void participantRejectsFinalFencesBeforeCompleteContribution() {
        try (var fixture = new Mechanics()) {
            var admission = fixture.service.acquire(fixture.prepared, fixture.request);
            assertThrows(RuntimeException.class, () -> fixture.service.finalFences(admission));
            assertTrue(fixture.holder.isRollbackOnly());
            org.mockito.Mockito.verifyNoInteractions(fixture.reader);
        }
    }

    @Test void participantRejectsChangedAuthenticationAndMissingPhysicalContext() {
        try (var fixture = new Mechanics()) {
            var admission = fixture.service.acquire(fixture.prepared, fixture.request);
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
            assertThrows(RuntimeException.class, () -> fixture.service.contribute(admission));
            assertTrue(fixture.holder.isRollbackOnly());
        }
        try (var fixture = new Mechanics()) {
            org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(false);
            assertThrows(RuntimeException.class, () -> fixture.service.acquire(fixture.prepared, fixture.request));
            assertTrue(fixture.holder.isRollbackOnly());
        }
    }

    @Test void participantWrongThreadFailurePoisonsOriginalHolder() throws Exception {
        try (var fixture = new Mechanics()) {
            var admission = fixture.service.acquire(fixture.prepared, fixture.request);
            var worker = java.util.concurrent.Executors.newSingleThreadExecutor();
            try { worker.submit(() -> assertThrows(RuntimeException.class,
                    () -> fixture.service.requireCurrent(admission))).get(5, java.util.concurrent.TimeUnit.SECONDS); }
            finally { worker.shutdownNow(); }
            assertTrue(fixture.holder.isRollbackOnly());
        }
    }

    /** Mock mechanics only. PostgreSQL cases separately exercise real native origins and graphs. */
    private static final class Mechanics implements AutoCloseable {
        final ProtectedOperationIdentityParticipant identity = org.mockito.Mockito.mock(ProtectedOperationIdentityParticipant.class);
        final dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader reader =
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader.class);
        final org.springframework.jdbc.core.JdbcTemplate jdbc = org.mockito.Mockito.mock(org.springframework.jdbc.core.JdbcTemplate.class);
        final javax.sql.DataSource dataSource = org.mockito.Mockito.mock(javax.sql.DataSource.class);
        final org.springframework.jdbc.datasource.ConnectionHolder holder = new org.springframework.jdbc.datasource.ConnectionHolder(org.mockito.Mockito.mock(java.sql.Connection.class));
        final ProtectedOrganizationAppointmentAuthorizationParticipant service = new ProtectedOrganizationAppointmentAuthorizationParticipant(identity, reader, jdbc);
        final Request request = new Request(Action.CREATE, List.of(new CompanyUnit("company", "unit")), "employee");
        final Prepared prepared;
        Mechanics() {
            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(
                    org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated("synthetic", "", List.of()));
            org.mockito.Mockito.when(identity.prepare()).thenReturn(org.mockito.Mockito.mock(ProtectedOperationIdentityParticipant.Prepared.class));
            org.mockito.Mockito.when(identity.admit(org.mockito.ArgumentMatchers.any())).thenReturn(org.mockito.Mockito.mock(ProtectedOperationIdentityParticipant.Admitted.class));
            org.mockito.Mockito.when(jdbc.getDataSource()).thenReturn(dataSource);
            prepared = service.prepare();
            org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(true);
            org.springframework.transaction.support.TransactionSynchronizationManager.initSynchronization();
            org.springframework.transaction.support.TransactionSynchronizationManager.bindResource(dataSource, holder);
        }
        @Override public void close() {
            org.springframework.transaction.support.TransactionSynchronizationManager.unbindResource(dataSource);
            org.springframework.transaction.support.TransactionSynchronizationManager.clear();
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        }
    }
    private static final CompanyUnit A1 = new CompanyUnit("company-a", "unit-1");
    private static final CompanyUnit A2 = new CompanyUnit("company-a", "unit-2");
    private static final CompanyUnit B3 = new CompanyUnit("company-b", "unit-3");
    private static final Instant SAMPLE = Instant.parse("2026-10-21T12:00:00Z");

    @Test
    void createAndEndRequireExactlyOnePairAndEmployee() {
        for (var action : List.of(Action.CREATE, Action.END)) {
            assertEquals(List.of(A1), request(action, List.of(A1), "employee-1").companyUnits());
            assertThrows(IllegalArgumentException.class, () -> request(action, List.of(A1), null));
            assertThrows(IllegalArgumentException.class, () -> request(action, List.of(A1, A2), "employee-1"));
        }
    }

    @Test
    void transferAndCorrectAllowOneOrTwoSameCompanyPairs() {
        for (var action : List.of(Action.TRANSFER, Action.CORRECT)) {
            assertEquals(3, request(action, List.of(A1), "employee-1").requirements().size());
            assertEquals(4, request(action, List.of(A1, A2), "employee-1").requirements().size());
        }
    }

    @Test
    void transferAndCorrectRejectCrossCompanyOrMissingEmployee() {
        for (var action : List.of(Action.TRANSFER, Action.CORRECT)) {
            assertThrows(IllegalArgumentException.class, () -> request(action, List.of(A1, B3), "employee-1"));
            assertThrows(IllegalArgumentException.class, () -> request(action, List.of(A1, A2), null));
        }
    }

    @Test
    void transferAndCorrectRejectThreePairsEvenInOneCompany() {
        var pairs = List.of(A1, A2, new CompanyUnit("company-a", "unit-3"));
        for (var action : List.of(Action.TRANSFER, Action.CORRECT)) {
            assertThrows(IllegalArgumentException.class, () -> request(action, pairs, "employee-1"));
        }
    }

    @Test
    void currentAndHistoryReadsSupportUnitOrEmployeeViews() {
        for (var action : List.of(Action.READ_CURRENT, Action.READ_HISTORY)) {
            assertEquals(List.of(new Requirement(ResourceKind.COMPANY, "company-a"),
                            new Requirement(ResourceKind.UNIT, "unit-1")),
                    request(action, List.of(A1), null).requirements());
            assertEquals(new Requirement(ResourceKind.EMPLOYEE, "employee-1"),
                    request(action, List.of(A1), "employee-1").requirements().get(2));
        }
    }

    @Test
    void policyReadRejectsEmployeeAndRequiresCompanyAndUnit() {
        assertEquals(2, request(Action.READ_POLICY, List.of(A1), null).requirements().size());
        assertThrows(IllegalArgumentException.class,
                () -> request(Action.READ_POLICY, List.of(A1), "employee-1"));
    }

    @Test
    void everyReadAcceptsOneHundredPairsAndRejectsOneHundredOne() {
        for (var action : List.of(Action.READ_CURRENT, Action.READ_HISTORY, Action.READ_POLICY)) {
            assertEquals(100, request(action, pairs(100), null).companyUnits().size());
            assertThrows(IllegalArgumentException.class, () -> request(action, pairs(101), null));
        }
    }

    @Test
    void rejectsEmptyNullOrNullElementPairsAndMissingAction() {
        assertThrows(IllegalArgumentException.class, () -> request(Action.READ_HISTORY, List.of(), null));
        assertThrows(NullPointerException.class, () -> request(Action.READ_HISTORY, null, null));
        assertThrows(NullPointerException.class,
                () -> request(Action.READ_HISTORY, java.util.Arrays.asList(A1, null), null));
        assertThrows(NullPointerException.class, () -> request(null, List.of(A1), null));
    }

    @Test
    void rejectsDuplicateAndNoncanonicalCompanyThenUnitOrdering() {
        for (var pairs : List.of(List.of(A1, A1), List.of(A2, A1), List.of(B3, A1))) {
            assertThrows(IllegalArgumentException.class, () -> request(Action.READ_HISTORY, pairs, null));
        }
    }

    @Test
    void requestCopiesPairsAndNeverReordersCallerData() {
        var input = new ArrayList<>(List.of(A1, A2));
        var request = request(Action.READ_HISTORY, input, null);
        input.clear();
        assertEquals(List.of(A1, A2), request.companyUnits());
        assertThrows(UnsupportedOperationException.class, () -> request.companyUnits().clear());
        assertThrows(UnsupportedOperationException.class, () -> request.requirements().clear());
    }

    @Test
    void requiresEveryHistoricalCompanyAndDistinctUnitInStableOrder() {
        var request = request(Action.READ_HISTORY, List.of(A1, A2, B3), "employee-1");
        assertEquals(List.of(new Requirement(ResourceKind.COMPANY, "company-a"),
                new Requirement(ResourceKind.COMPANY, "company-b"),
                new Requirement(ResourceKind.UNIT, "unit-1"),
                new Requirement(ResourceKind.UNIT, "unit-2"),
                new Requirement(ResourceKind.UNIT, "unit-3"),
                new Requirement(ResourceKind.EMPLOYEE, "employee-1")), request.requirements());
    }

    @Test
    void historicalUnitSharedAcrossCompanyPairsRetainsBothCompanyRequirements() {
        var request = request(Action.READ_HISTORY,
                List.of(A1, new CompanyUnit("company-b", "unit-1")), null);
        assertEquals(List.of(new Requirement(ResourceKind.COMPANY, "company-a"),
                new Requirement(ResourceKind.COMPANY, "company-b"),
                new Requirement(ResourceKind.UNIT, "unit-1")), request.requirements());
    }

    @Test
    void equalIdentifiersAcrossKindsRemainSeparateRequirements() {
        var request = request(Action.CREATE, List.of(new CompanyUnit("same", "same")), "same");
        assertEquals(List.of(new Requirement(ResourceKind.COMPANY, "same"),
                new Requirement(ResourceKind.UNIT, "same"),
                new Requirement(ResourceKind.EMPLOYEE, "same")), request.requirements());
    }

    @Test
    void rejectsMissingBlankPaddedControlAndMalformedIdentifiersEverywhere() {
        for (String invalid : java.util.Arrays.asList(null, "", " ", " id", "id ", "\tid", "id\n",
                "id\u0000x", "id\u007fx", "\u2003id", "id\u2003", "\u00a0id", "id\u00a0", "\ud800", "\udc00")) {
            assertThrows(IllegalArgumentException.class, () -> new CompanyUnit(invalid, "unit"));
            assertThrows(IllegalArgumentException.class, () -> new CompanyUnit("company", invalid));
            assertThrows(IllegalArgumentException.class, () -> new Requirement(ResourceKind.UNIT, invalid));
            // Null has the separately specified meaning of no employee on a read request.
            if (invalid != null) {
                assertThrows(IllegalArgumentException.class,
                        () -> request(Action.READ_CURRENT, List.of(A1), invalid));
            }
        }
    }

    @Test
    void identifierLimitUsesCodePointsWithoutSilentlyNormalizing() {
        String exact = "\ud83d\ude00".repeat(80);
        assertEquals(exact, new CompanyUnit(exact, exact).companyPartyId());
        assertEquals(exact, request(Action.CREATE, List.of(A1), exact).employeeId());
        assertThrows(IllegalArgumentException.class, () -> new CompanyUnit(exact + "x", "unit"));
        assertThrows(IllegalArgumentException.class, () -> new CompanyUnit("company", exact + "x"));
        assertThrows(IllegalArgumentException.class,
                () -> request(Action.READ_HISTORY, List.of(A1), exact + "x"));
        assertNotEquals(new CompanyUnit("Company", "unit"), new CompanyUnit("company", "unit"));
    }

    @Test
    void permissionVocabularyIsExactlyTwentyExplicitActionResourceDefinitions() {
        var codes = new HashSet<String>();
        var verbs = List.of("create", "end", "transfer", "correct", "read-current", "read-history", "read-policy");
        var resources = List.of("company", "unit", "employee");
        for (int a = 0; a < Action.values().length; a++) {
            for (int k = 0; k < ResourceKind.values().length; k++) {
                if (a == 6 && k == 2) continue;
                String code = OrganizationProtectedAppointmentAuthorizationContract
                        .permissionCode(Action.values()[a], ResourceKind.values()[k]);
                assertEquals("organization:appointment-" + resources.get(k) + ":" + verbs.get(a), code);
                assertTrue(codes.add(code));
            }
        }
        assertEquals(20, codes.size());
    }

    @Test
    void permissionDispatchRejectsEmployeePolicyAndNullDispatchInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> OrganizationProtectedAppointmentAuthorizationContract.permissionCode(Action.READ_POLICY, ResourceKind.EMPLOYEE));
        assertThrows(NullPointerException.class,
                () -> OrganizationProtectedAppointmentAuthorizationContract.permissionCode(null, ResourceKind.UNIT));
        assertThrows(NullPointerException.class,
                () -> OrganizationProtectedAppointmentAuthorizationContract.permissionCode(Action.CREATE, null));
        assertThrows(NullPointerException.class, () -> new Requirement(null, "unit"));
    }

    @Test
    void resourceTypesAreExactAndPermissionDispatchIsLocaleIndependent() {
        assertEquals("ORGANIZATION_COMPANY", ResourceKind.COMPANY.resourceType());
        assertEquals("ORGANIZATION_UNIT", ResourceKind.UNIT.resourceType());
        assertEquals("ORGANIZATION_EMPLOYEE", ResourceKind.EMPLOYEE.resourceType());
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals("organization:appointment-unit:read-policy",
                    OrganizationProtectedAppointmentAuthorizationContract.permissionCode(Action.READ_POLICY, ResourceKind.UNIT));
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    void contributionRequiresCompleteExactCoverageIncludingDestinationAndEmployee() {
        var request = request(Action.TRANSFER, List.of(A1, A2), "employee-1");
        var complete = decisions(request, Decision.PERMIT);
        for (int i = 0; i < complete.size(); i++) {
            var missing = new ArrayList<>(complete);
            missing.remove(i);
            assertThrows(IllegalArgumentException.class, () -> contribution(request, missing));
        }
        assertEquals(Decision.PERMIT, contribution(request, complete).overallDecision());
    }

    @Test
    void contributionRejectsForeignDuplicateExtraAndReorderedDecisions() {
        var request = request(Action.CREATE, List.of(A1), "employee-1");
        var complete = decisions(request, Decision.PERMIT);
        var foreign = new ArrayList<>(complete);
        foreign.set(0, new ResourceDecision(new Requirement(ResourceKind.COMPANY, "foreign"), Decision.PERMIT));
        var duplicate = new ArrayList<>(complete);
        duplicate.set(1, duplicate.get(0));
        var extra = new ArrayList<>(complete);
        extra.add(new ResourceDecision(new Requirement(ResourceKind.UNIT, "foreign"), Decision.PERMIT));
        var reordered = new ArrayList<>(complete);
        Collections.reverse(reordered);
        for (var invalid : List.of(foreign, duplicate, extra, reordered)) {
            assertThrows(IllegalArgumentException.class, () -> contribution(request, invalid));
        }
    }

    @Test
    void everyCompanyUnitAndEmployeeDenialWinsOverIndeterminate() {
        var request = request(Action.READ_HISTORY, List.of(A1, A2, B3), "employee-1");
        for (int i = 0; i < request.requirements().size(); i++) {
            var values = new ArrayList<>(decisions(request, Decision.INDETERMINATE));
            values.set(i, new ResourceDecision(request.requirements().get(i), Decision.DENY));
            assertEquals(Decision.DENY, contribution(request, values).overallDecision());
        }
    }

    @Test
    void anyUncertainRequirementPreventsAllPermitObservation() {
        var request = request(Action.CREATE, List.of(A1), "employee-1");
        for (int i = 0; i < request.requirements().size(); i++) {
            var values = new ArrayList<>(decisions(request, Decision.PERMIT));
            values.set(i, new ResourceDecision(request.requirements().get(i), Decision.INDETERMINATE));
            assertEquals(Decision.INDETERMINATE, contribution(request, values).overallDecision());
        }
    }

    @Test
    void contributionCopiesDecisionsAndRetainsOneNativeSample() {
        var request = request(Action.CREATE, List.of(A1), "employee-1");
        var values = new ArrayList<>(decisions(request, Decision.PERMIT));
        var result = contribution(request, values);
        values.clear();
        assertEquals(3, result.decisions().size());
        assertThrows(UnsupportedOperationException.class, () -> result.decisions().clear());
        assertEquals(request, result.request());
        assertEquals("binding", result.bindingId());
        assertEquals("session", result.sessionId());
        assertEquals("user", result.userId());
        assertEquals(SAMPLE, result.sampledAt());
        assertEquals(SAMPLE.plusSeconds(30), result.validUntil());
        assertEquals(12, result.backendPid());
        assertEquals(34L, result.transactionId());
    }

    @Test
    void contributionRejectsExpiredOrNonpositivePhysicalCoordinates() {
        var request = request(Action.CREATE, List.of(A1), "employee-1");
        var values = decisions(request, Decision.PERMIT);
        for (var until : List.of(SAMPLE, SAMPLE.minusNanos(1))) {
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    "binding", "session", "user", SAMPLE, until, 12, 34));
        }
        for (int pid : List.of(0, -1)) {
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    "binding", "session", "user", SAMPLE, SAMPLE.plusSeconds(1), pid, 34));
        }
        for (long xid : List.of(0L, -1L)) {
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    "binding", "session", "user", SAMPLE, SAMPLE.plusSeconds(1), 12, xid));
        }
    }

    @Test
    void contributionRejectsMissingMetadataAndMalformedNativeIds() {
        var request = request(Action.CREATE, List.of(A1), "employee-1");
        var values = decisions(request, Decision.PERMIT);
        assertThrows(NullPointerException.class, () -> new Contribution(null, values,
                "binding", "session", "user", SAMPLE, SAMPLE.plusSeconds(1), 12, 34));
        assertThrows(NullPointerException.class, () -> contribution(request, null));
        assertThrows(NullPointerException.class, () -> contribution(request, java.util.Arrays.asList((ResourceDecision) null)));
        assertThrows(NullPointerException.class, () -> new ResourceDecision(null, Decision.PERMIT));
        assertThrows(NullPointerException.class, () -> new ResourceDecision(request.requirements().get(0), null));
        assertThrows(NullPointerException.class, () -> new Contribution(request, values,
                "binding", "session", "user", null, SAMPLE.plusSeconds(1), 12, 34));
        assertThrows(NullPointerException.class, () -> new Contribution(request, values,
                "binding", "session", "user", SAMPLE, null, 12, 34));
        for (String invalid : java.util.Arrays.asList(null, "", " binding", "binding\n", "x".repeat(81))) {
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    invalid, "session", "user", SAMPLE, SAMPLE.plusSeconds(1), 12, 34));
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    "binding", invalid, "user", SAMPLE, SAMPLE.plusSeconds(1), 12, 34));
            assertThrows(IllegalArgumentException.class, () -> new Contribution(request, values,
                    "binding", "session", invalid, SAMPLE, SAMPLE.plusSeconds(1), 12, 34));
        }
    }

    @Test
    void maximumHistoryGroupRetainsAllCompaniesUnitsAndEmployeeDecisions() {
        var pairs = IntStream.range(0, 100).mapToObj(i -> new CompanyUnit(
                String.format(Locale.ROOT, "company-%03d", i),
                String.format(Locale.ROOT, "unit-%03d", 99 - i))).toList();
        var request = request(Action.READ_HISTORY, pairs, "employee-1");
        assertEquals(201, request.requirements().size());
        assertEquals(new Requirement(ResourceKind.COMPANY, "company-099"), request.requirements().get(99));
        assertEquals(new Requirement(ResourceKind.UNIT, "unit-000"), request.requirements().get(100));
        assertEquals(new Requirement(ResourceKind.UNIT, "unit-099"), request.requirements().get(199));
        assertEquals(new Requirement(ResourceKind.EMPLOYEE, "employee-1"), request.requirements().get(200));
        var values = decisions(request, Decision.PERMIT);
        assertEquals(Decision.PERMIT, contribution(request, values).overallDecision());
        assertThrows(IllegalArgumentException.class, () -> contribution(request, values.subList(0, 200)));
    }

    private static Request request(Action action, List<CompanyUnit> pairs, String employee) {
        return new Request(action, pairs, employee);
    }

    private static List<CompanyUnit> pairs(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new CompanyUnit("company", "unit-" + String.format(Locale.ROOT, "%03d", i)))
                .toList();
    }

    private static List<ResourceDecision> decisions(Request request, Decision decision) {
        return request.requirements().stream().map(r -> new ResourceDecision(r, decision)).toList();
    }

    private static Contribution contribution(Request request, List<ResourceDecision> decisions) {
        return new Contribution(request, decisions, "binding", "session", "user",
                SAMPLE, SAMPLE.plusSeconds(30), 12, 34);
    }
}
