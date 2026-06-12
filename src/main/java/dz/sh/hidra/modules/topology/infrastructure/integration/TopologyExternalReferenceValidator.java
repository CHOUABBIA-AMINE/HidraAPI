/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyExternalReferenceValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Validates topology-adjacent neutral references.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

public interface TopologyExternalReferenceValidator { boolean partyExists(String partyId); }
