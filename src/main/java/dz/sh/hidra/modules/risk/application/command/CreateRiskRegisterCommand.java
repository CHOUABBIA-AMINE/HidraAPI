/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskRegisterCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.command
 *
 * @Description : Command to create risk register.
 *
 */
package dz.sh.hidra.modules.risk.application.command;

import java.time.Instant;

/**
 * Command to create risk register.
 */
public record CreateRiskRegisterCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String registerTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        String reviewFrequencyId,
        Instant effectiveFrom,
        Instant effectiveTo,
        String createdByActorId,
        String createdByDisplayNameSnapshot
) {
}
