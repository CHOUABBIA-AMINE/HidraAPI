/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.value
 *
 * @Description : Stable party identifier.
 *
 */
package dz.sh.hidra.modules.party.domain.value;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;

import java.util.UUID;

/**
 * Stable party identifier.
 *
 * @param value identifier value
 */
public record PartyId(String value) {

    public PartyId {
        value = requireText(value, "Party ID must not be null or blank.");
    }

    public static PartyId of(String value) {
        return new PartyId(value);
    }

    public static PartyId newId() {
        return new PartyId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidPartyValueException(message);
        }
        return value.trim();
    }
}
