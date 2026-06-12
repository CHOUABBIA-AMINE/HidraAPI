/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopTopologyExternalReferenceValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : No-op external reference validator.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

public class NoopTopologyExternalReferenceValidator implements TopologyExternalReferenceValidator { public boolean partyExists(String partyId) { return true; } }
