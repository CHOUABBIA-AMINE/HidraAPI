/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Command to create a pipeline system using an explicit classification catalog code.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

public record CreatePipelineSystemCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeCode,
        String description
) { }
