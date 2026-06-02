/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringPasswordEncoderAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.adapter
 *
 * @Description : Adapter from Spring PasswordEncoder to identity PasswordEncoderPort.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.adapter;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Adapter from Spring PasswordEncoder to identity PasswordEncoderPort.
 *
 * <p>Business role: allows identity application services to encode and verify
 * credentials through an outbound port without owning the hashing algorithm.</p>
 *
 * <p>Architecture role: infrastructure adapter around Spring's {@link PasswordEncoder}.
 * It does not create a Spring Security filter chain, authentication entry point, access
 * denied handler, current-principal extractor, or any business identity model.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and blank password inputs
 * before delegating to Spring's password encoder.</p>
 *
 * <p>Usage: registered by {@code IdentityConfiguration} only when a Spring
 * {@link PasswordEncoder} bean is already available from platform/security
 * infrastructure.</p>
 */
public final class SpringPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    public SpringPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = requireNonNull(passwordEncoder, "PasswordEncoder");
    }

    @Override
    public String encode(String rawPassword) {
        String requiredRawPassword = requireText(rawPassword, "rawPassword");
        return passwordEncoder.encode(requiredRawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        String requiredRawPassword = requireText(rawPassword, "rawPassword");
        String requiredEncodedPassword = requireText(encodedPassword, "encodedPassword");
        return passwordEncoder.matches(requiredRawPassword, requiredEncodedPassword);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be blank.");
        }
        return value;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
