package dao.custom;

import java.util.ArrayList;

import dao.CrudDAO;
import entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    // custom data
    public ArrayList<Customer> getCustomerID() throws Exception;

}
