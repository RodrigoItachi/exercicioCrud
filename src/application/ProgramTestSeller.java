package application;

import java.util.List;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

public class ProgramTestSeller {

	public static void main(String[] args) {
		SellerDao sellerDao = FactoryDao.createSellerDao();
		System.out.println("Test 1 - findById");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller);

		System.out.println("Test 2 - findByDepartment");
		Department department = new Department(2, null);
		List<Seller> sellers = sellerDao.findByDepartment(department);
		sellers.forEach(System.out::println);
	}

}