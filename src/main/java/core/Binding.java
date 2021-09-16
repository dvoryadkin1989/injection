package core;

import core.exception.NoSuchBindingException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Binding<T> {
    private final Constructor<? extends T> implConstructor;
    private final Map<Class<?>, Binding<?>> bindings;
    final boolean isSingleton;

    private Provider<T> provider;

    public Binding(Constructor<? extends T> implConstructor, boolean isSingleton, Map<Class<?>, Binding<?>> bindings) {
        this.implConstructor = implConstructor;
        this.isSingleton = isSingleton;
        this.bindings = bindings;
    }

    public Provider<T> getProvider() {
        if (provider != null) {
            return provider;
        }
        List<Provider<?>> dependencyProviders = getDependencyProviders();
        provider = createProvider(implConstructor, dependencyProviders);
        return provider;
    }

    private List<Provider<?>> getDependencyProviders() {
        return Arrays.stream(implConstructor.getParameterTypes())
                .map(bindings::get)
                .peek(this::assertBindingIsPresent)
                .map(Binding::getProvider)
                .collect(toList());
    }

    private void assertBindingIsPresent(Binding<?> binding) {
        if (binding == null) {
            throw new NoSuchBindingException();
        }
    }

    private Provider<T> createProvider(Constructor<? extends T> implConstructor, List<Provider<?>> dependencyProviders) {
        PrototypeProvider<T> prototypeProvider = new PrototypeProvider<>(implConstructor, dependencyProviders);
        return isSingleton ? new SingletonProvider<>(prototypeProvider) : prototypeProvider;
    }
}
