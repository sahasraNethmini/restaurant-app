package bo.custom;

import java.util.ArrayList;

import dto.OrderDTO;
import dto.OrderDetailDTO;

public interface OrderBo {

    public boolean saveOrder(OrderDTO dto, ArrayList<OrderDetailDTO> orderDetailDTOs) throws Exception;

    public ArrayList<OrderDTO> getAllOrderID() throws Exception;
}
