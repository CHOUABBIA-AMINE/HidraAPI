/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response for a pipeline system with wire-safe classification catalog fields.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

public record PipelineSystemResponse(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeId,
        String systemTypeCode,
        String systemTypeNameAr,
        String systemTypeNameFr,
        String systemTypeNameEn,
        TopologyStatus status
) { }
