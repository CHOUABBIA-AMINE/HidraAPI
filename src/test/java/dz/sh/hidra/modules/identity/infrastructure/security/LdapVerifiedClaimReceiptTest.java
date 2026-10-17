/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * LdapVerifiedClaimReceiptTest @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type : Class @Layer
 * : Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.security @Description : Verifies directory callback
 * provenance with simulated transport and bounded observations.
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.model.*;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.*;
import dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties;
import dz.sh.hidra.platform.security.LdapAuthenticationToken;

import org.junit.jupiter.api.*;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;

/**
 * No network/real LDAP server proof: the actual provider/adapter execute a simulated bind callback.
 */
class LdapVerifiedClaimReceiptTest {
    private final List<TrustedAuthenticationVerificationRegistry> owners = new ArrayList<>();

    @AfterEach
    void cleanup() {
        owners.forEach(TrustedAuthenticationVerificationRegistry::close);
        TransactionSynchronizationManager.clear();
    }

    private record Fixture(
            TrustedAuthenticationVerificationRegistry owner,
            LdapAuthenticationProvider provider,
            LdapCredentialVerificationAdapter adapter,
            LdapTemplate transport,
            DirContext context,
            BasicAttributes attributes,
            ClaimNameCatalogPort catalog,
            BoundSourceClaimIssuanceFencePort fence) {}

