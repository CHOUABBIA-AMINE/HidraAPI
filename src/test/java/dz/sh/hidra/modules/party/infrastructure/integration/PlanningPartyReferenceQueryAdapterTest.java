/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPartyReferenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.integration;

import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyJpaRepository;
import dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyJpaEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

class PlanningPartyReferenceQueryAdapterTest {
    @Test void blankAndUnknownPartyReferencesAreDenied() {
        var repo=mock(PartyJpaRepository.class);var provider=new PlanningPartyReferenceQueryAdapter(repo);
        assertTrue(provider.resolve(" ").isEmpty());verifyNoInteractions(repo);
        when(repo.findByIdForShare("missing")).thenReturn(Optional.empty());assertTrue(provider.resolve("missing").isEmpty());
    }
    @Test void existenceReturnsCanonicalOwnerCodeWithoutInventedRoleRestriction() {
        var repo=mock(PartyJpaRepository.class);var party=mock(PartyJpaEntity.class);
        when(party.id()).thenReturn("party");when(party.code()).thenReturn("CANONICAL");
        when(repo.findByIdForShare("party")).thenReturn(Optional.of(party));
        var resolved=new PlanningPartyReferenceQueryAdapter(repo).resolve("party").orElseThrow();
        assertEquals("party",resolved.id());assertEquals("CANONICAL",resolved.code());
        verify(repo).findByIdForShare("party");
    }
}
