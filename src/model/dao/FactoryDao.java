package model.dao;

import conn.DBConnection;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class FactoryDao {
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DBConnection.getConnection());
	}

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DBConnection.getConnection());
	}
}