package bo.custom.impl;

import java.util.ArrayList;

import bo.custom.CustomerBo;
import dao.DaoFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws Exception {
        return dao.save(new Customer(dto.getId(), dto.getName(), dto.getContact()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws Exception {
        return dao.update(new Customer(dto.getId(), dto.getName(), dto.getContact()));
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public CustomerDTO getCustomer(String id) throws Exception {

        Customer customer = dao.get(id);
        if (customer != null) {
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getContact());
        }
        return null;
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {

        ArrayList<Customer> List = dao.getAll();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer c : List) {
            dtoList.add(new CustomerDTO(c.getId(), c.getName(), c.getContact()));
        }
        return dtoList;
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomerID() throws Exception {

        ArrayList<Customer> listID = dao.getCustomerID();
        ArrayList<CustomerDTO> dtoListID = new ArrayList<>();

        for (Customer c : listID) {
            dtoListID.add(new CustomerDTO(c.getId()));
        }
        return dtoListID;
    }

}
