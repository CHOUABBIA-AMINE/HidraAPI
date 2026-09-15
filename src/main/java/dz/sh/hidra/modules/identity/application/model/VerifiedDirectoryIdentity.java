/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : VerifiedDirectoryIdentity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Carries provider-neutral directory identity attributes after successful external credential verification.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

/**
 * Verified external directory identity without Hidra authorization meaning.
 */
public record VerifiedDirectoryIdentity(
        String subject,
        String username,
        String displayName,
        String emailAddress,
        String distinguishedName
) {

    public VerifiedDirectoryIdentity {
        subject = require(subject, "subject");
        username = require(username, "username");
        displayName = normalize(displayName);
        emailAddress = normalize(emailAddress);
        distinguishedName = normalize(distinguishedName);
    }

    private static String require(String value, String field) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(field + " must not be blank.");
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
