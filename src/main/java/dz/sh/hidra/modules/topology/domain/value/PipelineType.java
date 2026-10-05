/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a Topology-owned Pipeline classification catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;

public record PipelineType(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn
) {

    /*
     * Compatibility references for the seven catalog rows migrated from the former enum.
     * These constants are seeded references, not an exhaustive business taxonomy.
     */
    public static final PipelineType CRUDE_OIL = seeded("CRUDE_OIL");
    public static final PipelineType CONDENSATE = seeded("CONDENSATE");
    public static final PipelineType NATURAL_GAS = seeded("NATURAL_GAS");
    public static final PipelineType LPG = seeded("LPG");
    public static final PipelineType MULTI_PRODUCT = seeded("MULTI_PRODUCT");
    public static final PipelineType WATER = seeded("WATER");
    public static final PipelineType OTHER = seeded("OTHER");

    public PipelineType {
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("Pipeline type id must not be blank.");
        }
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("Pipeline type code must not be blank.");
        }

        id = id.trim();
        code = code.trim();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
    }

    private static PipelineType seeded(String code) {
        return new PipelineType(code, code, null, null, null);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
