/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Command to create pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
public record CreatePipelineSystemCommand(String code, String nameAr, String nameFr, String nameEn, PipelineSystemType systemType, String description) { }
