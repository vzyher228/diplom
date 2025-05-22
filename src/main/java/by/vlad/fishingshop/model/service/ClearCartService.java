package by.vlad.fishingshop.model.service;

import by.vlad.fishingshop.model.entity.Cart;
import by.vlad.fishingshop.exception.ServiceException;

/**
 * The interface ClearCartService.
 * <p>
 * Include method to clear cart
 *
 * @author Anton Pysk
 */
public interface ClearCartService {
    /**
     *
     * @param cart {@link Cart}
     * @throws ServiceException service exception
     */
    void clearCart(Cart cart) throws ServiceException;
}
