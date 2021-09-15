package core;

import java.lang.reflect.Constructor;

public class Binding<T> {
    final Class<T> type;
    final Class<?>[] dependencies;
    final Constructor<? extends T> implConstructor;
    final boolean isSingleton;

    private Provider<T> provider;

    public Binding(Class<T> type, Class<?>[] dependencies, Constructor<? extends T> implConstructor, boolean isSingleton) {
        this.type = type;
        this.dependencies = dependencies;
        this.implConstructor = implConstructor;
        this.isSingleton = isSingleton;
    }

    public Provider<T> getProvider() {
        if (provider != null) {
            return provider;
        }
        return new PrototypeProvider<>(implConstructor, null);
    }
}
