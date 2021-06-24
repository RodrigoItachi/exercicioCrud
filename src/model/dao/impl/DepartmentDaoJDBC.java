package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conn.DBConnection;
import dbUtil.DbException;
import model.dao.DepartmentDao;
import model.entitites.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection connection = null;

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM department WHERE department.Id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				return department;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeResultSet(resultSet);
			DBConnection.closeStatement(preparedStatement);
		}
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM department ORDER BY Name");
			resultSet = preparedStatement.executeQuery();
			List<Department> lisrDepartments = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (resultSet.next()) {
				Department department = map.get(resultSet.getInt("Id"));
				if (department == null) {
					department = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("Id"), department);
				}
				lisrDepartments.add(department);
			}
			return lisrDepartments;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeResultSet(resultSet);
			DBConnection.closeStatement(preparedStatement);
		}
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		department.setId(resultSet.getInt("Id"));
		department.setName(resultSet.getString("Name"));
		return department;
	}
}