    private Fixture fixture(ProviderType type, boolean enabled) throws Exception {
        var fence = mock(BoundSourceClaimIssuanceFencePort.class);
        var owner =
                new TrustedAuthenticationVerificationRegistry(
                        "synthetic-directory-receipt-key-at-least-32-bytes",
                        "hidra-api",
                        "hidra-api",
                        fence,
                        mock(BoundSourceClaimEvidenceRepositoryPort.class));
        owners.add(owner);
        var properties =
                new HidraLdapSecurityProperties(
                        true,
                        "ldap://synthetic.invalid",
                        "dc=synthetic",
                        "ou=people",
                        "(uid={0})",
                        null,
                        null,
                        null,
                        null);
        var adapter =
                new LdapCredentialVerificationAdapter(
                        mock(LdapContextSource.class), properties, owner);
        var transport = mock(LdapTemplate.class);
        ReflectionTestUtils.setField(adapter, "ldapTemplate", transport);
        var context = mock(DirContext.class);
        var attrs = new BasicAttributes(true);
        attrs.put("entryUUID", " exact-directory-subject ");
        attrs.put("uid", "operator");
        attrs.put("memberOf", " exact-group-é ");
        when(context.getAttributes(any(Name.class), any(String[].class))).thenReturn(attrs);
        when(transport.authenticate(
                        anyString(),
                        anyString(),
                        anyString(),
                        any(AuthenticatedLdapEntryContextCallback.class)))
                .thenAnswer(
                        i -> {
                            ((AuthenticatedLdapEntryContextCallback) i.getArgument(3))
                                    .executeWithContext(
                                            context,
                                            new LdapEntryIdentification(
                                                    new LdapName(
                                                            "uid=operator,ou=people,dc=synthetic"),
                                                    new LdapName("uid=operator,ou=people")));
                            return true;
                        });
        var providers = mock(IdentityProviderJpaRepository.class);
        var p = mock(IdentityProviderJpaEntity.class);
        when(p.id()).thenReturn("provider");
        when(p.providerType()).thenReturn(type);
        when(p.status()).thenReturn(IdentityProviderStatus.ACTIVE);
        when(providers.findAll()).thenReturn(List.of(p));
        var links = mock(ExternalIdentityJpaRepository.class);
        var link = mock(ExternalIdentityJpaEntity.class);
        when(link.id()).thenReturn("link");
        when(link.userId()).thenReturn("user");
        when(link.status()).thenReturn(ExternalIdentityStatus.LINKED);
        when(links.findByIdentityProviderIdAndExternalSubject(
                        "provider", " exact-directory-subject "))
                .thenReturn(Optional.of(link));
        var users = mock(UserJpaRepository.class);
        var user = mock(UserJpaEntity.class);
        when(user.id()).thenReturn("user");
        when(user.username()).thenReturn("operator");
        when(user.displayName()).thenReturn("Operator");
        when(user.status()).thenReturn(UserStatus.ACTIVE);
        when(users.findById("user")).thenReturn(Optional.of(user));
        var query = mock(IdentityAdministrationQueryUseCase.class);
        var view = mock(IdentityAdministrationQueryUseCase.PrincipalView.class);
        when(view.effectivePermissions()).thenReturn(List.of("read"));
        when(query.principal("user", List.of())).thenReturn(view);
        var states = mock(AuthenticationSecurityStateQueryPort.class);
        var providerState =
                new AuthenticationSecurityStateQueryPort.ProviderState(
                        "provider",
                        type.name(),
                        1,
                        null,
                        properties.baseDn(),
                        properties.userSearchBase());
        when(states.provider("provider", type.name())).thenReturn(providerState);
        when(states.external(eq(providerState), anyString(), isNull(), isNull()))
                .thenAnswer(
                        i ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        type.name(),
                                        "provider",
                                        1,
                                        "link",
                                        i.getArgument(1),
                                        1L,
                                        null,
                                        null,
                                        null,
                                        null));
        var catalog = mock(ClaimNameCatalogPort.class);
        when(catalog.selectVersioned("provider")).thenReturn(selection("memberOf", "missing"));
        when(fence.prepare(eq("provider"), any()))
                .thenReturn(new BoundSourceClaimIssuanceFencePort.FenceStamp(11, 42));
        var provider =
                new LdapAuthenticationProvider(
                        adapter,
                        providers,
                        links,
                        users,
                        query,
                        states,
                        owner,
                        properties,
                        catalog,
                        enabled,
                        60);
        owner.begin();
        return new Fixture(owner, provider, adapter, transport, context, attrs, catalog, fence);
    }

    private static ClaimNameCatalogPort.VersionedSelection selection(String... names) {
        return new ClaimNameCatalogPort.VersionedSelection(
                new ClaimNameCatalogPort.Selection(List.of(names)), 1);
    }

    private static Authentication authenticate(Fixture f) {
        return f.provider.authenticate(
                LdapAuthenticationToken.unauthenticated("operator", "synthetic-password"));
    }

    private static TrustedAuthenticationVerificationPort.Verification consume(
            Fixture f, Authentication token) {
        var p = (HidraPrincipal) token.getPrincipal();
        return f.owner.consume(
                new AuthenticatedPrincipalInput(
                        p.userId(),
                        p.username(),
                        p.displayName(),
                        p.authenticationType(),
                        p.identityProviderId(),
                        p.roles(),
                        p.permissions(),
                        p.externalIdentityId(),
                        f.owner.verifiedHandle(token)));
    }

    @Test
    void genuineCallbackTransfersBothDirectoryTypesAndSelectsBeforeBind() throws Exception {
        for (var type : List.of(ProviderType.LDAP, ProviderType.ACTIVE_DIRECTORY)) {
            var f = fixture(type, true);
            var token = authenticate(f);
            var order = inOrder(f.catalog, f.transport);
            order.verify(f.catalog).selectVersioned("provider");
            order.verify(f.transport)
                    .authenticate(
                            anyString(),
                            anyString(),
                            anyString(),
                            any(AuthenticatedLdapEntryContextCallback.class));
            verify(f.context, times(1))
                    .getAttributes(
                            eq(new LdapName("uid=operator,ou=people")),
                            argThat(
                                    names ->
                                            Set.of(names)
                                                    .containsAll(
                                                            List.of(
                                                                    "entryUUID",
                                                                    "memberOf",
                                                                    "missing"))));
            var v = consume(f, token);
            assertThat(v.snapshot().authenticationType()).isEqualTo(type.name());
            assertThat(v.snapshot().sourceExpiresAt()).isNull();
            TransactionSynchronizationManager.setActualTransactionActive(true);
            TransactionSynchronizationManager.initSynchronization();
            assertThat(v.prepareSourceClaims()).isTrue();
            verify(f.fence).prepare("provider", selection("memberOf", "missing"));
            TransactionSynchronizationManager.clear();
            f.owner.close();
        }
    }

    @Test
    void defaultOffDoesNotSelectOrRequestClaims() throws Exception {
        var f = fixture(ProviderType.LDAP, false);
        var token = authenticate(f);
        verifyNoInteractions(f.catalog, f.fence);
        assertThat(consume(f, token).prepareSourceClaims()).isFalse();
        verify(f.context)
                .getAttributes(
                        any(Name.class), argThat(names -> !List.of(names).contains("memberOf")));
    }

    @Test
    void callbackAbsenceFalseResultAndDuplicateCallbackCannotMintReceipt() throws Exception {
        for (int mode = 0; mode < 3; mode++) {
            var f = fixture(ProviderType.LDAP, true);
            final int current = mode;
            when(f.transport.authenticate(
                            anyString(),
                            anyString(),
                            anyString(),
                            any(AuthenticatedLdapEntryContextCallback.class)))
                    .thenAnswer(
                            i -> {
                                var cb = (AuthenticatedLdapEntryContextCallback) i.getArgument(3);
                                var entry =
                                        new LdapEntryIdentification(
                                                new LdapName("uid=operator,dc=synthetic"),
                                                new LdapName("uid=operator"));
                                if (current != 0) cb.executeWithContext(f.context, entry);
                                if (current == 2) {
                                    try {
                                        cb.executeWithContext(f.context, entry);
                                    } catch (RuntimeException deliberatelySuppressedByTransport) {
                                    }
                                }
                                return current != 1;
                            });
            assertThatThrownBy(() -> authenticate(f))
                    .isInstanceOf(
                            org.springframework.security.authentication.BadCredentialsException
                                    .class);
            assertThat(f.owner.directoryCaptureSelection()).isNull();
            f.owner.close();
        }
    }

    @Test
    void attributeReadAndSelectionFailuresStayRedactedAndClearScope() throws Exception {
        var f = fixture(ProviderType.LDAP, true);
        when(f.context.getAttributes(any(Name.class), any(String[].class)))
                .thenThrow(new NamingException("private-source-secret"));
        assertThatThrownBy(() -> authenticate(f))
                .hasMessage("Directory verified claim evidence unavailable.")
                .hasNoCause();
        assertThat(f.owner.directoryCaptureSelection()).isNull();
        var next = fixture(ProviderType.LDAP, true);
        when(next.catalog.selectVersioned("provider"))
                .thenThrow(new IllegalStateException("private-source-secret"));
        assertThatThrownBy(() -> authenticate(next))
                .hasMessage("Directory verified claim evidence unavailable.")
                .hasNoCause();
        verifyNoInteractions(next.transport);
    }

    @Test
    void existingOwnerTransactionAndUnsupportedNamesRefuseBeforeBind() throws Exception {
        var f = fixture(ProviderType.LDAP, true);
        TransactionSynchronizationManager.setActualTransactionActive(true);
        assertThatThrownBy(() -> authenticate(f))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        verifyNoInteractions(f.catalog, f.transport);
        TransactionSynchronizationManager.clear();
        when(f.catalog.selectVersioned("provider")).thenReturn(selection("realm.groups"));
        assertThatThrownBy(() -> authenticate(f))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        verifyNoInteractions(f.transport);
    }

    @Test
    void noScopeAndCopiedIdentityCannotManufactureProducerAuthority() throws Exception {
        var f = fixture(ProviderType.LDAP, true);
        assertThatThrownBy(
                        () ->
                                f.owner.captureDirectory(
                                        new VerifiedDirectoryIdentity("s", "u", null, null, null),
                                        new LdapClaimCoverageProjector.Projection(
                                                ClaimCoverage.empty(), 1)))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        var scope = f.owner.beginDirectoryCapture("provider", "LDAP", selection(), 60);
        var identity = f.adapter.verify("operator", "synthetic-password").orElseThrow();
        var snapshot =
                new AuthenticationVerificationSnapshot(
                        "user",
                        "LDAP",
                        "provider",
                        1,
                        "link",
                        identity.subject(),
                        1L,
                        null,
                        null,
                        null,
                        null);
        var p =
                new HidraPrincipal(
                        "user",
                        "operator",
                        "Operator",
                        ProviderType.LDAP,
                        "provider",
                        Set.of(),
                        Set.of("read"),
                        "link");
        var token =
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken
                        .authenticated(p, null, List.of());
        var copy =
                new VerifiedDirectoryIdentity(
                        identity.subject(),
                        identity.username(),
                        identity.displayName(),
                        identity.emailAddress(),
                        identity.distinguishedName());
        assertThatThrownBy(() -> f.owner.verifiedDirect(token, snapshot, copy))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        assertThatThrownBy(() -> f.owner.verifiedDirect(token, snapshot, identity))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        scope.close();
    }

    @Test
    void scopeRejectsNestedWrongThreadExpiredAndClosedUse() throws Exception {
        var f = fixture(ProviderType.LDAP, true);
        var scope = f.owner.beginDirectoryCapture("provider", "LDAP", selection(), 60);
        assertThatThrownBy(() -> f.owner.beginDirectoryCapture("provider", "LDAP", selection(), 60))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        try (var pool = Executors.newSingleThreadExecutor()) {
            pool.submit(
                            () ->
                                    assertThatThrownBy(scope::close)
                                            .isInstanceOf(
                                                    org.springframework.security.authentication
                                                            .BadCredentialsException.class))
                    .get(3, TimeUnit.SECONDS);
        }
        ReflectionTestUtils.setField(scope, "deadline", Instant.EPOCH);
        assertThatThrownBy(f.owner::directoryCaptureSelection)
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
        scope.close();
        assertThat(f.owner.directoryCaptureSelection()).isNull();
        f.owner.close();
        assertThatThrownBy(() -> f.owner.beginDirectoryCapture("provider", "LDAP", selection(), 60))
                .isInstanceOf(
                        org.springframework.security.authentication.BadCredentialsException.class);
    }

    @Test
    void valuesAreExactCaseInsensitiveObservationsAndOmissionsAreUnknown() {
        var attrs = new BasicAttributes(false);
        var groups = new BasicAttribute("MEMBEROF");
        groups.add(" group-é ");
        groups.add("CN=Other,OU=Ops");
        attrs.put(groups);
        attrs.put("unselected", "private-unselected");
        var projection =
                LdapClaimCoverageProjector.project(attrs, selection("memberOf", "missing"));
        assertThat(projection.coverage().values("memberOf"))
                .containsExactlyInAnyOrder(" group-é ", "CN=Other,OU=Ops");
        assertThat(projection.coverage().state("missing")).isEqualTo(ClaimCoverage.State.UNKNOWN);
        assertThat(projection.coverage().entries()).doesNotContainKey("unselected");
        assertThat(projection.toString()).doesNotContain("group-é");
    }

    @Test
    void rangeAndOptionVariantsDiscardEvenAnOtherwisePresentValue() {
        var attrs = new BasicAttributes(false);
        attrs.put("memberOf", "must-not-retain");
        attrs.put("memberOf;range=0-1499", "partial");
        assertThat(
                        LdapClaimCoverageProjector.project(attrs, selection("memberOf"))
                                .coverage()
                                .state("memberOf"))
                .isEqualTo(ClaimCoverage.State.UNKNOWN);
        attrs.remove("memberOf;range=0-1499");
        attrs.put("memberOf;binary", new byte[] {1});
        assertThat(
                        LdapClaimCoverageProjector.project(attrs, selection("memberOf"))
                                .coverage()
                                .values("memberOf"))
                .isEmpty();
    }

    @Test
    void malformedTypesEmptyAndInvalidUnicodeAreNotStringCoerced() {
        for (Object raw : List.of(new byte[] {1}, 42, true, " ", "bad\u0000text", "bad\ud800")) {
            var attrs = new BasicAttributes();
            attrs.put("groups", raw);
            assertThat(
                            LdapClaimCoverageProjector.project(attrs, selection("groups"))
                                    .coverage()
                                    .state("groups"))
                    .isEqualTo(ClaimCoverage.State.MALFORMED);
        }
        var attrs = new BasicAttributes();
        attrs.put(new BasicAttribute("groups"));
        assertThat(
                        LdapClaimCoverageProjector.project(attrs, selection("groups"))
                                .coverage()
                                .state("groups"))
                .isEqualTo(ClaimCoverage.State.MALFORMED);
    }

    @Test
    void namesAliasesAndOidEnvelopeAreCheckedBeforeProjection() {
        for (String name :
                List.of("*", "+", "realm.groups", "memberOf;binary", "1.02.3", "0", "01.2"))
            assertThatThrownBy(
                            () ->
                                    LdapClaimCoverageProjector.project(
                                            new BasicAttributes(), selection(name)))
                    .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                LdapClaimCoverageProjector.validateSelection(
                                        selection("memberOf", "MEMBEROF")))
                .isInstanceOf(IllegalArgumentException.class);
        var attrs = new BasicAttributes(false);
        attrs.put("groups", "one");
        attrs.put("GROUPS", "two");
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(
                        LdapClaimCoverageProjector.project(
                                        new BasicAttributes(), selection("1.2.840.113556"))
                                .coverage()
                                .state("1.2.840.113556"))
                .isEqualTo(ClaimCoverage.State.UNKNOWN);
    }

    @Test
    void rawCountsDuplicateValuesAggregateAndReturnedIdentifierLimitsReject() {
        var attrs = new BasicAttributes();
        var values = new BasicAttribute("groups", true);
        for (int i = 0; i < 257; i++) values.add("v" + i);
        attrs.put(values);
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .isInstanceOf(IllegalArgumentException.class);
        values.clear();
        values.add("same");
        values.add("same");
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .isInstanceOf(IllegalArgumentException.class);
        values.clear();
        values.add("x".repeat(1025));
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .isInstanceOf(IllegalArgumentException.class);
        values.clear();
        for (int i = 0; i < 17; i++) values.add("x".repeat(1020) + i);
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .isInstanceOf(IllegalArgumentException.class);
        var tooMany = new BasicAttributes();
        for (int i = 0; i < 147; i++) tooMany.put("unselected" + i, "x");
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(tooMany, selection()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(
                        LdapClaimCoverageProjector.project(new BasicAttributes(), selection())
                                .coverage())
                .isEqualTo(ClaimCoverage.empty());
    }

    @Test
    void namingEnumerationFailureClosesItAndDoesNotDiscloseSourceException() throws Exception {
        var attrs = mock(Attributes.class);
        NamingEnumeration<Attribute> enumeration = mock(NamingEnumeration.class);
        when(attrs.getAll()).thenReturn(enumeration);
        when(enumeration.hasMore()).thenThrow(new NamingException("private-source-secret"));
        assertThatThrownBy(() -> LdapClaimCoverageProjector.project(attrs, selection("groups")))
                .hasMessage("Invalid bounded directory claim projection")
                .hasNoCause();
        verify(enumeration).close();
    }
}
