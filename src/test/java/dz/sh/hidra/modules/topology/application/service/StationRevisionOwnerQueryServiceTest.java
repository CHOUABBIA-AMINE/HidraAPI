/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationRevisionOwnerQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies exact live owner lookup, eligibility, fresh observations and bounded references.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyEligibilityContract;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.StationRevisionOwnerQueryService.OwnerReference;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class StationRevisionOwnerQueryServiceTest {
    private static final Instant NOW = Instant.parse("2026-10-11T00:00:00Z");
    private final Repository repository = new Repository();
    private final Eligibility eligibility = new Eligibility();
    private final StationRevisionOwnerQueryService service =
            new StationRevisionOwnerQueryService(repository, eligibility);

    @ParameterizedTest
    @MethodSource("invalidReferences")
    void invalidQueryDoesNotReadEitherOwner(String identity) {
        assertTrue(service.resolve(identity).isEmpty());
        assertTrue(repository.lookups.isEmpty());
        assertTrue(eligibility.lookups.isEmpty());
    }

    @Test
    void resolvesTrimmedExactIdentitiesWithoutCaseOrSnapshotSubstitution() {
        repository.lookup = id -> Optional.of(facility(" Synthetic-F ", " Synthetic-P "));
        assertEquals(Optional.of(new OwnerReference("Synthetic-F", "Synthetic-P")),
                service.resolve("\t Synthetic-F \n"));
        assertEquals(List.of("Synthetic-F"), repository.lookups);
        assertEquals(List.of("Synthetic-P"), eligibility.lookups);
    }

    @Test
    void absentFacilityDoesNotQueryParty() {
        assertTrue(service.resolve("synthetic-missing").isEmpty());
        assertEquals(List.of("synthetic-missing"), repository.lookups);
        assertTrue(eligibility.lookups.isEmpty());
    }

    @Test
    void differentFacilityIdentityDoesNotQueryPartyEvenWithPlausibleOwner() {
        repository.lookup = id -> Optional.of(facility("Synthetic-f", "Synthetic-P"));
        assertTrue(service.resolve("Synthetic-F").isEmpty());
        assertTrue(eligibility.lookups.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidReferences")
    void missingOrOverlongLiveOwnerCannotUsePopulatedSnapshots(String owner) {
        repository.lookup = id -> Optional.of(facility(id, owner));
        assertTrue(service.resolve("synthetic-f").isEmpty());
        assertEquals(List.of("synthetic-f"), repository.lookups);
        assertTrue(eligibility.lookups.isEmpty());
    }

    @Test
    void ineligibleOwnerDoesNotProduceReference() {
        repository.lookup = id -> Optional.of(facility(id, "synthetic-p"));
        eligibility.lookup = id -> false;
        assertTrue(service.resolve("synthetic-f").isEmpty());
        assertEquals(List.of("synthetic-p"), eligibility.lookups);
    }

    @Test
    void independentFacilitiesUseOnlyTheirOwnLiveOwner() {
        repository.lookup = id -> Optional.of(facility(id,
                id.equals("synthetic-fa") ? "synthetic-pa" : "synthetic-pb"));
        eligibility.lookup = id -> id.equals("synthetic-pa");
        assertEquals(Optional.of(new OwnerReference("synthetic-fa", "synthetic-pa")),
                service.resolve("synthetic-fa"));
        assertTrue(service.resolve("synthetic-fb").isEmpty());
        assertEquals(List.of("synthetic-fa", "synthetic-fb"), repository.lookups);
        assertEquals(List.of("synthetic-pa", "synthetic-pb"), eligibility.lookups);
    }

    @Test
    void rereadsOwnerAndEligibilityAfterEachMasterChange() {
        repository.lookup = id -> Optional.of(facility(id, "synthetic-pa"));
        OwnerReference first = service.resolve("synthetic-f").orElseThrow();
        repository.lookup = id -> Optional.of(facility(id, "synthetic-pb"));
        assertEquals(new OwnerReference("synthetic-f", "synthetic-pb"),
                service.resolve("synthetic-f").orElseThrow());
        eligibility.lookup = id -> false;
        assertTrue(service.resolve("synthetic-f").isEmpty());
        repository.lookup = id -> Optional.empty();
        assertTrue(service.resolve("synthetic-f").isEmpty());
        assertEquals(new OwnerReference("synthetic-f", "synthetic-pa"), first);
        assertEquals(List.of("synthetic-f", "synthetic-f", "synthetic-f", "synthetic-f"),
                repository.lookups);
        assertEquals(List.of("synthetic-pa", "synthetic-pb", "synthetic-pb"), eligibility.lookups);
    }

    @ParameterizedTest
    @EnumSource(FacilityStatus.class)
    void referenceDoesNotIntroduceFacilityLifecyclePermissionRules(FacilityStatus status) {
        repository.lookup = id -> Optional.of(facility(id, "synthetic-p", status, FacilityKind.STATION));
        assertEquals(Optional.of(new OwnerReference("synthetic-f", "synthetic-p")),
                service.resolve("synthetic-f"));
    }

    @ParameterizedTest
    @EnumSource(FacilityKind.class)
    void referenceDoesNotIntroduceFacilityTypePermissionRules(FacilityKind kind) {
        repository.lookup = id -> Optional.of(facility(id, "synthetic-p", FacilityStatus.ACTIVE, kind));
        assertEquals(Optional.of(new OwnerReference("synthetic-f", "synthetic-p")),
                service.resolve("synthetic-f"));
    }

    @Test
    void asciiAndSupplementaryUnicodeAtStorageLimitAreNotTruncated() {
        for (String identity : List.of("a".repeat(80), "\uD83D\uDE80".repeat(80))) {
            repository.lookup = id -> Optional.of(facility(id, identity));
            assertEquals(Optional.of(new OwnerReference(identity, identity)),
                    service.resolve(" " + identity + " "));
        }
        assertEquals(List.of("a".repeat(80), "\uD83D\uDE80".repeat(80)), repository.lookups);
        assertEquals(repository.lookups, eligibility.lookups);
    }

    @Test
    void repositoryFailurePropagatesWithoutPartyLookup() {
        var failure = new IllegalStateException("Synthetic Facility storage failure");
        repository.lookup = id -> { throw failure; };
        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> service.resolve("synthetic-f")));
        assertTrue(eligibility.lookups.isEmpty());
    }

    @Test
    void partyFailurePropagatesWithoutReference() {
        repository.lookup = id -> Optional.of(facility(id, "synthetic-p"));
        var failure = new IllegalStateException("Synthetic Party lookup failure");
        eligibility.lookup = id -> { throw failure; };
        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> service.resolve("synthetic-f")));
        assertEquals(List.of("synthetic-p"), eligibility.lookups);
    }

    @Test
    void constructorRequiresBothOwnerDependencies() {
        assertThrows(NullPointerException.class, () -> new StationRevisionOwnerQueryService(null, eligibility));
        assertThrows(NullPointerException.class, () -> new StationRevisionOwnerQueryService(repository, null));
    }

    @Test
    void referenceNormalizesBothIdentities() {
        assertEquals(new OwnerReference("Synthetic-F", "Synthetic-P"),
                new OwnerReference(" Synthetic-F ", "\tSynthetic-P\n"));
    }

    @ParameterizedTest
    @MethodSource("invalidReferences")
    void referenceRejectsEitherInvalidComponent(String identity) {
        assertThrows(IllegalArgumentException.class, () -> new OwnerReference(identity, "synthetic-p"));
        assertThrows(IllegalArgumentException.class, () -> new OwnerReference("synthetic-f", identity));
    }

    private static Stream<String> invalidReferences() {
        return Stream.of(null, "", " \t\n", "\u2003", "a".repeat(81), "\uD83D\uDE80".repeat(81));
    }

    private static Facility facility(String id, String owner) {
        return facility(id, owner, FacilityStatus.ACTIVE, FacilityKind.STATION);
    }

    private static Facility facility(String id, String owner, FacilityStatus status, FacilityKind kind) {
        return new Facility(id, "synthetic-code", null, null, "Synthetic facility", "synthetic-type",
                kind, owner, "plausible-company-code", "Plausible company name",
                null, null, null, status, null, null, NOW, NOW);
    }

    private static final class Repository implements FacilityRepositoryPort {
        private final List<String> lookups = new ArrayList<>();
        private Function<String, Optional<Facility>> lookup = id -> Optional.empty();

        @Override
        public Optional<Facility> findById(String id) {
            lookups.add(id);
            return lookup.apply(id);
        }

        @Override
        public Facility save(Facility facility) {
            throw new AssertionError("Owner query must never write Facility data.");
        }
    }

    private static final class Eligibility implements TopologyPartyEligibilityContract {
        private final List<String> lookups = new ArrayList<>();
        private Function<String, Boolean> lookup = id -> true;

        @Override
        public boolean eligibleForNewReference(String partyId) {
            lookups.add(partyId);
            return lookup.apply(partyId);
        }
    }
}
