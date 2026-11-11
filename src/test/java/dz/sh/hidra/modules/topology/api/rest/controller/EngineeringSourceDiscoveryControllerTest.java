/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Verifies exact selection DTOs and scoped calls without duplicating source-owner publication logic.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase.*;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EngineeringSourceDiscoveryControllerTest {
    private static final UUID DRAFT=UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID CONTEXT=UUID.fromString("00000000-0000-0000-0000-000000000022");
    private static final UUID REVISION=UUID.fromString("00000000-0000-0000-0000-000000000033");

    @Test
    void resolvesCurrentKnownDraftAsCompactUnqualifiedSource() {
        var owner=mock(EngineeringSourceDiscoveryUseCase.class);
        var source=new Source(Kind.NETWORK_PUBLICATION,CONTEXT,DRAFT,REVISION,3,
                "a".repeat(64),"ACTIVE",Instant.parse("2026-11-11T08:00:00Z"),true,false);
        when(owner.current(any())).thenReturn(Optional.of(source));
        var page=new EngineeringSourceDiscoveryController(owner).discover(
                "company-1","network-1",Kind.NETWORK_PUBLICATION,DRAFT.toString(),10,null);
        assertEquals(1,page.items().size());
        assertEquals(REVISION,page.items().getFirst().revisionId());
        assertEquals("a".repeat(64),page.items().getFirst().metadataSha256());
        assertFalse(page.items().getFirst().engineeringQualified());
        assertNull(page.nextCursor());
        verify(owner).current(new Lookup(Kind.NETWORK_PUBLICATION,"company-1","network-1",DRAFT,null,null));
    }
    @Test
    void exactReferencePreservesOwnerAndOriginalRevision() {
        var owner=mock(EngineeringSourceDiscoveryUseCase.class);
        when(owner.exact(any())).thenReturn(new Source(Kind.NETWORK_PUBLICATION,CONTEXT,DRAFT,REVISION,2,
                "b".repeat(64),"HISTORICAL",Instant.parse("2026-11-11T08:00:00Z"),false,false));
        var item=new EngineeringSourceDiscoveryController(owner).exact(Kind.NETWORK_PUBLICATION,
                CONTEXT.toString(),DRAFT.toString(),REVISION.toString(),"company-1","network-1");
        assertEquals(CONTEXT,item.ownerId());
        assertFalse(item.currentlyEligible());
        verify(owner).exact(new Lookup(Kind.NETWORK_PUBLICATION,"company-1","network-1",DRAFT,CONTEXT,REVISION));
    }
    @Test
    void rejectsUnsupportedPaginationAndNonCanonicalKeysBeforeOwner() {
        var owner=mock(EngineeringSourceDiscoveryUseCase.class);
        var controller=new EngineeringSourceDiscoveryController(owner);
        assertThrows(IllegalArgumentException.class,()->controller.discover(
                "company-1","network-1",Kind.NETWORK_PUBLICATION,DRAFT.toString(),51,null));
        assertThrows(IllegalArgumentException.class,()->controller.discover(
                "company-1","network-1",Kind.NETWORK_PUBLICATION,DRAFT.toString(),1,"cursor"));
        assertThrows(IllegalArgumentException.class,()->controller.discover(
                "company-1","network-1",Kind.NETWORK_PUBLICATION,"a",1,null));
        verifyNoInteractions(owner);
    }
    @Test
    void currentNoPublicationYieldsNoProtectedCandidateOrCount() {
        var owner=mock(EngineeringSourceDiscoveryUseCase.class);
        when(owner.current(any())).thenReturn(Optional.empty());
        var page=new EngineeringSourceDiscoveryController(owner).discover(
                "company-1","network-1",Kind.NETWORK_PUBLICATION,DRAFT.toString(),1,null);
        assertTrue(page.items().isEmpty());
        assertNull(page.nextCursor());
    }
}
