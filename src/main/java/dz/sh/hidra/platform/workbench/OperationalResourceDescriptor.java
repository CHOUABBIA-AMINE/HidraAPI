/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalResourceDescriptor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Describes a readable operational resource exposed to workbench clients.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.List;

/**
 * Describes a readable operational resource exposed to workbench clients.
 */
public record OperationalResourceDescriptor(
        String module,
        String resource,
        String idField,
        List<String> outputFields,
        List<String> searchableFields,
        List<String> filterableFields,
        List<String> sortableFields,
        String readPermission,
        String searchPermission,
        String listEndpoint,
        String detailEndpoint,
        String searchEndpoint
) { }
