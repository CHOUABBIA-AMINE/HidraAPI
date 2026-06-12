/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectMeasurementJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DefectMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for DefectMeasurement.
     */
    @Entity
    @Table(name = "hidra_integrity_defect_measurement")
    public class DefectMeasurementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "defect_id", nullable = false, length = 80)
    private String defectId;

    @Column(name = "measurement_type_id", nullable = false, length = 80)
    private String measurementTypeId;

    @Column(name = "measurement_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal measurementValue;

    @Column(name = "unit_id", nullable = false, length = 80)
    private String unitId;

    @Column(name = "measurement_method_id", nullable = true, length = 80)
    private String measurementMethodId;

    @Column(name = "measured_by_actor_id", nullable = true, length = 80)
    private String measuredByActorId;

    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

        protected DefectMeasurementJpaEntity() {
            // Required by JPA.
        }

        public DefectMeasurementJpaEntity(
                String id,
            String defectId,
            String measurementTypeId,
            BigDecimal measurementValue,
            String unitId,
            String measurementMethodId,
            String measuredByActorId,
            Instant measuredAt,
            String notes
        ) {
            this.id = id;
        this.defectId = defectId;
        this.measurementTypeId = measurementTypeId;
        this.measurementValue = measurementValue;
        this.unitId = unitId;
        this.measurementMethodId = measurementMethodId;
        this.measuredByActorId = measuredByActorId;
        this.measuredAt = measuredAt;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String defectId() {
        return defectId;
    }


    public String measurementTypeId() {
        return measurementTypeId;
    }


    public BigDecimal measurementValue() {
        return measurementValue;
    }


    public String unitId() {
        return unitId;
    }


    public String measurementMethodId() {
        return measurementMethodId;
    }


    public String measuredByActorId() {
        return measuredByActorId;
    }


    public Instant measuredAt() {
        return measuredAt;
    }


    public String notes() {
        return notes;
    }

    }
