package dao.custom.Impl;

import dao.SQLUtil;
import dao.custom.CustomerDao;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public String getCustomerId(Customer cus) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT custId FROM customer WHERE name = ?",cus);
        if (resultSet.next()){
            return resultSet.getString("custId");
        }
        else return  "N/A";
    }
    @Override
    public String getCustomerName(String custId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM customer WHERE custId = ?",custId);
        if(resultSet.next()){
            return resultSet.getString("name");
        }
        else return "Not registered";
    }
    @Override
    public String getCustomerId(String text) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT custId FROM customer WHERE name = ?",text);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        else return "Unregistered";
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?)",entity.getCustId(),
                entity.getName(),
                entity.getAddress(),
                entity.getContact());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        Boolean execute = SQLUtil.execute("UPDATE customer SET name = ?,address = ?, contact = ? WHERE custId = ?", entity.getName(), entity.getAddress(), entity.getContact(), entity.getCustId());
        return execute;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE custId = ?",id);
    }
    @Override
    public Customer search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE custId = ?",id);
        Customer entity = null;
        if(resultSet.next()){
            String custId = resultSet.getNString("custId");
            String name = resultSet.getString("name");
            String address = resultSet.getString("Address");
            String contact = resultSet.getString("contact");

            entity = new Customer(custId,name,address,contact);
        }
        return entity;

    }
    @Override
    public Customer searchCustomerByFname(String fname) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE name LIKE ?",(fname+"%"));
        Customer entity = null;
        if(resultSet.next()){
            String custId = resultSet.getNString("custId");
            String name = resultSet.getString("name");
            String address = resultSet.getString("Address");
            String contact = resultSet.getString("contact");

            entity = new Customer(custId,name,address,contact);
        }
        return entity;
    }
    @Override
    public Customer searchCustomerByLname(String Lname) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE name LIKE %?",Lname);
        Customer entity = null;
        if(resultSet.next()){
            String custId = resultSet.getNString("custId");
            String name = resultSet.getString("name");
            String address = resultSet.getString("Address");
            String contact = resultSet.getString("contact");

            entity = new Customer(custId,name,address,contact);
        }
        return entity;
    }
    @Override
    public Customer searchCustomerByContact(String contact) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE contact = ?",contact);
        Customer en = null;
        if(resultSet.next()){
            String cusId = resultSet.getString("custId");
            String name = resultSet.getString("name");
            String address = resultSet.getString("Address");
            en = new Customer(cusId,name,address,contact);
        }
        return en;

    }

    @Override
    public Customer searchCustomerByName(String name) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from customer where name = ?", name);
        if (rst.next()) {
            return new Customer(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4));
        }
        else return null;
    }

    @Override
    public String getCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) as count FROM customer ");
        rst.next();
        return rst.getString(1);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> dto = new ArrayList<>();
        while (resultSet.next()){
            dto.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dto;
    }

}
