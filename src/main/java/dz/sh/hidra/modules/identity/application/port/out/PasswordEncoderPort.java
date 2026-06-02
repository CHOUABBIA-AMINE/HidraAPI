/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PasswordEncoderPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound port for password encoding decisions needed by identity use cases.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

/**
 * Outbound port for password encoding decisions needed by identity use cases.
 *
 * <p>Business role: represents identity's need to store and compare encoded credentials
 * without owning the raw hashing algorithm implementation.</p>
 *
 * <p>Architecture role: application outbound port implemented later by infrastructure or
 * platform adapters. This contract does not configure Spring Security and does not
 * implement hashing itself.</p>
 *
 * <p>Validation responsibility: implementations should reject blank raw or encoded
 * password values and use a secure encoder outside the domain model.</p>
 *
 * <p>Usage: depend on this port from application services when credential handling is
 * introduced by a later roadmap task.</p>
 */
public interface PasswordEncoderPort {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
