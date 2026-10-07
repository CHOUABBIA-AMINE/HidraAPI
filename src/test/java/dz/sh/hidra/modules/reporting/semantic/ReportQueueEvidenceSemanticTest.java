/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportQueueEvidenceSemanticTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.semantic
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.semantic;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter.JpaReportQueueEvidenceAdapter;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.reporting.domain.value.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ReportQueueEvidenceSemanticTest {
    final ReportTemplateJpaRepository templates=mock(ReportTemplateJpaRepository.class);
    final ReportTemplateVersionJpaRepository versions=mock(ReportTemplateVersionJpaRepository.class);
    final ReportParameterDefinitionJpaRepository definitions=mock(ReportParameterDefinitionJpaRepository.class);
    final ReportParameterValueJpaRepository values=mock(ReportParameterValueJpaRepository.class);
    JpaReportQueueEvidenceAdapter adapter(){return new JpaReportQueueEvidenceAdapter(templates,versions,definitions,values);}
    ReportParameterDefinitionJpaEntity required(){var d=mock(ReportParameterDefinitionJpaEntity.class);when(d.active()).thenReturn(true);when(d.required()).thenReturn(true);when(d.reportDefinitionId()).thenReturn("definition");when(d.id()).thenReturn("parameter");when(d.code()).thenReturn("code");return d;}
    ReportParameterValueJpaEntity value(ReportValueType type){var v=mock(ReportParameterValueJpaEntity.class);when(v.reportRequestId()).thenReturn("request");when(v.parameterDefinitionId()).thenReturn("parameter");when(v.parameterCode()).thenReturn("code");when(v.valueType()).thenReturn(type);return v;}
    @Test void templateRequiresActiveVersionActiveTemplateAndDefinitionLineage(){
        var v=mock(ReportTemplateVersionJpaEntity.class);var t=mock(ReportTemplateJpaEntity.class);
        when(versions.findById("version")).thenReturn(Optional.of(v));when(v.status()).thenReturn(ReportTemplateVersionStatus.ACTIVE);when(v.reportTemplateId()).thenReturn("template");
        when(templates.findById("template")).thenReturn(Optional.of(t));when(t.active()).thenReturn(true);when(t.reportDefinitionId()).thenReturn("definition");
        assertTrue(adapter().eligibleTemplate("version","definition"));assertFalse(adapter().eligibleTemplate("version","other"));
        when(t.active()).thenReturn(false);assertFalse(adapter().eligibleTemplate("version","definition"));when(t.active()).thenReturn(true);
        when(v.status()).thenReturn(ReportTemplateVersionStatus.RETIRED);assertFalse(adapter().eligibleTemplate("version","definition"));
        when(versions.findById("version")).thenReturn(Optional.empty());assertFalse(adapter().eligibleTemplate("version","definition"));
    }
    @Test void missingEmptyWrongDefinitionAndExtraValueFieldsDoNotSatisfyRequiredEvidence(){
        when(definitions.findAll()).thenReturn(List.of(required()));when(values.findAll()).thenReturn(List.of());assertFalse(adapter().requiredParametersPresent("request","definition"));
        var v=value(ReportValueType.TEXT);when(v.valueText()).thenReturn(" ");when(values.findAll()).thenReturn(List.of(v));assertFalse(adapter().requiredParametersPresent("request","definition"));
        when(v.valueText()).thenReturn("value");assertTrue(adapter().requiredParametersPresent("request","definition"));
        when(v.parameterDefinitionId()).thenReturn("foreign");assertFalse(adapter().requiredParametersPresent("request","definition"));when(v.parameterDefinitionId()).thenReturn("parameter");
        when(v.valueNumber()).thenReturn(BigDecimal.ZERO);assertFalse(adapter().requiredParametersPresent("request","definition"));
    }
    @Test void falseZeroAndEachMatchingTypedFieldCountAsConcreteEvidence(){
        when(definitions.findAll()).thenReturn(List.of(required()));
        for(var type:ReportValueType.values()){
            var v=value(type);switch(type){case TEXT,REFERENCE->when(v.valueText()).thenReturn("value");case NUMBER->when(v.valueNumber()).thenReturn(BigDecimal.ZERO);case BOOLEAN->when(v.valueBoolean()).thenReturn(false);case DATE->when(v.valueDate()).thenReturn(java.time.LocalDate.of(2026,10,7));case DATE_TIME->when(v.valueDateTime()).thenReturn(java.time.Instant.EPOCH);case JSON->when(v.valueJson()).thenReturn("{}");}
            when(values.findAll()).thenReturn(List.of(v));assertTrue(adapter().requiredParametersPresent("request","definition"));
        }
    }
    @Test void inactiveAndOptionalDefinitionsDoNotCreateRequiredDefaults(){
        var d=required();when(d.active()).thenReturn(false);when(definitions.findAll()).thenReturn(List.of(d));when(values.findAll()).thenReturn(List.of());assertTrue(adapter().requiredParametersPresent("request","definition"));
        when(d.active()).thenReturn(true);when(d.required()).thenReturn(false);assertTrue(adapter().requiredParametersPresent("request","definition"));
    }
}
