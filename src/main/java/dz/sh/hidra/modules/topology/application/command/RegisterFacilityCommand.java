/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterFacilityCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Command to register facility.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import java.math.BigDecimal;
public record RegisterFacilityCommand(String code, String nameAr, String nameFr, String nameEn, String facilityTypeId, FacilityKind facilityKind, String ownerPartyId, String ownerPartyCodeSnapshot, String ownerPartyNameSnapshot, BigDecimal latitude, BigDecimal longitude, BigDecimal elevationMeters) { }
