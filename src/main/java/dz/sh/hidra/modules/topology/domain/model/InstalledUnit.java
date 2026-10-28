/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnit
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Defines an independently identified physical unit and explicit evidence classifications.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;

/** Physical identity, not a topology station position or Assets maintenance aggregate. */
public record InstalledUnit(
        String id, String companyPartyId, String equipmentKindId,
        Evidence manufacturerParty, Evidence manufacturerModel, Evidence serialNumber,
        String maintainableAssetId) {
    public enum Verification { UNKNOWN, UNVERIFIED, VERIFIED }

    /** Supplied evidence classification is not Party authority or engineering qualification. */
    public record Evidence(String value, Verification verification, String sourceReference) {
        public Evidence {
            if (verification == null) throw invalid("Evidence classification required.");
            value = optional(value);
            sourceReference = optional(sourceReference);
            if (verification == Verification.UNKNOWN) {
                if (value != null || sourceReference != null)
                    throw invalid("Unknown evidence cannot carry an invented value or source.");
            } else if (value == null || sourceReference == null) {
                throw invalid("Known evidence requires both a value and provenance.");
            }
        }
        public static Evidence unknown() { return new Evidence(null, Verification.UNKNOWN, null); }
        public boolean isKnown() { return verification != Verification.UNKNOWN; }
    }

    public InstalledUnit {
        id = required(id, "Physical unit id");
        companyPartyId = required(companyPartyId, "Company Party id");
        equipmentKindId = required(equipmentKindId, "Equipment kind id");
        maintainableAssetId = optional(maintainableAssetId);
        if (manufacturerParty == null || manufacturerModel == null || serialNumber == null)
            throw invalid("Every evidence classification is required.");
        if (manufacturerModel.isKnown() && !manufacturerParty.isKnown())
            throw invalid("Known model requires an identified manufacturer.");
        if (manufacturerModel.verification() == Verification.VERIFIED
                && manufacturerParty.verification() != Verification.VERIFIED)
            throw invalid("Verified model classification requires verified manufacturer classification.");
    }

    private static String required(String value, String label) {
        String clean = optional(value);
        if (clean == null) throw invalid(label + " required.");
        return clean;
    }
    private static String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
