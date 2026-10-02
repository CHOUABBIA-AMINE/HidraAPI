/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilitySummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Facility summary DTO.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
public record FacilitySummaryDto(String id, String code, String nameAr, String nameFr, String nameEn, FacilityKind facilityKind, FacilityStatus status) { }
