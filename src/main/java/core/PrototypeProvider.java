package core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PrototypeProvider<T> implements Provider<T> {
    private final Constructor<? extends T> constructor;
    private final List<Provider<?>> dependencyProviders;

    public PrototypeProvider(Constructor<? extends T> constructor, List<Provider<?>> dependencyProviders) {
        this.constructor = constructor;
        this.dependencyProviders = dependencyProviders;
    }

    @Override
    public T getInstance() {
        Object[] dependencies = dependencyProviders.stream().map(Provider::getInstance).toArray();
        try {
            return constructor.newInstance(dependencies);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // TODO throw proper exception
            throw new RuntimeException(e);
        }
    }
}
