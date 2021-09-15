package core;

import core.exception.ConstructorNotFoundException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DefaultInjector implements Injector {
    private final Map<Class<?>, Binding<?>> bindings = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        Binding<T> binding = (Binding<T>) bindings.get(type);
        return binding != null ? binding.getProvider() : null;
    }

    @Override
    public <T> void bind(Class<T> type, Class<? extends T> impl) {
        doBind(type, impl, false);
    }

    @Override
    public <T> void bindSingleton(Class<T> type, Class<? extends T> impl) {
        doBind(type, impl, true);
    }

    private <T> void doBind(Class<T> type, Class<? extends T> impl, boolean isSingleton) {
        Constructor<? extends T> implConstructor = findConstructorForInjection(impl);
        Class<?>[] typeDependencies = implConstructor.getParameterTypes();
        bindings.put(type, new Binding<>(type, typeDependencies, implConstructor, isSingleton));
    }

    private <T> Constructor<? extends T> findConstructorForInjection(Class<? extends T> impl) {
        try {
            // TODO proper implementation
            return impl.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new ConstructorNotFoundException();
        }
    }

}
