/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.value
 *
 * @Description : Normalized party business code.
 *
 */
package dz.sh.hidra.modules.party.domain.value;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;

import java.util.Locale;

/**
 * Normalized party business code.
 *
 * @param value normalized code
 */
public record PartyCode(String value) {

    public PartyCode {
        if (value == null || value.isBlank()) {
            throw new InvalidPartyValueException("Party code must not be null or blank.");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    public static PartyCode of(String value) {
        return new PartyCode(value);
    }
}
