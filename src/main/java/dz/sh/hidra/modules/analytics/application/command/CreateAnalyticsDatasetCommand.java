/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAnalyticsDatasetCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.command
 *
 * @Description : Command to create an analytics dataset.
 *
 */
package dz.sh.hidra.modules.analytics.application.command;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsDatasetType;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRefreshMode;

/**
 * Command to create an analytics dataset.
 */
public record CreateAnalyticsDatasetCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        AnalyticsDatasetType datasetType,
        AnalyticsRefreshMode refreshMode,
        String schemaVersion,
        String createdFrom
) {
}
