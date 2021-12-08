package mx.edu.utez.controller;

import mx.edu.utez.database.ConnectionMysql;
import mx.edu.utez.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/employee")
public class Service {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    Statement statement;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)

    public List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();

        Employee employee = new Employee();
        employee.setEmployeeNumber(1);
        employee.setFirstName("Luis");
        employees.add(employee);

        employee = new Employee();
        employee.setEmployeeNumber(2);
        employee.setFirstName("Alejandro");
        employees.add(employee);
        return employees;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id") int employeeNumber){
        Employee employee = new Employee();
        if(employeeNumber != 0)
            employee.setFirstName("Alejandro");
        return employee;
    }

    //employees   employeeNumber , lastName , firstName , extension , email , officeCode , reportsTo , jobTitle

    @POST
    @Path("/createemp")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createUser (Employee emp){
        boolean state = false;
        try {
            con = ConnectionMysql.getConnection();
            String query = "INSERT INTO `employees`(lastName,firstName,extension,email,officeCode,reportsTo,jobTitle)VALUES(?,?,?,?,?,?,?);";
            pstm = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1,emp.getLastName());
            pstm.setString(2,emp.getFirstName());
            pstm.setString(3,emp.getExtension());
            pstm.setString(4,emp.getEmail());
            pstm.setInt(5,emp.getOfficeCode());
            pstm.setInt(6,emp.getReportsTo());
            pstm.setString(7,emp.getJobTitle());
            state = pstm.executeUpdate() == 1;
            rs = pstm.getGeneratedKeys();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }
    @PUT
    @Path("/modifyemp")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean modifyPerson (Employee emp){
        boolean state = false;
        try {
            con = ConnectionMysql.getConnection();
            String query = "UPDATE employees SET lastName=?,firstName=?,extension=?,email=?,officeCode=?,reportsTo=?,jobTitle=? where  employeeNumber = ?;";
            pstm = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1,emp.getLastName());
            pstm.setString(2,emp.getFirstName());
            pstm.setString(3,emp.getExtension());
            pstm.setString(4,emp.getEmail());
            pstm.setInt(5,emp.getOfficeCode());
            pstm.setInt(6,emp.getReportsTo());
            pstm.setString(7,emp.getJobTitle());
            state = pstm.executeUpdate() == 1;
            rs = pstm.getGeneratedKeys();

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }
    @DELETE
    @Path("/deleteemp")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deletePerson (int id){
        boolean state = false;
        try {
            con = ConnectionMysql.getConnection();
            String query = "DELETE FROM employees WHERE employeeNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1,id);
            state = pstm.executeUpdate() == 1;

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }
    }


}
