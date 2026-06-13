/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPageResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Returns a page of generic operational records.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.List;

/**
 * Returns a page of generic operational records.
 */
public record OperationalPageResponse(
        String module,
        String resource,
        int page,
        int size,
        long totalElements,
        int totalPages,
        List<OperationalRecordResponse> items
) { }
