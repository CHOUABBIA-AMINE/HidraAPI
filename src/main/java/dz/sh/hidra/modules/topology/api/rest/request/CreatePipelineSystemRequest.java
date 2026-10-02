/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request to create pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
public record CreatePipelineSystemRequest(String code, String nameAr, String nameFr, String nameEn, PipelineSystemType systemType, String description) { }
