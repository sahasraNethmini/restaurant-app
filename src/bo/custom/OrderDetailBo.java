package bo.custom;

import java.util.ArrayList;

import dto.OrderDetailDTO;

public interface OrderDetailBo {

    public boolean saveOrderDetail(ArrayList<OrderDetailDTO> dto) throws Exception;

    public boolean saveOrderDetail(OrderDetailDTO dto) throws Exception;

}
