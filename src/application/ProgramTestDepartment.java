package application;

import java.util.List;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entitites.Department;

public class ProgramTestDepartment {

	public static void main(String[] args) {
		System.out.println("Test 1 - findAll");
		DepartmentDao departmentDao = FactoryDao.createDepartmentDao();
		List<Department> departments = departmentDao.findAll();
		departments.forEach(System.out::println);

		System.out.println("Test 2 - findById");
		Department department = departmentDao.findById(2);
		System.out.println(department);

		System.out.println("Test 3 - insert");
//		Department department2 = new Department("House");
//		departmentDao.insert(department2);
//		System.out.println("Id: " + department2.getId());
		System.out.println();

		System.out.println("Test 4 - update");
		department = departmentDao.findById(9);
		System.out.println("Department name before: " + department.getName());
		department.setName("Car");
		departmentDao.update(department);
		System.out.println("Department name after: " + department.getName());

	}

}