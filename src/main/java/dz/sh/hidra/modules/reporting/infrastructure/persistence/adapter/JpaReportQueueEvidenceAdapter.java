/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportQueueEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.reporting.application.port.out.ReportQueueEvidencePort;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateVersionJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportParameterDefinitionJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportParameterValueJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportParameterValueJpaEntity;
import dz.sh.hidra.modules.reporting.domain.value.ReportTemplateVersionStatus;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class JpaReportQueueEvidenceAdapter implements ReportQueueEvidencePort {
    private final ReportTemplateJpaRepository templates;
    private final ReportTemplateVersionJpaRepository versions;
    private final ReportParameterDefinitionJpaRepository definitions;
    private final ReportParameterValueJpaRepository values;
    public JpaReportQueueEvidenceAdapter(ReportTemplateJpaRepository templates,ReportTemplateVersionJpaRepository versions,
            ReportParameterDefinitionJpaRepository definitions,ReportParameterValueJpaRepository values){
        this.templates=Objects.requireNonNull(templates);this.versions=Objects.requireNonNull(versions);
        this.definitions=Objects.requireNonNull(definitions);this.values=Objects.requireNonNull(values);
    }
    public boolean eligibleTemplate(String versionId,String definitionId){
        return versions.findById(versionId).filter(v->v.status()==ReportTemplateVersionStatus.ACTIVE)
            .flatMap(v->templates.findById(v.reportTemplateId()))
            .filter(t->t.active() && definitionId.equals(t.reportDefinitionId())).isPresent();
    }
    public boolean requiredParametersPresent(String requestId,String definitionId){
        var recorded=values.findAll().stream().filter(v->requestId.equals(v.reportRequestId())).toList();
        return definitions.findAll().stream().filter(d->d.active() && d.required() && definitionId.equals(d.reportDefinitionId()))
            .allMatch(d->recorded.stream().anyMatch(v->d.id().equals(v.parameterDefinitionId())
                && d.code().equals(v.parameterCode()) && concreteValue(v)));
    }
    private static boolean concreteValue(ReportParameterValueJpaEntity v){
        int fields=(v.valueText()!=null?1:0)+(v.valueNumber()!=null?1:0)+(v.valueBoolean()!=null?1:0)
            +(v.valueDate()!=null?1:0)+(v.valueDateTime()!=null?1:0)+(v.valueJson()!=null?1:0);
        if(fields!=1 || v.valueType()==null)return false;
        return switch(v.valueType()){
            case TEXT,REFERENCE -> v.valueText()!=null && !v.valueText().isBlank();
            case NUMBER -> v.valueNumber()!=null;
            case BOOLEAN -> v.valueBoolean()!=null;
            case DATE -> v.valueDate()!=null;
            case DATE_TIME -> v.valueDateTime()!=null;
            case JSON -> v.valueJson()!=null && !v.valueJson().isBlank();
        };
    }
}
