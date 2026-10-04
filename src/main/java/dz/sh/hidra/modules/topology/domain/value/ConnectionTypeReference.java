/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectionTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to the Topology-owned connection-type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;

/**
 * Stable catalog reference for topology connection business classification.
 *
 * @param id stable catalog identifier
 * @param code stable language-neutral code
 * @param nameAr optional Arabic label
 * @param nameFr optional French label
 * @param nameEn optional English label
 */
public record ConnectionTypeReference(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn
) {

    public static final ConnectionTypeReference PIPELINE_SEGMENT = seeded("PIPELINE_SEGMENT");
    public static final ConnectionTypeReference DIRECT_LINK = seeded("DIRECT_LINK");
    public static final ConnectionTypeReference VIRTUAL_LINK = seeded("VIRTUAL_LINK");
    public static final ConnectionTypeReference TRANSFER_LINK = seeded("TRANSFER_LINK");
    public static final ConnectionTypeReference MEASUREMENT_LINK = seeded("MEASUREMENT_LINK");

    public ConnectionTypeReference {
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException(
                    "Connection type id must not be blank."
            );
        }
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException(
                    "Connection type code must not be blank."
            );
        }

        id = id.trim();
        code = code.trim();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
    }

    private static ConnectionTypeReference seeded(String code) {
        return new ConnectionTypeReference(code, code, null, null, null);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
