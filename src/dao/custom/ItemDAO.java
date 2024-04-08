package dao.custom;

import java.util.ArrayList;

import dao.CrudDAO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item, String> {

    public ArrayList<Item> getItemCode() throws Exception;

    public boolean updateWhenOrder(Item item) throws Exception;

}
