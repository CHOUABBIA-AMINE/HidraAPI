from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-012 LDAP security infrastructure completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-011 LOCAL authentication outcomes completed |":
        "| Status | Active — AUTH-012 LDAP security infrastructure completed |",
    "| AUTH-012 | `feat(authentication): add ldap security infrastructure` | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | Planned |":
        "| AUTH-012 | `feat(authentication): add ldap security infrastructure` | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | Completed — Spring LDAP core plus externalized URL/base/search/bind configuration, connection/read timeouts, enabled-only LdapContextSource, and staging/production LDAPS guard added; no secrets, credential verification, or LDAP AuthenticationProvider introduced; `mvn -q test` passed in PR CI run 153 |",
    """Requirements:\n\n```text\nadd only required Maven LDAP dependencies\nexternalized URL/base DN/search/filter/bind configuration\nTLS/LDAPS production guard\nconnection/read timeout configuration\nno company secrets committed\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-013 — LDAP credential verification adapter""":
        """Requirements:\n\n```text\nadd only required Maven LDAP dependencies\nexternalized URL/base DN/search/filter/bind configuration\nTLS/LDAPS production guard\nconnection/read timeout configuration\nno company secrets committed\n```\n\nStatus:\n\n```text\nCompleted — added Spring LDAP core as the only LDAP dependency required for the technical connection seam. Added HidraLdapSecurityProperties and HidraLdapSecurityConfiguration, with LDAP disabled by default, externalized URL/base DN/user search/filter/bind settings, externalized bind password, configurable connection/read timeouts, and an enabled-only LdapContextSource. Enabled staging/production LDAP must use ldaps://. No directory credential verification, Hidra user mapping, LDAP AuthenticationProvider, company secret, or AUTH-013 behavior was introduced.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 153 completed repository and acceptance `mvn -q test` checks successfully for AUTH-012 implementation commit 4d62eb5f1530f4a4f20acb778a7740dfbf407f1a.\n```\n\n---\n\n### AUTH-013 — LDAP credential verification adapter""",
    """AUTH-012 — feat(authentication): add ldap security infrastructure\n```\n\nAUTH-011 now records LOCAL success/failure state and AuthenticationEvent outcomes without storing submitted passwords. Do not implement AUTH-013 or later tasks during AUTH-012.""":
        """AUTH-013 — feat(authentication): add ldap credential verification adapter\n```\n\nAUTH-012 now provides externalized, guarded LDAP connection infrastructure. Do not implement AUTH-014 or later tasks during AUTH-013."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:160]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
