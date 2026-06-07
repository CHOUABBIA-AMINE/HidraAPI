/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Configuration
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.configuration
 *
 * @Description : Spring configuration for telemetry domain and application services.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointBindingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTopologyAssetLookupPort;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryBindingApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryCatalogApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryDeviceApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryIngestionApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryPointApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryReadingApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetrySourceApplicationService;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryReadingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetrySourcePolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryBindingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryCatalogDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryReadingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;

/**
 * Spring configuration for telemetry domain and application services.
 *
 * <p>Business role:
 * Wires the telemetry module's policy, domain service, and application service graph.
 *
 * <p>Architecture role:
 * This is infrastructure-layer composition only. It does not contain business behavior, persistence
 * mapping, REST mapping, migrations, tests, topology implementation imports, flow calculation, risk
 * scoring, analytics, workflow, reporting, or notification behavior.
 *
 * <p>Dependency rule:
 * Outbound ports are supplied by later infrastructure adapters. This configuration intentionally
 * depends on outbound port interfaces, not JPA repositories or external clients.
 */
@Configuration
public class TelemetryConfiguration {

    @Bean
    TelemetryCatalogPolicy telemetryCatalogPolicy() {
        return new TelemetryCatalogPolicy();
    }

    @Bean
    TelemetrySourcePolicy telemetrySourcePolicy() {
        return new TelemetrySourcePolicy();
    }

    @Bean
    TelemetryPointPolicy telemetryPointPolicy() {
        return new TelemetryPointPolicy();
    }

    @Bean
    TelemetryBindingPolicy telemetryBindingPolicy() {
        return new TelemetryBindingPolicy();
    }

    @Bean
    TelemetryReadingPolicy telemetryReadingPolicy() {
        return new TelemetryReadingPolicy();
    }

    @Bean
    TelemetryIngestionPolicy telemetryIngestionPolicy() {
        return new TelemetryIngestionPolicy();
    }

    @Bean
    TelemetryCatalogDomainService telemetryCatalogDomainService(
            TelemetryCatalogPolicy telemetryCatalogPolicy) {

        return new TelemetryCatalogDomainService(telemetryCatalogPolicy);
    }

    @Bean
    TelemetryRegistrationDomainService telemetryRegistrationDomainService(
            TelemetrySourcePolicy telemetrySourcePolicy,
            TelemetryPointPolicy telemetryPointPolicy) {

        return new TelemetryRegistrationDomainService(
                telemetrySourcePolicy,
                telemetryPointPolicy);
    }

    @Bean
    TelemetryBindingDomainService telemetryBindingDomainService(
            TelemetryBindingPolicy telemetryBindingPolicy) {

        return new TelemetryBindingDomainService(telemetryBindingPolicy);
    }

    @Bean
    TelemetryReadingDomainService telemetryReadingDomainService(
            TelemetryPointPolicy telemetryPointPolicy,
            TelemetryReadingPolicy telemetryReadingPolicy) {

        return new TelemetryReadingDomainService(
                telemetryPointPolicy,
                telemetryReadingPolicy);
    }

    @Bean
    TelemetryIngestionDomainService telemetryIngestionDomainService(
            TelemetryIngestionPolicy telemetryIngestionPolicy) {

        return new TelemetryIngestionDomainService(telemetryIngestionPolicy);
    }

    @Bean
    TelemetryCatalogApplicationService telemetryCatalogApplicationService(
            TelemetryCatalogRepositoryPort telemetryCatalogRepositoryPort,
            TelemetryCatalogDomainService telemetryCatalogDomainService) {

        return new TelemetryCatalogApplicationService(
                telemetryCatalogRepositoryPort,
                telemetryCatalogDomainService);
    }

    @Bean
    TelemetrySourceApplicationService telemetrySourceApplicationService(
            TelemetrySourceRepositoryPort telemetrySourceRepositoryPort,
            TelemetryRegistrationDomainService telemetryRegistrationDomainService) {

        return new TelemetrySourceApplicationService(
                telemetrySourceRepositoryPort,
                telemetryRegistrationDomainService);
    }

    @Bean
    TelemetryDeviceApplicationService telemetryDeviceApplicationService(
            TelemetrySourceRepositoryPort telemetrySourceRepositoryPort,
            TelemetryDeviceRepositoryPort telemetryDeviceRepositoryPort,
            TelemetryRegistrationDomainService telemetryRegistrationDomainService) {

        return new TelemetryDeviceApplicationService(
                telemetrySourceRepositoryPort,
                telemetryDeviceRepositoryPort,
                telemetryRegistrationDomainService);
    }

    @Bean
    TelemetryPointApplicationService telemetryPointApplicationService(
            TelemetryDeviceRepositoryPort telemetryDeviceRepositoryPort,
            TelemetryPointRepositoryPort telemetryPointRepositoryPort,
            TelemetryRegistrationDomainService telemetryRegistrationDomainService) {

        return new TelemetryPointApplicationService(
                telemetryDeviceRepositoryPort,
                telemetryPointRepositoryPort,
                telemetryRegistrationDomainService);
    }

    @Bean
    TelemetryBindingApplicationService telemetryBindingApplicationService(
            TelemetryPointRepositoryPort telemetryPointRepositoryPort,
            TelemetryPointBindingRepositoryPort telemetryPointBindingRepositoryPort,
            TelemetryTopologyAssetLookupPort telemetryTopologyAssetLookupPort,
            TelemetryBindingDomainService telemetryBindingDomainService) {

        return new TelemetryBindingApplicationService(
                telemetryPointRepositoryPort,
                telemetryPointBindingRepositoryPort,
                telemetryTopologyAssetLookupPort,
                telemetryBindingDomainService);
    }

    @Bean
    TelemetryReadingApplicationService telemetryReadingApplicationService(
            TelemetryPointRepositoryPort telemetryPointRepositoryPort,
            TelemetryReadingRepositoryPort telemetryReadingRepositoryPort,
            TelemetryReadingDomainService telemetryReadingDomainService) {

        return new TelemetryReadingApplicationService(
                telemetryPointRepositoryPort,
                telemetryReadingRepositoryPort,
                telemetryReadingDomainService);
    }

    @Bean
    TelemetryIngestionApplicationService telemetryIngestionApplicationService(
            TelemetrySourceRepositoryPort telemetrySourceRepositoryPort,
            TelemetryIngestionBatchRepositoryPort telemetryIngestionBatchRepositoryPort,
            TelemetryIngestionDomainService telemetryIngestionDomainService) {

        return new TelemetryIngestionApplicationService(
                telemetrySourceRepositoryPort,
                telemetryIngestionBatchRepositoryPort,
                telemetryIngestionDomainService);
    }
}
