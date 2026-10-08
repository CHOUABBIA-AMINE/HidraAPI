/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningProductReferenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository;
import dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyCatalogEntryJpaEntity;
import org.springframework.jdbc.core.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.util.*;

class PlanningProductReferenceQueryAdapterTest {
    @Test void unknownAndUnapprovedRowsFailClosed() {
        var repo=mock(CustodyCatalogEntryJpaRepository.class);var jdbc=mock(JdbcTemplate.class);
        var provider=new PlanningProductReferenceQueryAdapter(repo,jdbc);
        when(repo.findByIdForShare("missing")).thenReturn(Optional.empty());
        assertTrue(provider.resolve("missing").isEmpty());verifyNoInteractions(jdbc);
        var entry=mock(CustodyCatalogEntryJpaEntity.class);when(entry.id()).thenReturn("p");
        when(repo.findByIdForShare("p")).thenReturn(Optional.of(entry));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("p"))).thenReturn(List.of());
        assertTrue(provider.resolve("p").isEmpty());
    }
    @Test void approvalAndOwnerActiveFlagsBothGovernFreshEligibility() {
        var repo=mock(CustodyCatalogEntryJpaRepository.class);var jdbc=mock(JdbcTemplate.class);var entry=mock(CustodyCatalogEntryJpaEntity.class);
        when(entry.id()).thenReturn("p");when(entry.code()).thenReturn("OWNER");when(entry.active()).thenReturn(true);
        when(repo.findByIdForShare("p")).thenReturn(Optional.of(entry));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("p"))).thenReturn(List.of(true),List.of(false));
        var provider=new PlanningProductReferenceQueryAdapter(repo,jdbc);
        assertTrue(provider.resolve("p").orElseThrow().active());assertFalse(provider.resolve("p").orElseThrow().active());
        when(entry.active()).thenReturn(false);
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("p"))).thenReturn(List.of(true));
        assertFalse(provider.resolve("p").orElseThrow().active());
    }
}
