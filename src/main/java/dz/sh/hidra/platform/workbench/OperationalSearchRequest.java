/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalSearchRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Carries generic search criteria for operational workbench resources.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.Map;

/**
 * Carries generic search criteria for operational workbench resources.
 */
public record OperationalSearchRequest(
        String query,
        Map<String, Object> filters,
        Integer page,
        Integer size,
        String sortBy,
        String sortDirection
) { }
