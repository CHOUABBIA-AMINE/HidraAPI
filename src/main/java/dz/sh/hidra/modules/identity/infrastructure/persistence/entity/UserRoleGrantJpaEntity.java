/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleGrantJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Framework-neutral persistence shape for UserRoleGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

/**
 * Persistence shape for UserRoleGrant.
 *
 * <p>This class intentionally carries no JPA annotations in generated source.
 * Add annotations in the repository implementation if needed.</p>
 *
 * @param id stable technical row identifier
 * @param payload serialized or mapped field payload
 */
public record UserRoleGrantJpaEntity(
        String id,
        String payload
) {
}
