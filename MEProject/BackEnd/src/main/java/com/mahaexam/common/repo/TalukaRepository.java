package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Taluka;

public interface TalukaRepository {

	List<Taluka> findAll(UserBean user);

	Optional<Taluka> findById(UserBean user,int id);

	List<Taluka> findByDistrictId(UserBean user,int districtId);

	int insert(UserBean user,Taluka taluka);

	int update(UserBean user,Taluka taluka);

	void softDelete(Integer id);

    List<Taluka> getTalukasByStateId(UserBean user, int stateId);

}
