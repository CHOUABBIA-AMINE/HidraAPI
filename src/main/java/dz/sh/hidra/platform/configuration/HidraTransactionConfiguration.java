/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTransactionConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Enables explicit Spring transaction management for application use cases.
 *
 */
package dz.sh.hidra.platform.configuration;

import dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager;
import dz.sh.hidra.platform.persistence.ProtectedOperationDataSource;
import dz.sh.hidra.platform.persistence.ProtectedCredentialRoutingDataSource;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Enables explicit transaction management for persistence-backed application services.
 */
@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
public class HidraTransactionConfiguration {
    @Bean
    ProtectedExecutionBudgetProperties protectedExecutionBudgetProperties(Environment environment) {
        return ProtectedExecutionBudgetProperties.from(environment);
    }

    @Bean
    static BeanPostProcessor protectedOperationDatasource(Environment environment) {
        var publication=ProtectedPublicationDatabaseProperties.from(environment);
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String name) {
                return bean instanceof DataSource ds
                                && !(bean instanceof ProtectedOperationDataSource)
                        ? new ProtectedOperationDataSource(new ProtectedCredentialRoutingDataSource(ds,publication))
                        : bean;
            }
        };
    }

    @Bean
    ProtectedJpaTransactionManager transactionManager(
            EntityManagerFactory factory, DataSource datasource) {
        return new ProtectedJpaTransactionManager(factory, datasource);
    }
}
