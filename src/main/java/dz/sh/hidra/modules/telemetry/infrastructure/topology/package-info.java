/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : package-info
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : PackageInfo
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.topology
 *
 * @Description : Telemetry infrastructure adapters for neutral topology lookups.
 *
 */
/**
 * Telemetry infrastructure adapters for neutral topology lookups.
 *
 * <p>Responsibility:
 * This package contains cross-module infrastructure adapters that validate telemetry topology asset
 * references through topology application ports.
 *
 * <p>Boundary rules:
 * Adapters in this package may depend on topology application read ports and value identifiers only
 * to translate a neutral telemetry reference into a read-only topology lookup. They must not import
 * topology persistence entities, topology repositories, topology mappers, topology REST controllers,
 * or topology domain aggregates.
 */
package dz.sh.hidra.modules.telemetry.infrastructure.topology;
