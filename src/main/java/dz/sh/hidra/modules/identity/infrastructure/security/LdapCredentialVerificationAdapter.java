/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapCredentialVerificationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies LDAP/Active Directory credentials and returns normalized external directory identity attributes.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.VerifiedDirectoryIdentity;
import dz.sh.hidra.modules.identity.application.port.out.LdapCredentialVerificationPort;
import dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * Spring LDAP implementation of the directory credential-verification port.
 */
@Component
@ConditionalOnProperty(
        prefix = "hidra.platform.security.ldap",
        name = "enabled",
        havingValue = "true")
public final class LdapCredentialVerificationAdapter implements LdapCredentialVerificationPort {

    private static final String USER_PLACEHOLDER = "{0}";
    private static final String[] IDENTITY_ATTRIBUTES = {
        "objectGUID",
        "entryUUID",
        "userPrincipalName",
        "sAMAccountName",
        "uid",
        "displayName",
        "cn",
        "mail",
        "distinguishedName"
    };

    private final LdapTemplate ldapTemplate;
    private final HidraLdapSecurityProperties properties;

    public LdapCredentialVerificationAdapter(
            LdapContextSource contextSource, HidraLdapSecurityProperties properties) {
        this.ldapTemplate = new LdapTemplate(Objects.requireNonNull(contextSource));
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    public Optional<VerifiedDirectoryIdentity> verify(String principal, String credentials) {
        String normalizedPrincipal = normalize(principal);
        if (normalizedPrincipal == null || credentials == null || credentials.isEmpty()) {
            return Optional.empty();
        }

        String filter = userFilter(normalizedPrincipal);
        // The callback's DN is the exact entry whose bind succeeded. A preceding profile search
        // cannot substitute another row's subject between search and authentication.
        java.util.concurrent.atomic.AtomicReference<DirectoryEntry> authenticated =
                new java.util.concurrent.atomic.AtomicReference<>();
        boolean success =
                ldapTemplate.authenticate(
                        searchBase(),
                        filter,
                        credentials,
                        (org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)
                                (context, entry) -> {
                                    try {
                                        authenticated.set(
                                                mapEntry(
                                                        context.getAttributes(
                                                                entry.getRelativeName(),
                                                                IDENTITY_ATTRIBUTES)));
                                    } catch (NamingException failure) {
                                        throw new IllegalStateException(
                                                "Authenticated directory identity is unavailable.");
                                    }
                                });
        if (!success) return Optional.empty();
        DirectoryEntry entry = authenticated.get();
        if (entry == null)
            throw new IllegalStateException("Authenticated directory identity is unavailable.");
        String username =
                firstNonBlank(
                        entry.userPrincipalName(),
                        entry.samAccountName(),
                        entry.uid(),
                        normalizedPrincipal);
        String subject =
                firstExact(
                        entry.objectGuid(),
                        entry.entryUuid(),
                        entry.userPrincipalName(),
                        entry.distinguishedName());
        if (subject == null) {
            throw new IllegalStateException(
                    "Verified LDAP identity does not expose a stable external subject.");
        }

        return Optional.of(
                new VerifiedDirectoryIdentity(
                        subject,
                        username,
                        firstNonBlank(entry.displayName(), entry.commonName()),
                        entry.mail(),
                        entry.distinguishedName()));
    }

    private String userFilter(String principal) {
        String configuredFilter = properties.userSearchFilter();
        if (configuredFilter == null || !configuredFilter.contains(USER_PLACEHOLDER)) {
            throw new IllegalStateException(
                    "LDAP user search filter must contain the {0} principal placeholder.");
        }
        return configuredFilter.replace(USER_PLACEHOLDER, LdapEncoder.filterEncode(principal));
    }

    private String searchBase() {
        return properties.userSearchBase() == null ? "" : properties.userSearchBase();
    }

    private static DirectoryEntry mapEntry(Attributes attributes) throws NamingException {
        return new DirectoryEntry(
                binarySubject(attributes.get("objectGUID")),
                exactValue(attributes.get("entryUUID")),
                exactValue(attributes.get("userPrincipalName")),
                stringValue(attributes.get("sAMAccountName")),
                stringValue(attributes.get("uid")),
                stringValue(attributes.get("displayName")),
                stringValue(attributes.get("cn")),
                stringValue(attributes.get("mail")),
                exactValue(attributes.get("distinguishedName")));
    }

    private static String binarySubject(Attribute attribute) throws NamingException {
        if (attribute == null) {
            return null;
        }
        Object value = attribute.get();
        if (value instanceof byte[] bytes) {
            return "objectGUID:" + Base64.getEncoder().encodeToString(bytes);
        }
        String text = value == null ? null : value.toString();
        return text == null ? null : "objectGUID:" + text;
    }

    private static String exactValue(Attribute attribute) throws NamingException {
        if (attribute == null) return null;
        Object value = attribute.get();
        return value instanceof String text && !text.isBlank() ? text : null;
    }

    private static String firstExact(String... values) {
        for (String value : values) if (value != null && !value.isBlank()) return value;
        return null;
    }

    private static String stringValue(Attribute attribute) throws NamingException {
        if (attribute == null) {
            return null;
        }
        Object value = attribute.get();
        return normalize(value == null ? null : value.toString());
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            String normalized = normalize(value);
            if (normalized != null) {
                return normalized;
            }
        }
        return null;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private record DirectoryEntry(
            String objectGuid,
            String entryUuid,
            String userPrincipalName,
            String samAccountName,
            String uid,
            String displayName,
            String commonName,
            String mail,
            String distinguishedName) {}
}
