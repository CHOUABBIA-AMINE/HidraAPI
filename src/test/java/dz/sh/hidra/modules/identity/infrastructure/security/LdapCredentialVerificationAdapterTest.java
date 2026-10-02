/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapCredentialVerificationAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies LDAP search escaping and stable directory identity attribute normalization without external authorization mapping.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import org.junit.jupiter.api.Test;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapEncoder;

class LdapCredentialVerificationAdapterTest {

    @Test
    void escapesSubmittedPrincipalBeforeBuildingDirectorySearchFilter() throws Exception {
        String submittedPrincipal = "ops*)(uid=*)";
        LdapCredentialVerificationAdapter adapter = adapter("(&(objectClass=person)(uid={0}))");

        String filter = invokeUserFilter(adapter, submittedPrincipal);

        assertThat(filter).isEqualTo(
                "(&(objectClass=person)(uid=" + LdapEncoder.filterEncode(submittedPrincipal) + "))"
        );
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
        attributes.put(new BasicAttribute("distinguishedName", " CN=Operator,OU=Users,DC=example,DC=invalid "));

        Object entry = invokeMapEntry(attributes);

        assertThat(invokeAccessor(entry, "objectGuid"))
                .isEqualTo("objectGUID:" + Base64.getEncoder().encodeToString(objectGuid));
        assertThat(invokeAccessor(entry, "userPrincipalName")).isEqualTo("operator@example.invalid");
        assertThat(invokeAccessor(entry, "samAccountName")).isEqualTo("operator");
        assertThat(invokeAccessor(entry, "displayName")).isEqualTo("Directory Operator");
        assertThat(invokeAccessor(entry, "mail")).isEqualTo("operator@example.invalid");
        assertThat(invokeAccessor(entry, "distinguishedName"))
                .isEqualTo("CN=Operator,OU=Users,DC=example,DC=invalid");
    }

    @Test
    void blankPrincipalOrCredentialsFailClosedBeforeDirectoryAccess() {
        LdapCredentialVerificationAdapter adapter = adapter("(uid={0})");

        Optional<?> blankPrincipal = adapter.verify("   ", "secret");
        Optional<?> missingCredentials = adapter.verify("operator", "");

        assertThat(blankPrincipal).isEmpty();
        assertThat(missingCredentials).isEmpty();
    }

    private static LdapCredentialVerificationAdapter adapter(String filter) {
        HidraLdapSecurityProperties properties = new HidraLdapSecurityProperties(
                true,
                "ldap://directory.example.invalid:389",
                "DC=example,DC=invalid",
                "OU=Users",
                filter,
                null,
                null,
                Duration.ofSeconds(2),
                Duration.ofSeconds(3)
        );
        return new LdapCredentialVerificationAdapter(mock(LdapContextSource.class), properties);
    }

    private static String invokeUserFilter(LdapCredentialVerificationAdapter adapter, String principal) throws Exception {
        Method method = LdapCredentialVerificationAdapter.class.getDeclaredMethod("userFilter", String.class);
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
        Method method = LdapCredentialVerificationAdapter.class.getDeclaredMethod(
                "mapEntry",
                javax.naming.directory.Attributes.class
        );
        method.setAccessible(true);
        return method.invoke(null, attributes);
    }

    private static Object invokeAccessor(Object entry, String accessor) throws Exception {
        Method method = entry.getClass().getDeclaredMethod(accessor);
        method.setAccessible(true);
        return method.invoke(entry);
    }
}
