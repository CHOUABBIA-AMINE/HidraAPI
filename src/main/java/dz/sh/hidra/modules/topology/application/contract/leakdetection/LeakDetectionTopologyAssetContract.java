/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionTopologyAssetContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.leakdetection
 *
 * @Description : Topology-owned public contract for Leak Detection typed asset resolution.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.leakdetection;

/**
 * Resolves Leak Detection's supported typed Topology references without exposing
 * Topology domain or persistence representations.
 */
public interface LeakDetectionTopologyAssetContract {

    AssetResolution resolve(String assetType, String assetId);

    record AssetResolution(
            boolean supported,
            boolean exists,
            String id,
            String code,
            String name
    ) {

        public AssetResolution {
            if (!supported && exists) {
                throw new IllegalArgumentException("Unsupported Topology asset cannot exist.");
            }
            if (exists) {
                if (id == null || id.isBlank()) {
                    throw new IllegalArgumentException("Resolved Topology asset ID must not be blank.");
                }
                if (code == null || code.isBlank()) {
                    throw new IllegalArgumentException("Resolved Topology asset code must not be blank.");
                }
                id = id.trim();
                code = code.trim();
                name = normalize(name);
            } else {
                id = null;
                code = null;
                name = null;
            }
        }

        public static AssetResolution unsupported() {
            return new AssetResolution(false, false, null, null, null);
        }

        public static AssetResolution missing() {
            return new AssetResolution(true, false, null, null, null);
        }

        public static AssetResolution resolved(String id, String code, String name) {
            return new AssetResolution(true, true, id, code, name);
        }

        private static String normalize(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }
    }
}
