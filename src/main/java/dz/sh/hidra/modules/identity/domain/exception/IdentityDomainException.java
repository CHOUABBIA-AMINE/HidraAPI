/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Base exception for identity domain failures.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

import dz.sh.hidra.kernel.domain.exception.DomainException;

/**
 * Base exception for identity-specific domain failures.
 *
 * <p>Business role: marks a failure caused by violated identity business rules around
 * users, roles, permissions, and access policies.</p>
 *
 * <p>Architecture role: identity-domain exception base type that still depends only on
 * the kernel exception contract and remains independent from HTTP, Spring, persistence,
 * and platform security concerns.</p>
 *
 * <p>Validation responsibility: carries a clear domain failure message and optional
 * cause without introducing transport-specific status codes.</p>
 *
 * <p>Usage: extend this type for identity-specific domain errors that are not better
 * represented by a more specific kernel exception subtype.</p>
 */
public class IdentityDomainException extends DomainException {

    private static final long serialVersionUID = -4287316397339651029L;

    public IdentityDomainException(String message) {
        super(message);
    }

    public IdentityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
