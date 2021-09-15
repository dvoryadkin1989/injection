package core;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Binding<T> {
    private final Class<T> type;
    private final Class<?>[] dependencies;
    private final Constructor<? extends T> implConstructor;
    private final Map<Class<?>, Binding<?>> bindings;
    final boolean isSingleton;

    private Provider<T> provider;

    public Binding(Class<T> type, Class<?>[] dependencies, Constructor<? extends T> implConstructor, boolean isSingleton, Map<Class<?>, Binding<?>> bindings) {
        this.type = type;
        this.dependencies = dependencies;
        this.implConstructor = implConstructor;
        this.isSingleton = isSingleton;
        this.bindings = bindings;
    }

    public Provider<T> getProvider() {
        if (provider != null) {
            return provider;
        }
        // TODO check that binding is not found and throw exception
        List<Provider<?>> dependencyProviders = Arrays.stream(dependencies).map(type -> bindings.get(type).getProvider()).collect(toList());
        return new PrototypeProvider<>(implConstructor, dependencyProviders);
    }
}
