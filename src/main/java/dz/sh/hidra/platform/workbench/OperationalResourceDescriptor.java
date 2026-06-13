/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalResourceDescriptor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
        String entityName,
        String javaType,
        String tableName,
        String idField,
        List<String> searchableFields,
        String listEndpoint,
        String detailEndpoint,
        String searchEndpoint
) { }
