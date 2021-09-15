import beans.EventDAO;
import beans.InMemoryEventDAOImpl;
import core.DefaultInjector;
import core.Injector;
import core.Provider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
}
