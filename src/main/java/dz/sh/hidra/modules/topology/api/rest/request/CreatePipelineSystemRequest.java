/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request to create a pipeline system using an explicit classification catalog code.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

public record CreatePipelineSystemRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeCode,
        String description
) { }
