package model.dao.Impl;

import db.DB;
import db.DbException;
import model.dao.DepatmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJdbc implements DepatmentDao {
    Connection conn;

    public DepartmentDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected :/");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setInt(2,obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            st.setInt(1,id);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(" SELECT * FROM department Where Id = ?");

            st.setInt(1,id);

            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = new Department(rs.getInt("Id"),rs.getString("Name"));
                return dep;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Department> departamentos = new ArrayList<>();

        try {
            st = conn.prepareStatement(" SELECT * FROM department ");

            rs = st.executeQuery();

            while (rs.next()) {
                Department dep = new Department(rs.getInt("Id"),rs.getString("Name"));
                departamentos.add(dep);
            }
            return departamentos;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
