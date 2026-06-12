/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.response
 *
 * @Description : REST response for risk register.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.response;

import dz.sh.hidra.modules.risk.domain.value.RiskRegisterStatus;

/**
 * REST response for risk register.
 */
public record RiskRegisterResponse(
        String id,
        String code,
        String nameFr,
        String registerTypeId,
        String scopeType,
        String scopeId,
        RiskRegisterStatus status
) {
}
