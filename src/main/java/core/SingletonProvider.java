package core;

public class SingletonProvider<T> implements Provider<T> {
    private final PrototypeProvider<T> delegateProvider;
    private T instance;

    public SingletonProvider(PrototypeProvider<T> delegateProvider) {
        this.delegateProvider = delegateProvider;
    }

    @Override
    public T getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = delegateProvider.getInstance();
        return instance;
    }
}
