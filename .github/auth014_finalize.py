from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-013 LDAP credential verification adapter completed |":
        "| Status | Active — AUTH-014 LDAP authentication provider completed |",
    "| AUTH-014 | `feat(authentication): add ldap authentication provider` | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | Planned |":
        "| AUTH-014 | `feat(authentication): add ldap authentication provider` | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | Completed — LdapAuthenticationProvider handles only LdapAuthenticationToken, delegates credential verification to AUTH-013, resolves one active LDAP/AD IdentityProvider and LINKED ExternalIdentity, enforces Hidra user state and Hidra-owned effective permissions, and returns HidraPrincipal without LOCAL fallback or AD-group authorization shortcuts; `mvn -q test` passed in PR CI run 167 |",
    """No LOCAL fallback and no AD-group authorization shortcut.\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-015 — Unified Hidra access-token issuer""":
        """No LOCAL fallback and no AD-group authorization shortcut.\n\nStatus:\n\n```text\nCompleted — LdapAuthenticationProvider is enabled only with LDAP infrastructure, supports only LdapAuthenticationToken, verifies submitted directory credentials through LdapCredentialVerificationPort, requires exactly one active LDAP or ACTIVE_DIRECTORY IdentityProvider, resolves the verified stable subject to a LINKED ExternalIdentity and Hidra User, enforces locked/non-ACTIVE Hidra account state, resolves existing Hidra-owned effective permissions, and returns HidraPrincipal. Directory groups are not promoted to Hidra authorization, no LOCAL fallback exists, and no token/session/login endpoint behavior from later tasks was added.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 167 completed repository and acceptance `mvn -q test` checks successfully for AUTH-014 implementation commit fcb0a011536ef38b316fa8dd69343db03493f9ba.\n```\n\n---\n\n### AUTH-015 — Unified Hidra access-token issuer""",
    """AUTH-014 — feat(authentication): add ldap authentication provider\n```\n\nAUTH-013 now provides isolated LDAP credential verification and a provider-neutral verified directory identity result. Do not implement AUTH-015 or later tasks during AUTH-014.""":
        """AUTH-015 — feat(authentication): add unified hidra access token issuer\n```\n\nAUTH-014 now normalizes successful LDAP/Active Directory authentication to HidraPrincipal using linked Hidra identity and authorization. Do not implement AUTH-016 or later tasks during AUTH-015."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
