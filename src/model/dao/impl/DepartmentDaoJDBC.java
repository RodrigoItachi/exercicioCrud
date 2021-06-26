package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO department (Name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, department.getName());
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					department.setId(id);
				}
				DBConnection.closeResultSet(resultSet);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			preparedStatement.setString(1, department.getName());
			preparedStatement.setInt(2, department.getId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeStatement(preparedStatement);
		}
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