package bo.custom.impl;

import java.util.ArrayList;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDAO;
import db.DbConnection;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Order;

public class OrderBoImpl implements OrderBo {

    OrderDAO orderDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER);
    OrderDetailBo detailBO = BoFactory.getInstance().getBo(BoFactory.BoType.ORDER_DETAIL);
    ItemBo itemBo = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);

    @Override
    public boolean saveOrder(OrderDTO dto, ArrayList<OrderDetailDTO> orderDetailDTOs) throws Exception {
        // transaction
        try {

            DbConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isSaved = orderDAO.save(new Order(dto.getOrderID(), dto.getOrderDate(), dto.getCustomerID()));
            boolean isDetailsSaved = detailBO.saveOrderDetail(orderDetailDTOs);
            boolean isUpdated = itemBo.updateWhenOrder(orderDetailDTOs);


            if (isSaved & isDetailsSaved & isUpdated) {
                DbConnection.getInstance().getConnection().commit();
                return true;

            } else {
                DbConnection.getInstance().getConnection().rollback();
                return false;
            }

        } catch (Exception e) {
            DbConnection.getInstance().getConnection().rollback();
            return false;

        } finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<OrderDTO> getAllOrderID() throws Exception {
        
        ArrayList<Order> listOrderID = orderDAO.getOrderID();
        ArrayList<OrderDTO> dtoListOrderID = new ArrayList<>();

        for (Order orderDTO : listOrderID) {
            dtoListOrderID.add(new OrderDTO(orderDTO.getOrderID()));
        }
        return dtoListOrderID;
    }

}
