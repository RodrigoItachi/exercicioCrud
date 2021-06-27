package application;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entitites.Seller;

public class ProgramTestSeller {

	public static void main(String[] args) {
		SellerDao sellerDao = FactoryDao.createSellerDao();
		System.out.println("Test 1 - findById");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller);
	}

}