/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalRecordResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Returns a generic operational record for workbench detail and list views.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.Map;

/**
 * Returns a generic operational record for workbench detail and list views.
 */
public record OperationalRecordResponse(
        String module,
        String resource,
        Object id,
        Map<String, Object> attributes
) { }
