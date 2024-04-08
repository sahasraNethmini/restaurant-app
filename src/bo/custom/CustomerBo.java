package bo.custom;

import java.util.ArrayList;

import dto.CustomerDTO;

public interface CustomerBo {

    public boolean saveCustomer(CustomerDTO customerDTO) throws Exception;

    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception;

    public boolean deleteCustomer(String id) throws Exception;

    public CustomerDTO getCustomer(String id) throws Exception;

    public ArrayList<CustomerDTO> getAllCustomer() throws Exception;

    public ArrayList<CustomerDTO> getAllCustomerID() throws Exception;
}
