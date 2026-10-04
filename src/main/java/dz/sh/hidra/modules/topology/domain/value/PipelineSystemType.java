/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a Topology-owned PipelineSystem classification catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;

public record PipelineSystemType(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn
) {

    /*
     * Compatibility references for the six catalog rows migrated from the former enum.
     * The record remains open to future catalog codes and these constants are not an exhaustive taxonomy.
     */
    public static final PipelineSystemType TRANSPORT = seeded("TRANSPORT");
    public static final PipelineSystemType GATHERING = seeded("GATHERING");
    public static final PipelineSystemType DISTRIBUTION = seeded("DISTRIBUTION");
    public static final PipelineSystemType EXPORT = seeded("EXPORT");
    public static final PipelineSystemType IMPORT = seeded("IMPORT");
    public static final PipelineSystemType MIXED = seeded("MIXED");

    public PipelineSystemType {
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSystem system type id must not be blank.");
        }
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSystem system type code must not be blank.");
        }

        id = id.trim();
        code = code.trim();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
    }

    private static PipelineSystemType seeded(String code) {
        return new PipelineSystemType(code, code, null, null, null);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
