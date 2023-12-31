package dao.custom;

import dao.CrudDao;
import dto.EmployeeDto;
import entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao extends CrudDao<Employee> {
     String generateNextEmpId() throws SQLException;
     String splitEmpID(String empId);
    String getEmployee(String empId) throws SQLException;
    boolean updateName(String name, String userId) throws SQLException;
    boolean UpdateNIC(String userId, String nic) throws SQLException;
}
