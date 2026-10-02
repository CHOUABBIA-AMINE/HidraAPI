/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Framework-neutral topology controller contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;
import dz.sh.hidra.modules.topology.api.rest.request.*;
import dz.sh.hidra.modules.topology.api.rest.response.*;

/**
 * Framework-neutral topology controller contract.
 */
public interface TopologyController {
    PipelineSystemResponse createPipelineSystem(CreatePipelineSystemRequest request);
    FacilityResponse registerFacility(RegisterFacilityRequest request);
}
