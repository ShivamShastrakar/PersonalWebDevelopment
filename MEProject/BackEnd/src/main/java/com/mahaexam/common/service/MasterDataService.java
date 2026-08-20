package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.District;
import com.mahaexam.common.model.Division;
import com.mahaexam.common.model.State;
import com.mahaexam.common.model.Taluka;
import com.mahaexam.common.model.Zone;

public interface MasterDataService {

	// State
	public List<State> findAllstate();

	Optional<State> getStateById(UserBean user, int id);

	int createState(UserBean user, State state);

	int updateState(UserBean user, State state);

	void deleteState(Integer id);

	// Region
	public List<Division> findAlldivision();

	Optional<Division> getRegionById(UserBean user, int id);

	List<Division> getdivisionByStateId(UserBean user, int stateId);

	int createRegion(UserBean user, Division region);

	int updateRegion(UserBean user, Division region);

	void delete(Integer id);

	// District
	public List<District> findAllDistrict();

	Optional<District> getDistrictById(UserBean user, int id);

	List<District> getdistrictByStateId(UserBean user, int stateId);

	int createDistrict(UserBean user, District district);

	int updateDistrict(UserBean user, District district);

	// Taluka
	public List<Taluka> findAllTaluka();

	Optional<Taluka> getTalukaById(UserBean user, int id);

	List<Taluka> getTalukasByDistrictId(UserBean user, int districtId);

	int createTaluka(UserBean user, Taluka taluka);

	int updateTaluka(UserBean user, Taluka taluka);

	public void refreshAllMasterDataCache(UserBean user);
	
	
	//Zone
	
	Zone createZone(Zone zone);
    Optional<Zone> getZoneById(int id);
    List<Zone> getAllZones();
    List<Zone> getZonesByTenant(Long tenantId);
    Zone updateZone(Zone zone);

    List<Taluka> getTalukasByStateId(UserBean user, int stateId);

	public void deleteTaluka(Integer id);
}
