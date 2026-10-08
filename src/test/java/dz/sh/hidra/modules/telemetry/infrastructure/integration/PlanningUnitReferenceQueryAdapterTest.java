/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningUnitReferenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.integration;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryUnitJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryUnitJpaEntity;
import org.springframework.jdbc.core.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.util.*;

class PlanningUnitReferenceQueryAdapterTest {
    TelemetryUnitJpaEntity unit(String id){var u=mock(TelemetryUnitJpaEntity.class);when(u.id()).thenReturn(id);when(u.active()).thenReturn(true);when(u.dimension()).thenReturn("OWNER_DIMENSION");return u;}
    @Test void rolesAreRequiredEvenForExistingEngineeringUnits() {
        var repo=mock(TelemetryUnitJpaRepository.class);var jdbc=mock(JdbcTemplate.class);
        var quantity=unit("q");
        when(repo.findByIdForShare("q")).thenReturn(Optional.of(quantity));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("q"),eq("QUANTITY"))).thenReturn(List.of());
        assertTrue(new PlanningUnitReferenceQueryAdapter(repo,jdbc).resolve("q",null).isEmpty());
    }
    @Test void optionalRateAndInactiveRolesAreExplicit() {
        var repo=mock(TelemetryUnitJpaRepository.class);var jdbc=mock(JdbcTemplate.class);
        var quantity=unit("q");
        when(repo.findByIdForShare("q")).thenReturn(Optional.of(quantity));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("q"),eq("QUANTITY"))).thenReturn(List.of(false));
        var resolved=new PlanningUnitReferenceQueryAdapter(repo,jdbc).resolve("q",null).orElseThrow();
        assertNull(resolved.rate());assertFalse(resolved.quantity().active());
    }
    @Test void sortedIdentityLocksAndApprovedPairAreRequired() {
        var repo=mock(TelemetryUnitJpaRepository.class);var jdbc=mock(JdbcTemplate.class);
        var quantity=unit("z");var rate=unit("a");
        when(repo.findByIdForShare("z")).thenReturn(Optional.of(quantity));when(repo.findByIdForShare("a")).thenReturn(Optional.of(rate));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("z"),eq("QUANTITY"))).thenReturn(List.of(true));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("a"),eq("RATE"))).thenReturn(List.of(true));
        when(jdbc.query(anyString(),org.mockito.ArgumentMatchers.<RowMapper<Boolean>>any(),eq("z"),eq("a"))).thenReturn(List.of(),List.of(false));
        var provider=new PlanningUnitReferenceQueryAdapter(repo,jdbc);assertTrue(provider.resolve("z","a").isEmpty());
        var order=inOrder(repo);order.verify(repo).findByIdForShare("a");order.verify(repo).findByIdForShare("z");
        assertFalse(provider.resolve("z","a").orElseThrow().pairActive());
    }
}
