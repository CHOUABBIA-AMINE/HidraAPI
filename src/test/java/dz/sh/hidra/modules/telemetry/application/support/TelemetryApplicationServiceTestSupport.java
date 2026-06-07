/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryApplicationServiceTestSupport
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : TestSupport
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.support
 *
 * @Description : Reusable in-memory telemetry application service test support.
 *
 */
package dz.sh.hidra.modules.telemetry.application.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointBindingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTopologyAssetLookupPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;

/**
 * Reusable in-memory telemetry application service test support.
 *
 * <p>These fakes implement application outbound ports only. They deliberately avoid Spring,
 * JPA, REST, topology implementation classes, and external services.
 */
public final class TelemetryApplicationServiceTestSupport {

    private TelemetryApplicationServiceTestSupport() {
        // Test support only.
    }

    public static <T> PageResult<T> page(List<T> items, PageRequest pageRequest) {
        List<T> safeItems = items == null ? List.of() : List.copyOf(items);
        int page = pageRequest == null ? 0 : pageRequest.page();
        int size = pageRequest == null ? safeItems.size() : pageRequest.size();

        return new PageResult<>(safeItems, page, size, safeItems.size(), safeItems.isEmpty() ? 0 : 1);
    }

    public static final class InMemoryTelemetryCatalogRepository implements TelemetryCatalogRepositoryPort {

        private final Map<String, TelemetryTypeCatalog> catalogs = new LinkedHashMap<>();

        public void add(TelemetryTypeCatalog catalog) {
            save(catalog);
        }

        @Override
        public TelemetryTypeCatalog save(TelemetryTypeCatalog catalog) {
            catalogs.put(catalog.id().value(), catalog);
            return catalog;
        }

        @Override
        public Optional<TelemetryTypeCatalog> findById(TelemetryTypeCatalogId id) {
            return Optional.ofNullable(catalogs.get(id.value()));
        }

        @Override
        public Optional<TelemetryTypeCatalog> findByCatalogNameAndCode(String catalogName, TelemetryCode code) {
            return catalogs.values()
                    .stream()
                    .filter(catalog -> catalog.matchesCatalog(catalogName) && catalog.code().equals(code))
                    .findFirst();
        }

        @Override
        public PageResult<TelemetryTypeCatalog> findAll(String catalogName, Boolean active, PageRequest pageRequest) {
            List<TelemetryTypeCatalog> filtered = catalogs.values()
                    .stream()
                    .filter(catalog -> catalogName == null || catalog.matchesCatalog(catalogName))
                    .filter(catalog -> active == null || catalog.active() == active)
                    .toList();

            return page(filtered, pageRequest);
        }

        @Override
        public boolean existsByCatalogNameAndCode(String catalogName, TelemetryCode code) {
            return findByCatalogNameAndCode(catalogName, code).isPresent();
        }
    }

    public static final class InMemoryTelemetrySourceRepository implements TelemetrySourceRepositoryPort {

        private final Map<String, TelemetrySource> sources = new LinkedHashMap<>();

        public void add(TelemetrySource source) {
            save(source);
        }

        @Override
        public TelemetrySource save(TelemetrySource source) {
            sources.put(source.id().value(), source);
            return source;
        }

        @Override
        public Optional<TelemetrySource> findById(TelemetrySourceId id) {
            return Optional.ofNullable(sources.get(id.value()));
        }

