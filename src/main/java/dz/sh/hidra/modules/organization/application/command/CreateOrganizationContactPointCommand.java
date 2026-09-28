/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationContactPointCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to create an operational contact point for an Organization-owned target.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;

/**
 * Creates one canonical Organization contact point.
 *
 * @param contactPointType operational channel type
 * @param target canonical Organization-owned target
 * @param label optional business label
 * @param value contact value
 * @param primaryContact whether the channel is primary
 * @param emergencyContact whether the channel is intended for emergency use
 * @param active whether the contact point is active
 */
public record CreateOrganizationContactPointCommand(
        ContactPointType contactPointType,
        ContactPointTargetReference target,
        String label,
        String value,
        boolean primaryContact,
        boolean emergencyContact,
        boolean active
) {
}
