/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringLocalPasswordHashAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Adapts Spring PasswordEncoder to the Identity LOCAL password-hashing port.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.LocalPasswordHashPort;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Spring Security password hashing adapter for persistent LOCAL credentials.
 */
@Component
public final class SpringLocalPasswordHashAdapter implements LocalPasswordHashPort {

    private final PasswordEncoder passwordEncoder;

    public SpringLocalPasswordHashAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "PasswordEncoder must not be null.");
    }

    @Override
    public String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("LOCAL bootstrap password must not be blank.");
        }
        return passwordEncoder.encode(rawPassword);
    }
}
