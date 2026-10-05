/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkbenchResourceRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Registers only explicit workbench contracts without JPA entity discovery.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;

/** Immutable and empty by default when no reviewed module adapter beans exist. */
@Component
public final class WorkbenchResourceRegistry {
    private final Map<String, Entry> entries;

    public WorkbenchResourceRegistry(List<WorkbenchResource> resources) {
        Map<String, Entry> registered = new LinkedHashMap<>();
        for (WorkbenchResource resource : resources) {
            WorkbenchResourceDefinition definition = resource.definition();
            if (definition == null || registered.putIfAbsent(key(definition.module(), definition.resource()),
                    new Entry(definition, resource)) != null) {
                throw new IllegalArgumentException("Invalid workbench registry.");
            }
        }
        entries = Map.copyOf(registered);
    }

    public Collection<Entry> entries() {
        return entries.values();
    }

    public Entry resolve(String module, String resource) {
        if (!WorkbenchResourceDefinition.validIdentifier(module)
                || !WorkbenchResourceDefinition.validIdentifier(resource)) {
            throw unavailable();
        }
        Entry entry = entries.get(key(module, resource));
        if (entry == null) {
            throw unavailable();
        }
        return entry;
    }

    static NoSuchElementException unavailable() {
        return new NoSuchElementException("Workbench resource unavailable.");
    }

    private static String key(String module, String resource) {
        return module + ":" + resource;
    }

    public record Entry(WorkbenchResourceDefinition definition, WorkbenchResource resource) { }
}
