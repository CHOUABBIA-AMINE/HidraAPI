/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterFacilityRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request to register facility.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import java.math.BigDecimal;
public record RegisterFacilityRequest(String code, String nameAr, String nameFr, String nameEn, String facilityTypeId, FacilityKind facilityKind, String ownerPartyId, String ownerPartyCodeSnapshot, String ownerPartyNameSnapshot, BigDecimal latitude, BigDecimal longitude, BigDecimal elevationMeters) { }
