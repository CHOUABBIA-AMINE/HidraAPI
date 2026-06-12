/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.dto
 *
 * @Description : Risk register summary DTO.
 *
 */
package dz.sh.hidra.modules.risk.application.dto;

import dz.sh.hidra.modules.risk.domain.value.RiskRegisterStatus;

/**
 * Risk register summary DTO.
 */
public record RiskRegisterSummaryDto(
        String id,
        String code,
        String nameFr,
        String registerTypeId,
        String scopeType,
        String scopeId,
        RiskRegisterStatus status
) {
}
