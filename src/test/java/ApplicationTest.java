import beans.DefaultEventService;
import beans.EventDAO;
import beans.EventService;
import beans.InMemoryEventDAOImpl;
import core.DefaultInjector;
import core.Injector;
import core.Provider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void testExistingBinding() {
        Injector injector = new DefaultInjector();
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    void testExistingInjection() {
        Injector injector = new DefaultInjector();
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class);
        injector.bind(EventService.class, DefaultEventService.class);
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        assertNotNull(serviceProvider);
        EventService service = serviceProvider.getInstance();
        assertNotNull(service);
        assertNotNull(service.getDao());
        assertNotSame(serviceProvider.getInstance(), serviceProvider.getInstance());
    }

    @Test
    void testSingletonProvider() {
        Injector injector = new DefaultInjector();
        injector.bindSingleton(EventDAO.class, InMemoryEventDAOImpl.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        assertNotNull(daoProvider);
        assertSame(daoProvider.getInstance(), daoProvider.getInstance());
    }
}
