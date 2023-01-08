package model.dao;

import db.DB;
import model.dao.Impl.DepartmentDaoJdbc;
import model.dao.Impl.SellerDaoJdbc;
import model.entities.Department;

public class DaoFactory {

    public static SellerDao createSellerDao () {
        return new SellerDaoJdbc(DB.getConnection());
    }

    public static DepatmentDao createDepartmentDao () {
        return new DepartmentDaoJdbc(DB.getConnection());
    }

}
