/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectionType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility enum for legacy callers; canonical state uses ConnectionTypeReference.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

/**
 * Legacy compatibility surface for the five historical connection-type codes.
 *
 * <p>Canonical TopologyConnection state and persistence use {@link ConnectionTypeReference}.
 * This enum must not be introduced into new domain or persistence contracts.</p>
 */
@Deprecated(forRemoval = false)
public enum ConnectionType {
    PIPELINE_SEGMENT,
    DIRECT_LINK,
    VIRTUAL_LINK,
    TRANSFER_LINK,
    MEASUREMENT_LINK
}