        @Override
        public Optional<TelemetrySource> findByCode(TelemetryCode code) {
            return sources.values().stream().filter(source -> source.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TelemetryCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<TelemetrySource> findAll(
                String searchText,
                TelemetrySourceTypeReference sourceType,
                TelemetryProtocolReference protocol,
                TelemetrySourceStatus status,
                PageRequest pageRequest) {

            return page(sources.values()
                    .stream()
                    .filter(source -> status == null || source.status() == status)
                    .filter(source -> sourceType == null || source.sourceType().equals(sourceType))
                    .filter(source -> protocol == null || source.protocol().equals(protocol))
                    .filter(source -> searchText == null || source.code().value().contains(searchText.toUpperCase()))
                    .toList(), pageRequest);
        }
    }

    public static final class InMemoryTelemetryDeviceRepository implements TelemetryDeviceRepositoryPort {

        private final Map<String, TelemetryDevice> devices = new LinkedHashMap<>();

        public void add(TelemetryDevice device) {
            save(device);
        }

        @Override
        public TelemetryDevice save(TelemetryDevice device) {
            devices.put(device.id().value(), device);
            return device;
        }

        @Override
        public Optional<TelemetryDevice> findById(TelemetryDeviceId id) {
            return Optional.ofNullable(devices.get(id.value()));
        }

        @Override
        public Optional<TelemetryDevice> findByCode(TelemetryCode code) {
            return devices.values().stream().filter(device -> device.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TelemetryCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<TelemetryDevice> findAll(
                String searchText,
                TelemetrySourceId sourceId,
                TelemetryDeviceTypeReference deviceType,
                TelemetryDeviceStatus status,
                PageRequest pageRequest) {

            return page(devices.values()
                    .stream()
                    .filter(device -> sourceId == null || device.sourceId().equals(sourceId))
                    .filter(device -> deviceType == null || device.deviceType().equals(deviceType))
                    .filter(device -> status == null || device.status() == status)
                    .filter(device -> searchText == null || device.code().value().contains(searchText.toUpperCase()))
                    .toList(), pageRequest);
        }
    }

    public static final class InMemoryTelemetryPointRepository implements TelemetryPointRepositoryPort {

        private final Map<String, TelemetryPoint> points = new LinkedHashMap<>();

        public void add(TelemetryPoint point) {
            save(point);
        }

        @Override
        public TelemetryPoint save(TelemetryPoint point) {
            points.put(point.id().value(), point);
            return point;
        }

        @Override
        public Optional<TelemetryPoint> findById(TelemetryPointId id) {
            return Optional.ofNullable(points.get(id.value()));
        }

        @Override
        public Optional<TelemetryPoint> findByCode(TelemetryCode code) {
            return points.values().stream().filter(point -> point.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TelemetryCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<TelemetryPoint> findAll(
                String searchText,
                TelemetryDeviceId deviceId,
                TelemetryPointTypeReference pointType,
                TelemetrySignalTypeReference signalType,
                TelemetryPointStatus status,
                PageRequest pageRequest) {

            return page(points.values()
                    .stream()
                    .filter(point -> deviceId == null || point.deviceId().equals(deviceId))
                    .filter(point -> pointType == null || point.pointType().equals(pointType))
                    .filter(point -> signalType == null || point.signalType().equals(signalType))
                    .filter(point -> status == null || point.status() == status)
                    .filter(point -> searchText == null || point.code().value().contains(searchText.toUpperCase()))
                    .toList(), pageRequest);
        }
    }

    public static final class InMemoryTelemetryPointBindingRepository implements TelemetryPointBindingRepositoryPort {

        private final Map<String, TelemetryPointBinding> bindings = new LinkedHashMap<>();

        public void add(TelemetryPointBinding binding) {
            save(binding);
        }

        @Override
        public TelemetryPointBinding save(TelemetryPointBinding binding) {
            bindings.put(binding.id().value(), binding);
            return binding;
        }

        @Override
        public Optional<TelemetryPointBinding> findById(TelemetryPointBindingId id) {
            return Optional.ofNullable(bindings.get(id.value()));
        }

        @Override
        public List<TelemetryPointBinding> findActiveByPointId(TelemetryPointId pointId) {
            return bindings.values()
                    .stream()
                    .filter(binding -> binding.pointId().equals(pointId) && binding.active())
                    .toList();
        }

        @Override
        public PageResult<TelemetryPointBinding> findAll(
                TelemetryPointId pointId,
                TelemetryCode assetTypeCode,
                String assetId,
                TelemetryBindingRoleReference bindingRole,
                Boolean active,
                PageRequest pageRequest) {

            return page(bindings.values()
                    .stream()
                    .filter(binding -> pointId == null || binding.pointId().equals(pointId))
                    .filter(binding -> assetTypeCode == null || binding.topologyAssetReference().assetTypeCode().equals(assetTypeCode))
                    .filter(binding -> assetId == null || binding.topologyAssetReference().assetId().equals(assetId))
                    .filter(binding -> bindingRole == null || binding.bindingRole().equals(bindingRole))
                    .filter(binding -> active == null || binding.active() == active)
                    .toList(), pageRequest);
        }
    }

    public static final class InMemoryTelemetryReadingRepository implements TelemetryReadingRepositoryPort {

        private final Map<String, TelemetryReading> readings = new LinkedHashMap<>();

        public void add(TelemetryReading reading) {
            save(reading);
        }

        @Override
        public TelemetryReading save(TelemetryReading reading) {
            readings.put(reading.id().value(), reading);
            return reading;
        }

        @Override
        public Optional<TelemetryReading> findById(TelemetryReadingId id) {
            return Optional.ofNullable(readings.get(id.value()));
        }

        @Override
        public Optional<TelemetryReading> findLatestByPointId(TelemetryPointId pointId) {
            List<TelemetryReading> matches = readings.values()
                    .stream()
                    .filter(reading -> reading.pointId().equals(pointId))
                    .sorted((left, right) -> right.sourceTimestamp().value().compareTo(left.sourceTimestamp().value()))
                    .toList();

            return matches.isEmpty() ? Optional.empty() : Optional.of(matches.get(0));
        }

        @Override
        public PageResult<TelemetryReading> findAll(
                TelemetryPointId pointId,
                TelemetryQualityCodeReference qualityCode,
                TelemetryReadingState state,
                TelemetryTimestamp fromSourceTimestamp,
                TelemetryTimestamp toSourceTimestamp,
                TelemetryIngestionBatchId ingestionBatchId,
                PageRequest pageRequest) {

            return page(readings.values()
                    .stream()
                    .filter(reading -> pointId == null || reading.pointId().equals(pointId))
                    .filter(reading -> qualityCode == null || reading.qualityCode().equals(qualityCode))
                    .filter(reading -> state == null || reading.state() == state)
                    .filter(reading -> ingestionBatchId == null || ingestionBatchId.equals(reading.ingestionBatchId()))
                    .toList(), pageRequest);
        }

        @Override
        public boolean existsByPointIdAndSourceTimestamp(TelemetryPointId pointId, TelemetryTimestamp sourceTimestamp) {
            return readings.values()
                    .stream()
                    .anyMatch(reading -> reading.pointId().equals(pointId)
                            && reading.sourceTimestamp().equals(sourceTimestamp));
        }
    }

    public static final class InMemoryTelemetryIngestionBatchRepository implements TelemetryIngestionBatchRepositoryPort {

        private final Map<String, TelemetryIngestionBatch> batches = new LinkedHashMap<>();

        public void add(TelemetryIngestionBatch batch) {
            save(batch);
        }

        @Override
        public TelemetryIngestionBatch save(TelemetryIngestionBatch batch) {
            batches.put(batch.id().value(), batch);
            return batch;
        }

        @Override
        public Optional<TelemetryIngestionBatch> findById(TelemetryIngestionBatchId id) {
            return Optional.ofNullable(batches.get(id.value()));
        }

        @Override
        public PageResult<TelemetryIngestionBatch> findAll(
                TelemetrySourceId sourceId,
                TelemetryIngestionBatchStatus status,
                TelemetryTimestamp startedFrom,
                TelemetryTimestamp startedTo,
                PageRequest pageRequest) {

            return page(batches.values()
                    .stream()
                    .filter(batch -> sourceId == null || batch.sourceId().equals(sourceId))
                    .filter(batch -> status == null || batch.status() == status)
                    .toList(), pageRequest);
        }
    }

    public static final class AllowingTelemetryTopologyAssetLookup implements TelemetryTopologyAssetLookupPort {

        @Override
        public Optional<TopologyAssetReferenceDto> findTopologyAssetReference(TelemetryCode assetTypeCode, String assetId) {
            return Optional.of(new TopologyAssetReferenceDto(
                    assetTypeCode.value(),
                    assetId,
                    "GPL-001",
                    "Pipeline GPL 001"));
        }

        @Override
        public boolean existsTopologyAsset(TelemetryCode assetTypeCode, String assetId) {
            return true;
        }
    }

    public static final class RejectingTelemetryTopologyAssetLookup implements TelemetryTopologyAssetLookupPort {

        @Override
        public Optional<TopologyAssetReferenceDto> findTopologyAssetReference(TelemetryCode assetTypeCode, String assetId) {
            return Optional.empty();
        }

        @Override
        public boolean existsTopologyAsset(TelemetryCode assetTypeCode, String assetId) {
            return false;
        }
    }
}
