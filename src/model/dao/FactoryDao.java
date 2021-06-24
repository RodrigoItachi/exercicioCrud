package model.dao;

import conn.DBConnection;
import model.dao.impl.DepartmentDaoJDBC;

public class FactoryDao {
	public static SellerDao createSellerDao() {
		return null;
	}

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DBConnection.getConnection());
	}
}