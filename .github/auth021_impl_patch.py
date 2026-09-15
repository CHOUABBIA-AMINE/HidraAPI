from pathlib import Path

security = Path("src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java")
text = security.read_text(encoding="utf-8")
for item in [
    "import org.springframework.security.config.Customizer;\n",
    "import org.springframework.security.core.userdetails.User;\n",
    "import org.springframework.security.core.userdetails.UserDetails;\n",
    "import org.springframework.security.core.userdetails.UserDetailsService;\n",
    "import org.springframework.security.provisioning.InMemoryUserDetailsManager;\n",
    "    private static final String DEVELOPMENT_PASSWORD = \"hidra-dev-change-me\";\n",
    "    private static final String AUTHENTICATION_MODE_BASIC = \"basic\";\n",
]:
    text = text.replace(item, "")

basic_branch = '''        if (AUTHENTICATION_MODE_BASIC.equals(normalizedMode)) {\n            http.oauth2ResourceServer(AbstractHttpConfigurer::disable);\n            http.httpBasic(Customizer.withDefaults());\n            return http.build();\n        }\n\n'''
if basic_branch not in text:
    raise SystemExit("Expected basic authentication branch not found")
text = text.replace(basic_branch, "")

bean_start = text.index("    @Bean\n    UserDetailsService hidraBootstrapUserDetailsService(")
bean_end = text.index("    @Bean\n    PasswordEncoder hidraPasswordEncoder()", bean_start)
text = text[:bean_start] + text[bean_end:]

role_start = text.find("    private static String[] roleArray(String roles) {")
if role_start != -1:
    production_end_marker = "    }\n}"
    production_start = text.index("    private static boolean productionLike(String environment) {", role_start)
    production_end = text.index(production_end_marker, production_start)
    text = text[:role_start] + text[production_end + len("    }\n"):]

text = text.replace(
    " * @Description : Configures protected APIs for Hidra bearer JWTs while isolating the external OIDC completion bridge.",
    " * @Description : Configures protected APIs for Hidra bearer JWTs with no ordinary in-memory authentication authority."
)
security.write_text(text, encoding="utf-8")

common = Path("src/main/resources/application.properties")
text = common.read_text(encoding="utf-8")
old = '''# Explicit bootstrap user prevents Spring Boot from generating a random development password.\nhidra.security.bootstrap.username=${HIDRA_SECURITY_BOOTSTRAP_USERNAME:hidra-admin}\nhidra.security.bootstrap.password=${HIDRA_SECURITY_BOOTSTRAP_PASSWORD:hidra-dev-change-me}\nhidra.security.bootstrap.roles=${HIDRA_SECURITY_BOOTSTRAP_ROLES:HIDRA_ADMIN}\n'''
new = '''# Controlled persistent LOCAL administrator bootstrap. Disabled unless explicitly enabled.\n# Password has no repository default and must be supplied externally when enabled.\nhidra.security.bootstrap.enabled=${HIDRA_SECURITY_BOOTSTRAP_ENABLED:false}\nhidra.security.bootstrap.username=${HIDRA_SECURITY_BOOTSTRAP_USERNAME:hidra-admin}\nhidra.security.bootstrap.password=${HIDRA_SECURITY_BOOTSTRAP_PASSWORD:}\nhidra.security.bootstrap.email-address=${HIDRA_SECURITY_BOOTSTRAP_EMAIL_ADDRESS:}\nhidra.security.bootstrap.display-name=${HIDRA_SECURITY_BOOTSTRAP_DISPLAY_NAME:Hidra Administrator}\n'''
if old not in text:
    raise SystemExit("Expected common bootstrap block not found")
common.write_text(text.replace(old, new), encoding="utf-8")

for name in [
    "application-dev.properties",
    "application-test.properties",
    "application-staging.properties",
    "application-production.properties",
]:
    path = Path("src/main/resources") / name
    text = path.read_text(encoding="utf-8")
    lines = []
    for line in text.splitlines():
        if line.startswith("hidra.security.bootstrap.roles="):
            continue
        if line.startswith("hidra.security.bootstrap.password="):
            line = "hidra.security.bootstrap.password=${HIDRA_SECURITY_BOOTSTRAP_PASSWORD:}"
        lines.append(line)
    text = "\n".join(lines) + "\n"
    text = text.replace("# Security bootstrap credentials", "# Persistent LOCAL administrator bootstrap inputs (external secret only)")
    text = text.replace(
        "# The in-memory Basic bootstrap remains available only when explicitly selected\n# through HIDRA_SECURITY_AUTHENTICATION_MODE=basic until AUTH-021 replaces it.",
        "# Persistent bootstrap is disabled by default and uses the common enabled flag."
    )
    path.write_text(text, encoding="utf-8")
