/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopedAccessContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Exports paired current company and facility permission checks for Topology.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

/**
 * Current permission query only; references do not prove ownership and the result
 * is not an atomic grant for subsequent publication or history access.
 */
public interface TopologyScopedAccessContract {

    boolean permitted(Request request);

    enum Operation {
        AUTHOR, PUBLISH, READ_CURRENT, READ_HISTORY, LIST_HISTORY, WITHDRAW, REACTIVATE, TRANSFER
    }

    enum Target { STATION, FOUNDATION_COMPANY, FOUNDATION_FACILITY }
    record Request(Operation operation, String companyPartyId, String facilityId, Target target) {
        public Request(Operation operation,String companyPartyId,String facilityId) {
            this(operation,companyPartyId,facilityId,Target.STATION);
        }
        public String resourceType() { return target == Target.STATION ? "STATION_GRAPH_REVISION" : "TOPOLOGY_FOUNDATION"; }
        public boolean companyTarget() { return target == Target.FOUNDATION_COMPANY; }
        public Request {
            if (operation == null || target == null) {
                throw new IllegalArgumentException("Operation must not be null.");
            }
            companyPartyId = requireReference(companyPartyId, "companyPartyId");
            facilityId = requireReference(facilityId, "facilityId");
            if (target != Target.STATION && operation != Operation.AUTHOR && operation != Operation.READ_CURRENT || (target == Target.FOUNDATION_COMPANY && !companyPartyId.equals(facilityId)))
                throw new IllegalArgumentException("Foundation target and permission mismatch");
        }

        private static String requireReference(String value, String field) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(field + " must not be blank.");
            }
            return value.trim();
        }
    }
}
