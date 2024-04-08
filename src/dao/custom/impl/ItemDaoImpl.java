package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

public class ItemDaoImpl implements ItemDAO {

    @Override
    public boolean save(Item item) throws Exception {
        return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)",
                item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());

    }

    @Override
    public boolean delete(String code) throws Exception {
        return CrudUtil.execute("DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getCode());
    }

    @Override
    public Item get(String code) throws Exception {

        ResultSet set = CrudUtil.execute("SELECT * From Item WHERE code=?", code);

        if (set.next()) {
            return new Item(
                    set.getString(1), set.getString(2),
                    set.getDouble(3), set.getInt(4));
        }
        return null;
    }

    @Override
    public ArrayList<Item> getAll() throws Exception {

        ResultSet set = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<Item> itemList = new ArrayList<>();

        while (set.next()) {
            itemList.add(new Item(
                    set.getString(1), set.getString(2),
                    set.getDouble(3), set.getInt(4)));
        }
        return itemList;
    }

    @Override
    public ArrayList<Item> getItemCode() throws Exception {

        ResultSet set = CrudUtil.execute("SELECT code FROM Item");
        ArrayList<Item> itemCodeList = new ArrayList<>();

        while (set.next()) {
            itemCodeList.add(new Item(set.getString(1)));
        }
        return itemCodeList;
    }

    @Override
    public boolean updateWhenOrder(Item item) throws Exception {
        return CrudUtil.execute("UPDATE Item SET qtyOnHand = (qtyOnHand - ? ) WHERE code=?",
                item.getQtyOnHand(), item.getCode());
    }

}
