/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * LdapCredentialVerificationAdapter @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Class @Layer : Infrastructure @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.security @Description : Verifies LDAP/Active
 * Directory credentials and returns normalized external directory identity attributes.
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

/** Spring LDAP implementation of the directory credential-verification port. */
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
    private final TrustedAuthenticationVerificationRegistry verification;
    private final HidraLdapSecurityProperties properties;

    public LdapCredentialVerificationAdapter(
            LdapContextSource contextSource, HidraLdapSecurityProperties properties) {
        this(contextSource, properties, null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public LdapCredentialVerificationAdapter(
            LdapContextSource contextSource,
            HidraLdapSecurityProperties properties,
            TrustedAuthenticationVerificationRegistry verification) {
        this.verification = verification;
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
        var selected = verification == null ? null : verification.directoryCaptureSelection();
        String[] requested = IDENTITY_ATTRIBUTES;
        if (selected != null) {
            java.util.Map<String, String> names = new java.util.LinkedHashMap<>();
            for (String name : IDENTITY_ATTRIBUTES)
                names.put(name.toLowerCase(java.util.Locale.ROOT), name);
            for (String name : selected.selection().names())
                names.putIfAbsent(name.toLowerCase(java.util.Locale.ROOT), name);
            requested = names.values().toArray(String[]::new);
        }
        final String[] attributesToRead = requested;
        java.util.concurrent.atomic.AtomicReference<VerifiedDirectoryIdentity> authenticated =
                new java.util.concurrent.atomic.AtomicReference<>();
        var callbackAttempts = new java.util.concurrent.atomic.AtomicInteger();
        boolean success =
                ldapTemplate.authenticate(
                        searchBase(),
                        filter,
                        credentials,
                        (org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)
                                (context, entry) -> {
                                    try {
                                        if (callbackAttempts.incrementAndGet() != 1
                                                || authenticated.get() != null) throw unavailable();
                                        Attributes attributes =
                                                context.getAttributes(
                                                        entry.getRelativeName(), attributesToRead);
                                        VerifiedDirectoryIdentity identity =
                                                identity(mapEntry(attributes), normalizedPrincipal);
                                        if (selected != null)
                                            verification.captureDirectory(
                                                    identity,
                                                    LdapClaimCoverageProjector.project(
                                                            attributes, selected));
                                        if (!authenticated.compareAndSet(null, identity))
                                            throw unavailable();
                                    } catch (NamingException | RuntimeException failure) {
                                        throw unavailable();
                                    }
                                });
        if (!success) return Optional.empty();
        VerifiedDirectoryIdentity result = authenticated.get();
        if (result == null || callbackAttempts.get() != 1) throw unavailable();
        return Optional.of(result);
    }

    private static VerifiedDirectoryIdentity identity(DirectoryEntry entry, String principal) {
        String username =
                firstNonBlank(
                        entry.userPrincipalName(), entry.samAccountName(), entry.uid(), principal);
        String subject =
                firstExact(
                        entry.objectGuid(),
                        entry.entryUuid(),
                        entry.userPrincipalName(),
                        entry.distinguishedName());
        if (subject == null) throw unavailable();
        return new VerifiedDirectoryIdentity(
                subject,
                username,
                firstNonBlank(entry.displayName(), entry.commonName()),
                entry.mail(),
                entry.distinguishedName());
    }

    private static IllegalStateException unavailable() {
        return new IllegalStateException("Authenticated directory identity is unavailable.");
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
