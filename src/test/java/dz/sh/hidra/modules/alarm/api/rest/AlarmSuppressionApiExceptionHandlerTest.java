/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest
 *
 * @Description : Verifies stable HTTP 409 mapping for Alarm suppression conflicts.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.modules.alarm.domain.exception.AlarmSuppressionConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

class AlarmSuppressionApiExceptionHandlerTest {

    @Test
    void mapsSuppressionConflictToHttp409() {
        ProblemDetail detail = new AlarmSuppressionApiExceptionHandler().conflict(
                new AlarmSuppressionConflictException("ACTIVE suppression already exists.")
        );

        assertEquals(HttpStatus.CONFLICT.value(), detail.getStatus());
        assertEquals("ALARM_SUPPRESSION_CONFLICT", detail.getTitle());
        assertEquals("ALARM_SUPPRESSION_CONFLICT", detail.getProperties().get("code"));
    }
}
