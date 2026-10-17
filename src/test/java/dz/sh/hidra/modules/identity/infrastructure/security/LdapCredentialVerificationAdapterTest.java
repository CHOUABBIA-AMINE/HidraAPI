/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * LdapCredentialVerificationAdapterTest @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-14 @Type :
 * Class @Layer : Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.security @Description : Verifies LDAP search escaping
 * and stable directory identity attribute normalization without external authorization mapping.
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties;

import org.junit.jupiter.api.Test;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

class LdapCredentialVerificationAdapterTest {

    @Test
    void callbackAttributeFailureCannotReturnAnIdentityOrExposeNamingDetails() throws Exception {
        var adapter = adapter("(uid={0})");
        var template = mock(org.springframework.ldap.core.LdapTemplate.class);
        org.springframework.test.util.ReflectionTestUtils.setField(
                adapter, "ldapTemplate", template);
        var context = mock(javax.naming.directory.DirContext.class);
        org.mockito.Mockito.when(
                        context.getAttributes(
                                org.mockito.ArgumentMatchers.any(javax.naming.Name.class),
                                org.mockito.ArgumentMatchers.any(String[].class)))
                .thenThrow(new javax.naming.NamingException("private-directory-value"));
        org.mockito.Mockito.when(
                        template.authenticate(
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.any(
                                        org.springframework.ldap.core
                                                .AuthenticatedLdapEntryContextCallback.class)))
                .thenAnswer(
                        i -> {
                            ((org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)
                                            i.getArgument(3))
                                    .executeWithContext(
                                            context,
                                            new org.springframework.ldap.core
                                                    .LdapEntryIdentification(
                                                    new javax.naming.ldap.LdapName(
                                                            "uid=operator,dc=synthetic"),
                                                    new javax.naming.ldap.LdapName(
                                                            "uid=operator")));
                            return true;
                        });
        assertThatThrownBy(() -> adapter.verify("operator", "synthetic-password"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Authenticated directory identity is unavailable.")
                .hasNoCause();
    }

    @Test
    void escapesSubmittedPrincipalBeforeBuildingDirectorySearchFilter() throws Exception {
        String submittedPrincipal = "ops*)(uid=*)";
        LdapCredentialVerificationAdapter adapter = adapter("(&(objectClass=person)(uid={0}))");

        String filter = invokeUserFilter(adapter, submittedPrincipal);

        assertThat(filter)
                .isEqualTo(
                        "(&(objectClass=person)(uid="
                                + LdapEncoder.filterEncode(submittedPrincipal)
                                + "))");
        assertThat(filter).doesNotContain("uid=*))(");
    }

    @Test
    void rejectsSearchFilterWithoutPrincipalPlaceholder() {
        LdapCredentialVerificationAdapter adapter = adapter("(uid=static-user)");

        assertThatThrownBy(() -> invokeUserFilter(adapter, "operator"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("LDAP user search filter must contain the {0} principal placeholder.");
    }

    @Test
    void normalizesActiveDirectoryAttributesToStableObjectGuidSubject() throws Exception {
        byte[] objectGuid = new byte[] {1, 2, 3, 4};
        BasicAttributes attributes = new BasicAttributes(true);
        attributes.put(new BasicAttribute("objectGUID", objectGuid));
        attributes.put(new BasicAttribute("userPrincipalName", " operator@example.invalid "));
        attributes.put(new BasicAttribute("sAMAccountName", "operator"));
        attributes.put(new BasicAttribute("displayName", " Directory Operator "));
        attributes.put(new BasicAttribute("mail", " operator@example.invalid "));
        attributes.put(
                new BasicAttribute(
                        "distinguishedName", " CN=Operator,OU=Users,DC=example,DC=invalid "));

        Object entry = invokeMapEntry(attributes);

        assertThat(invokeAccessor(entry, "objectGuid"))
                .isEqualTo("objectGUID:" + Base64.getEncoder().encodeToString(objectGuid));
        assertThat(invokeAccessor(entry, "userPrincipalName"))
                .isEqualTo(" operator@example.invalid ");
        assertThat(invokeAccessor(entry, "samAccountName")).isEqualTo("operator");
        assertThat(invokeAccessor(entry, "displayName")).isEqualTo("Directory Operator");
        assertThat(invokeAccessor(entry, "mail")).isEqualTo("operator@example.invalid");
        assertThat(invokeAccessor(entry, "distinguishedName"))
                .isEqualTo(" CN=Operator,OU=Users,DC=example,DC=invalid ");
    }

    @Test
    void blankPrincipalOrCredentialsFailClosedBeforeDirectoryAccess() {
        LdapCredentialVerificationAdapter adapter = adapter("(uid={0})");

        Optional<?> blankPrincipal = adapter.verify("   ", "secret");
        Optional<?> missingCredentials = adapter.verify("operator", "");

        assertThat(blankPrincipal).isEmpty();
        assertThat(missingCredentials).isEmpty();
    }

    @Test
    void authenticatedBindCallbackTransportsExactEntrySubjectRatherThanEarlierProfileSearch()
            throws Exception {
        var adapter = adapter("(uid={0})");
        var template = mock(org.springframework.ldap.core.LdapTemplate.class);
        org.springframework.test.util.ReflectionTestUtils.setField(
                adapter, "ldapTemplate", template);
        var context = mock(javax.naming.directory.DirContext.class);
        var attrs = new BasicAttributes(true);
        attrs.put(new BasicAttribute("entryUUID", "  exact-é-authenticated-entry  "));
        attrs.put(new BasicAttribute("uid", " operator "));
        org.mockito.Mockito.when(
                        context.getAttributes(
                                org.mockito.ArgumentMatchers.any(javax.naming.Name.class),
                                org.mockito.ArgumentMatchers.any(String[].class)))
                .thenReturn(attrs);
        org.mockito.Mockito.when(
                        template.authenticate(
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.any(
                                        org.springframework.ldap.core
                                                .AuthenticatedLdapEntryContextCallback.class)))
                .thenAnswer(
                        i -> {
                            ((org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)
                                            i.getArgument(3))
                                    .executeWithContext(
                                            context,
                                            new org.springframework.ldap.core
                                                    .LdapEntryIdentification(
                                                    new javax.naming.ldap.LdapName(
                                                            "uid=operator,dc=synthetic"),
                                                    new javax.naming.ldap.LdapName(
                                                            "uid=operator")));
                            return true;
                        });
        var verified = adapter.verify("operator", "synthetic-password").orElseThrow();
        assertThat(verified.subject()).isEqualTo("  exact-é-authenticated-entry  ");
        assertThat(verified.username()).isEqualTo("operator");
        org.mockito.Mockito.verify(context)
                .getAttributes(
                        new javax.naming.ldap.LdapName("uid=operator"),
                        new String[] {
                            "objectGUID",
                            "entryUUID",
                            "userPrincipalName",
                            "sAMAccountName",
                            "uid",
                            "displayName",
                            "cn",
                            "mail",
                            "distinguishedName"
                        });
    }

    @Test
    void successfulBindWithoutAuthenticatedEntryCannotManufactureAnIdentity() {
        var adapter = adapter("(uid={0})");
        var template = mock(org.springframework.ldap.core.LdapTemplate.class);
        org.springframework.test.util.ReflectionTestUtils.setField(
                adapter, "ldapTemplate", template);
        org.mockito.Mockito.when(
                        template.authenticate(
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.anyString(),
                                org.mockito.ArgumentMatchers.any(
                                        org.springframework.ldap.core
                                                .AuthenticatedLdapEntryContextCallback.class)))
                .thenReturn(true);
        assertThatThrownBy(() -> adapter.verify("operator", "synthetic-password"))
                .isInstanceOf(IllegalStateException.class);
    }

    private static LdapCredentialVerificationAdapter adapter(String filter) {
        HidraLdapSecurityProperties properties =
                new HidraLdapSecurityProperties(
                        true,
                        "ldap://directory.example.invalid:389",
                        "DC=example,DC=invalid",
                        "OU=Users",
                        filter,
                        null,
                        null,
                        Duration.ofSeconds(2),
                        Duration.ofSeconds(3));
        return new LdapCredentialVerificationAdapter(mock(LdapContextSource.class), properties);
    }

    private static String invokeUserFilter(
            LdapCredentialVerificationAdapter adapter, String principal) throws Exception {
        Method method =
                LdapCredentialVerificationAdapter.class.getDeclaredMethod(
                        "userFilter", String.class);
        method.setAccessible(true);
        try {
            return (String) method.invoke(adapter, principal);
        } catch (InvocationTargetException exception) {
            if (exception.getCause() instanceof Exception cause) {
                throw cause;
            }
            throw exception;
        }
    }

    private static Object invokeMapEntry(BasicAttributes attributes) throws Exception {
        Method method =
                LdapCredentialVerificationAdapter.class.getDeclaredMethod(
                        "mapEntry", javax.naming.directory.Attributes.class);
        method.setAccessible(true);
        return method.invoke(null, attributes);
    }

    private static Object invokeAccessor(Object entry, String accessor) throws Exception {
        Method method = entry.getClass().getDeclaredMethod(accessor);
        method.setAccessible(true);
        return method.invoke(entry);
    }
}
