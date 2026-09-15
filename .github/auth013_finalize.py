from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-012 LDAP security infrastructure completed |":
        "| Status | Active — AUTH-013 LDAP credential verification adapter completed |",
    "| AUTH-013 | `feat(authentication): add ldap credential verification adapter` | Implement AD/LDAP bind/search credential verification | Planned |":
        "| AUTH-013 | `feat(authentication): add ldap credential verification adapter` | Implement AD/LDAP bind/search credential verification | Completed — Identity application port plus provider-neutral VerifiedDirectoryIdentity contract and Spring LDAP adapter added; principal values are LDAP-filter escaped, search must resolve exactly one identity, credentials are verified by LDAP bind, and no Hidra permissions or AUTH-014 provider behavior is introduced; `mvn -q test` passed in PR CI run 160 |",
    """Return a provider-neutral verified directory identity result; do not assign Hidra permissions in the LDAP adapter.\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-014 — LDAP AuthenticationProvider""":
        """Return a provider-neutral verified directory identity result; do not assign Hidra permissions in the LDAP adapter.\n\nStatus:\n\n```text\nCompleted — LdapCredentialVerificationPort isolates the application layer from Spring LDAP and returns VerifiedDirectoryIdentity only after the configured LDAP user search resolves exactly one entry and LdapTemplate bind authentication succeeds. The supplied principal is escaped before filter substitution. The verified result carries a stable external subject plus normalized username/display/email/DN attributes, with AD objectGUID preferred and entryUUID/userPrincipalName/DN fallbacks. Submitted credentials are never returned, persisted, or logged. The adapter assigns no Hidra role/permission meaning and does not implement the AUTH-014 AuthenticationProvider.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 160 completed repository and acceptance `mvn -q test` checks successfully for AUTH-013 implementation commit 744f715fe916f19f9d7d43251c675dadb07db83b.\n```\n\n---\n\n### AUTH-014 — LDAP AuthenticationProvider""",
    """AUTH-013 — feat(authentication): add ldap credential verification adapter\n```\n\nAUTH-012 now provides externalized, guarded LDAP connection infrastructure. Do not implement AUTH-014 or later tasks during AUTH-013.""":
        """AUTH-014 — feat(authentication): add ldap authentication provider\n```\n\nAUTH-013 now provides isolated LDAP credential verification and a provider-neutral verified directory identity result. Do not implement AUTH-015 or later tasks during AUTH-014."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:160]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
