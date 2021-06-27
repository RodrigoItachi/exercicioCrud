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
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection connection = null;

	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT seller.*, department.Name AS DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?".trim());
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				Seller seller = instantiateSeller(resultSet, department);
				return seller;
			}
			return null;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeResultSet(resultSet);
			DBConnection.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department newDepartment) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT seller.*, department.Name AS DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? ORDER BY Name");
			preparedStatement.setInt(1, newDepartment.getId());
			resultSet = preparedStatement.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (resultSet.next()) {
				Department department = map.get(resultSet.getInt("DepartmentId"));
				if (department == null) {
					department = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), department);
				}
				Seller seller = instantiateSeller(resultSet, department);
				list.add(seller);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeResultSet(resultSet);
			DBConnection.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("SELECT seller.*, department.Name AS DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name");
			resultSet = preparedStatement.executeQuery();

			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (resultSet.next()) {
				Department department = map.get(resultSet.getInt("DepartmentId"));
				if (department == null) {
					department = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), department);
				}
				Seller seller = instantiateSeller(resultSet, department);
				sellers.add(seller);
			}
			return sellers;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DBConnection.closeResultSet(resultSet);
			DBConnection.closeStatement(preparedStatement);
		}
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		department.setId(resultSet.getInt("Id"));
		department.setName(resultSet.getString("DepName"));
		return department;
	}

	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);
		return seller;
	}
}