package bo;

import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBoImpl;
import bo.custom.impl.OrderDetailBoImpl;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory() {

    }

    public static BoFactory getInstance() {

        return (boFactory == null) ? (boFactory = new BoFactory()) : boFactory;
    }

    public enum BoType {
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL
    }

    @SuppressWarnings("unchecked")
    public <T> T getBo(BoType type) {
        switch (type) {
            case CUSTOMER:
                return (T) new CustomerBoImpl();

            case ITEM:
                return (T) new ItemBoImpl();

            case ORDER:
                return (T) new OrderBoImpl();

            case ORDER_DETAIL:
                return (T) new OrderDetailBoImpl();

            default:
                return null;
        }
    }

}
