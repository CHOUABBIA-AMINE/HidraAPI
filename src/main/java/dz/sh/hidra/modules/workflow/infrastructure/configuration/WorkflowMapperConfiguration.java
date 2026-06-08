/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowMapperConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Configuration
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.configuration
 *
 * @Description : Spring configuration for workflow mapper beans.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.configuration;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;

/**
 * Spring configuration for workflow mapper beans.
 *
 * <p>Business role:
 * Exposes workflow mapper components required by the REST controllers and persistence adapters.
 *
 * <p>Architecture role:
 * Infrastructure composition only. This configuration does not contain REST endpoints, persistence
 * adapters, repositories, application services, domain services, tests, migrations, or cross-module
 * implementation imports.
 */
@Configuration
public class WorkflowMapperConfiguration {

    @Bean
    @ConditionalOnMissingBean(WorkflowRestMapper.class)
    public WorkflowRestMapper workflowRestMapper() {
        return new WorkflowRestMapper();
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowPersistenceMapper.class)
    public WorkflowPersistenceMapper workflowPersistenceMapper() {
        return new WorkflowPersistenceMapper();
    }
}
