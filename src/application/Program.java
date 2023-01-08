package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(4);
        System.out.println(seller);

        System.out.println("");
        Department department = new Department(3,null);
        List<Seller> list = sellerDao.findByDepartment(department);

        for (Seller obj:
             list) {
            System.out.println(obj);
        }

        System.out.println("");
        list = sellerDao.findAll();

        for (Seller obj:
                list) {
            System.out.println(obj);
        }
    }

    private static void listarDados () {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select * from seller");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getString("email") + " - " + rs.getString("birthDate") + " - " + rs.getString("baseSalary") + " - " + rs.getString("departmentId"));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
//            DB.closeResultSet(rs);
//            DB.closeStatment(st);
//            DB.closeConnection();
        }

    }

    private static void inserirDados () {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();

            ps = conn.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId ) " +
                    "values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,"Jose");
            ps.setString(2,"jose@yahoo.com");
            ps.setDate(3,new java.sql.Date(sdf.parse("29/11/1972").getTime()));
            ps.setDouble(4,10400.0);
            ps.setInt(5,3);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Id = " + id);
                }

            } else {

            }


        } catch (SQLException | ParseException e) {

            throw new DbException(e.getMessage());

        } finally {

            //DB.closeConnection();
        }

    }

    private static void atualizarDados () {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();

            ps =  conn.prepareStatement("update seller set Name = ? where Id = ?");
            ps.setString(1,"Nome mudado");
            ps.setInt(2,10);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static void deletarDados (){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();

            ps =  conn.prepareStatement("delete from department where Id = ?");
            ps.setInt(1,1);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
    }

}
