package by.vlad.fishingshop.model.service.impl;

import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.exception.RepositoryException;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.Specification;
import by.vlad.fishingshop.model.repository.impl.ProductRepositoryImpl;
import by.vlad.fishingshop.model.service.ProductService;
import by.vlad.fishingshop.model.validator.ProductValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger();
    private ProductRepositoryImpl repository = ProductRepositoryImpl.getInstance();
    private static ProductServiceImpl instance;

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Product product) throws ServiceException {
        boolean flag = false;
        try {
            if (ProductValidator.isValidName(product.getName()) & ProductValidator.isValidVendor(product.getVendor()) &
                    ProductValidator.isValidNumberInStock(product.getNumberInStock()) & ProductValidator.isValidImage(product.getImage())) {
                repository.insert(product);
                flag = true;
            }
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean update(Product product) throws ServiceException {
        boolean flag = false;
        try {
            if (ProductValidator.isValidName(product.getName()) & ProductValidator.isValidVendor(product.getVendor()) &
                    ProductValidator.isValidNumberInStock(product.getNumberInStock())) {
                repository.update(product);
                flag = true;
            }
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public List<Product> query(Specification specification) throws ServiceException {
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            logger.error("Something happened while connected to db", e);
            throw new ServiceException(e);
        }
    }
}
