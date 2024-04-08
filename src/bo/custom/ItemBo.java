package bo.custom;

import java.util.ArrayList;
import dto.ItemDTO;
import dto.OrderDetailDTO;

public interface ItemBo {

    public boolean saveItem(ItemDTO itemDTO) throws Exception;

    public boolean updateItem(ItemDTO itemDTO) throws Exception;

    public boolean deleteItem(String code) throws Exception;

    public ItemDTO getItem(String code) throws Exception;

    public ArrayList<ItemDTO> getAllItem() throws Exception;

    public ArrayList<ItemDTO> getAllItemCode() throws Exception;

    public boolean updateWhenOrder(ArrayList<OrderDetailDTO> orderDetailDTOs) throws Exception;

    public boolean updateWhenOrder(OrderDetailDTO orderDetailDTOs) throws Exception;
}
