/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainModelContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.model
 *
 * @Description : Verifies minimal domain model contracts can be implemented.
 *
 */
package dz.sh.hidra.kernel.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainModelContractTest {

    @Test
    void shouldImplementAggregateRootContract() {
        AggregateRoot<String> aggregateRoot = new TestAggregateRoot("aggregate-1");

        assertThat(aggregateRoot.id()).isEqualTo("aggregate-1");
    }

    @Test
    void shouldImplementEntityContract() {
        Entity<String> entity = new TestEntity("entity-1");

        assertThat(entity.id()).isEqualTo("entity-1");
    }

    @Test
    void shouldImplementValueObjectContract() {
        ValueObject valueObject = new TestValueObject("value");

        assertThat(valueObject).isInstanceOf(ValueObject.class);
    }

    private record TestAggregateRoot(String id) implements AggregateRoot<String> {
    }

    private record TestEntity(String id) implements Entity<String> {
    }

    private record TestValueObject(String value) implements ValueObject {
    }
}
