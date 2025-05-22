package by.vlad.fishingshop.model.service.impl;

import by.vlad.fishingshop.model.entity.Order;
import by.vlad.fishingshop.exception.RepositoryException;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.Specification;
import by.vlad.fishingshop.model.repository.impl.OrderRepositoryImpl;
import by.vlad.fishingshop.model.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService implements Service<Order> {
    private static final Logger logger = LogManager.getLogger();
    private OrderRepositoryImpl repository = OrderRepositoryImpl.getInstance();
    private static OrderService instance;

    private OrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    @Override
    public void insert(Order ob) throws ServiceException {
        try {
            repository.insert(ob);
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Order ob) throws ServiceException {
        try {
            repository.update(ob);
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> query(Specification specification) throws ServiceException {
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
    }
}
