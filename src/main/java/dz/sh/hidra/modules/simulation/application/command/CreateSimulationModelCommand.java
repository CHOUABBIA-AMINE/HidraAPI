/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationModelCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.command
 *
 * @Description : Command to create a simulation model.
 *
 */
package dz.sh.hidra.modules.simulation.application.command;

/**
 * Command to create a simulation model.
 */
public record CreateSimulationModelCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String modelTypeId,
        String topologyScopeType,
        String topologyScopeId,
        String description
) {
}
