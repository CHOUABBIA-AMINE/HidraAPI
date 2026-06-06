/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DynamicTopologyJpaRepositoryFakes
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Dynamic proxy repository fakes for topology persistence adapter tests.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Dynamic proxy repository fakes for topology persistence adapter tests.
 *
 * <p>Business role:
 * Provides deterministic in-memory behavior for Spring Data repository interfaces used by topology
 * persistence adapters.
 *
 * <p>Architecture role:
 * Test-only helper. It avoids Mockito, Spring Boot, a database, H2, PostgreSQL, and Testcontainers.
 *
 * <p>Validation:
 * The fake implements the repository methods used by adapters: save, findById, findByCode,
 * existsByCode, and findAll.
 */
final class DynamicTopologyJpaRepositoryFakes {

    private DynamicTopologyJpaRepositoryFakes() {
    }

    static <R, E> R repository(
            Class<R> repositoryType,
            Function<E, String> idExtractor,
            Function<E, String> codeExtractor) {

        InvocationHandler handler = new RepositoryInvocationHandler<>(idExtractor, codeExtractor);
        Object proxy = Proxy.newProxyInstance(
                repositoryType.getClassLoader(),
                new Class<?>[] {repositoryType},
                handler);
        return repositoryType.cast(proxy);
    }

    private static final class RepositoryInvocationHandler<E> implements InvocationHandler {

        private final Function<E, String> idExtractor;
        private final Function<E, String> codeExtractor;
        private final Map<String, E> entities = new LinkedHashMap<>();

        private RepositoryInvocationHandler(
                Function<E, String> idExtractor,
                Function<E, String> codeExtractor) {

            this.idExtractor = idExtractor;
            this.codeExtractor = codeExtractor;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            String methodName = method.getName();

            if ("save".equals(methodName)) {
                E entity = entityArg(args);
                entities.put(idExtractor.apply(entity), entity);
                return entity;
            }
            if ("findById".equals(methodName)) {
                return Optional.ofNullable(entities.get((String) args[0]));
            }
            if ("findAll".equals(methodName) && method.getParameterCount() == 0) {
                return new ArrayList<>(entities.values());
            }
            if ("findByCode".equals(methodName)) {
                return entities.values().stream()
                        .filter(entity -> codeExtractor.apply(entity).equals(args[0]))
                        .findFirst();
            }
            if ("existsByCode".equals(methodName)) {
                return entities.values().stream()
                        .anyMatch(entity -> codeExtractor.apply(entity).equals(args[0]));
            }
            if (methodName.startsWith("findBy") && List.class.isAssignableFrom(method.getReturnType())) {
                return new ArrayList<>(entities.values());
            }
            if ("toString".equals(methodName)) {
                return "DynamicTopologyJpaRepositoryFake";
            }
            if ("hashCode".equals(methodName)) {
                return System.identityHashCode(proxy);
            }
            if ("equals".equals(methodName)) {
                return proxy == args[0];
            }

            throw new UnsupportedOperationException("Unsupported repository method in fake: " + methodName);
        }

        @SuppressWarnings("unchecked")
        private E entityArg(Object[] args) {
            return (E) args[0];
        }
    }
}
