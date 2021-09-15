package beans;

import core.Inject;

public class DefaultEventService implements EventService {
    private final EventDAO dao;

    @Inject
    public DefaultEventService(EventDAO dao) {
        this.dao = dao;
    }

    @Override
    public EventDAO getDao() {
        return dao;
    }
}
