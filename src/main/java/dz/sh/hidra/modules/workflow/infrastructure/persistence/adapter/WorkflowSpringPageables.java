/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowSpringPageables
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Internal mapper between kernel pagination and Spring Data pagination.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import dz.sh.hidra.kernel.application.pagination.PageResult;

/**
 * Internal mapper between kernel pagination and Spring Data pagination.
 */
final class WorkflowSpringPageables {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    private WorkflowSpringPageables() {
        // Utility class.
    }

    static Pageable from(dz.sh.hidra.kernel.application.pagination.PageRequest pageRequest) {
        if (pageRequest == null) {
            return PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);
        }

        int page = Math.max(0, pageRequest.page());
        int size = pageRequest.size() <= 0 ? DEFAULT_SIZE : pageRequest.size();

        return PageRequest.of(page, size);
    }

    static <E, D> PageResult<D> toPageResult(Page<E> page, Function<E, D> mapper) {
        return new PageResult<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
