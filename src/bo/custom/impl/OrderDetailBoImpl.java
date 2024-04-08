package bo.custom.impl;

import java.util.ArrayList;

import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;

public class OrderDetailBoImpl implements OrderDetailBo {

    OrderDetailDAO orderDetailDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER_DETAIL);

    @Override
    public boolean saveOrderDetail(ArrayList<OrderDetailDTO> dtoList) throws Exception {

        for (OrderDetailDTO orderDetailDTO : dtoList) {
            boolean isSaved = saveOrderDetail(orderDetailDTO);

            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {

        return orderDetailDAO.save(new OrderDetail(orderDetailDTO.getOrderID(),
                orderDetailDTO.getCode(),
                orderDetailDTO.getQty(), orderDetailDTO.getUnitPrice()));
    }
}
