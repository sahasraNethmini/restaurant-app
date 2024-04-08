package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

public class CustomerDaoImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customer) throws Exception {
        return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?)",
                customer.getId(), customer.getName(), customer.getContact());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute("DELETE FROM Customer WHERE id=?", id);
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute("UPDATE Customer SET id=?, name=?, contact=? WHERE id=?",
                customer.getId(), customer.getName(), customer.getContact(), customer.getId());
    }

    @Override
    public Customer get(String id) throws Exception {

        ResultSet set = CrudUtil.execute("SELECT * FROM Customer WHERE id=?", id);

        if (set.next()) {
            return new Customer(
                    set.getString(1), set.getString(2),
                    set.getString(3));
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {

        ResultSet set = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customerList = new ArrayList<>();

        while (set.next()) {
            customerList.add(new Customer(
                    set.getString(1), set.getString(2),
                    set.getString(3)));
        }

        return customerList;
    }

    @Override
    public ArrayList<Customer> getCustomerID() throws Exception {

        ResultSet set = CrudUtil.execute("SELECT id FROM Customer");
        ArrayList<Customer> customerIDList = new ArrayList<>();

        while (set.next()) {
            customerIDList.add(new Customer(set.getString(1)));
        }
        return customerIDList;
    }

}
