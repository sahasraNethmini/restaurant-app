package dao.custom;

import java.util.ArrayList;

import dao.CrudDAO;
import entity.Order;

public interface OrderDAO extends CrudDAO<Order, String> {

 public ArrayList<Order> getOrderID() throws Exception;
 
}
