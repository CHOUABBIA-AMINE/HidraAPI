/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GeoCoordinate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Geographical coordinate value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a physical geographical coordinate.
 *
 * <p>Business role:
 * This value object stores the latitude and longitude of a physical topology asset.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Latitude must be between -90 and 90. Longitude must be between -180 and 180.
 *
 * <p>Usage:
 * Use this type for optional physical location data on facilities, nodes, and appurtenances.
 *
 * @param latitude latitude in decimal degrees
 * @param longitude longitude in decimal degrees
 */
public record GeoCoordinate(double latitude, double longitude) implements ValueObject {

    public GeoCoordinate {
        if (Double.isNaN(latitude) || Double.isInfinite(latitude)) {
            throw new InvalidValueObjectException("GeoCoordinate latitude must be a finite number.");
        }

        if (Double.isNaN(longitude) || Double.isInfinite(longitude)) {
            throw new InvalidValueObjectException("GeoCoordinate longitude must be a finite number.");
        }

        if (latitude < -90.0 || latitude > 90.0) {
            throw new InvalidValueObjectException("GeoCoordinate latitude must be between -90 and 90.");
        }

        if (longitude < -180.0 || longitude > 180.0) {
            throw new InvalidValueObjectException("GeoCoordinate longitude must be between -180 and 180.");
        }
    }

    /**
     * Creates a coordinate value object.
     *
     * @param latitude latitude in decimal degrees
     * @param longitude longitude in decimal degrees
     * @return validated coordinate
     */
    public static GeoCoordinate of(double latitude, double longitude) {
        return new GeoCoordinate(latitude, longitude);
    }
}
