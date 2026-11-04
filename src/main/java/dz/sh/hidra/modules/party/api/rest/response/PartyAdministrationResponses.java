/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.response
 *
 * @Description : Maps Party owner values to explicit non-entity REST responses.
 *
 */
package dz.sh.hidra.modules.party.api.rest.response;

import dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase;
import java.time.Instant;
import java.util.List;
public final class PartyAdministrationResponses {
    private PartyAdministrationResponses() { }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationView")
    public record View(String id,String code,String partyTypeId,String legalName,String tradeName,String shortName,String countryCode,String jurisdictionCode,String status,String primaryRoleCodeSnapshot,Instant createdAt,Instant updatedAt) {
        public static View of(PartyAdministrationUseCase.View p) { return new View(p.id(),p.code(),p.partyTypeId(),p.legalName(),p.tradeName(),p.shortName(),p.countryCode(),p.jurisdictionCode(),p.status(),p.primaryRoleCodeSnapshot(),p.createdAt(),p.updatedAt()); }
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationPage")
    public record Page(List<View> items,long total,int offset,int limit) {
        public static Page of(PartyAdministrationUseCase.ViewPage p) { return new Page(p.items().stream().map(View::of).toList(),p.total(),p.offset(),p.limit()); }
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationChoice")
    public record Choice(String id,String code,String label,boolean qualificationRequired) {
        public static Choice of(PartyAdministrationUseCase.Choice c) { return new Choice(c.id(),c.code(),c.label(),c.qualificationRequired()); }
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationHistory")
    public record History(String id,String oldStatus,String newStatus,String reason,String details,String actor,Instant at) {
        public static History of(PartyAdministrationUseCase.History h) { return new History(h.id(),h.oldStatus(),h.newStatus(),h.reason(),h.details(),h.actor(),h.at()); }
    }
}
