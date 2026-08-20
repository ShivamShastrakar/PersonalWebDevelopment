package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.District;
import com.mahaexam.common.model.Division;
import com.mahaexam.common.model.State;
import com.mahaexam.common.model.Taluka;
import com.mahaexam.common.model.Zone;
import com.mahaexam.common.repo.DistrictRepository;
import com.mahaexam.common.repo.DivisionRepository;
import com.mahaexam.common.repo.StateRepository;
import com.mahaexam.common.repo.TalukaRepository;
import com.mahaexam.common.repo.ZoneRepository;

@Service
public class MasterDataServiceImpl implements MasterDataService {

	private final StateRepository stateRepo;
	private final DivisionRepository regionRepo;
	private final DistrictRepository districtRepo;
	private final TalukaRepository talukaRepo;
	private final ZoneRepository zoneRepository;
	private final CacheManager cacheManager;

	public MasterDataServiceImpl(StateRepository stateRepo, DivisionRepository regionRepo,
			DistrictRepository districtRepo, TalukaRepository talukaRepo, ZoneRepository zoneRepository,
			CacheManager cacheManager) {
		this.stateRepo = stateRepo;
		this.regionRepo = regionRepo;
		this.districtRepo = districtRepo;
		this.talukaRepo = talukaRepo;
		this.zoneRepository = zoneRepository;
		this.cacheManager = cacheManager;
	}

	// --------- State -----------

	@Override
	@Cacheable(value = "state", key = "'all'")
	// @Cacheable(value = "state", key = "#user.tenantId + ':all'")
	public List<State> findAllstate() {
		return stateRepo.findAll(null);
	}

	@Override
	@Cacheable(value = "state", key = "#id")
	public Optional<State> getStateById(UserBean user, int id) {
		return stateRepo.findById(user, id);
	}

	@Override
	@CachePut(value = "state", key = "#result.id")
	public int createState(UserBean user, State state) {
		if (stateRepo.existsByStateNameAndTenantIdExceptId(state.getStateName(), user.getTenantId(), -1)) {
			throw new ValidationException("State name already exists for this tenant.");
		}
		// Implement save logic using your repository, e.g. jdbcTemplate insert and
		// return saved entity with id
		return stateRepo.insert(user, state);
	}

	@Override
	@CachePut(value = "state", key = "#state.id")
	public int updateState(UserBean user, State state) {
		if (stateRepo.existsByStateNameAndTenantIdExceptId(state.getStateName(), user.getTenantId(), state.getId())) {
			throw new ValidationException("State name already exists for this tenant.");
		}
		// Implement update logic in repository
		return stateRepo.update(user, state);
	}

	@Override
	public void deleteState(Integer id){
		if (id == null) {
			throw new IllegalArgumentException("State ID is required");
		}
		stateRepo.deleteState(id);
	}
	
	@Override
	public void deleteTaluka(Integer id){
		if (id == null) {
			throw new IllegalArgumentException("Taluka ID is required");
		}
		talukaRepo.softDelete(id);
	}

	// --------- Region -----------

	@Override
	@Cacheable(value = "division", key = "'all'")
	// @Cacheable(value = "state", key = "#user.tenantId + ':all'")
	public List<Division> findAlldivision() {
		return regionRepo.findAll(null);
	}

	@Override
	@Cacheable(value = "division", key = "#id")
	public Optional<Division> getRegionById(UserBean user, int id) {
		return regionRepo.findById(user, id);
	}

	@Override
	@Cacheable(value = "divisionByState", key = "#stateId")
	public List<Division> getdivisionByStateId(UserBean user, int stateId) {
		return regionRepo.findByStateId(user, stateId);
	}

	@Override
	@CachePut(value = "division", key = "#result.id")
	public int createRegion(UserBean user, Division region) {
        region.setDivisionName(region.getDivisionName().toUpperCase());
        Optional<Division> divisionOptional = regionRepo.findByName(user, region.getDivisionName());
        if(divisionOptional.isPresent()){
            throw new IllegalCallerException("Division Name already Exists.");
        }
        return regionRepo.insert(user, region);
	}

	@Override
	@CachePut(value = "division", key = "#region.id")
	public int updateRegion(UserBean user, Division region) {
        region.setDivisionName(region.getDivisionName().toUpperCase());
        if(regionRepo.existsByNameAndIdNot(user, region.getDivisionName(),region.getId().longValue())){
            throw new IllegalCallerException("Division Name already Exists.");
        }
		return regionRepo.update(user, region);
	}

	@Override
	public void delete(Integer id){
		if (id == null) {
			throw new IllegalArgumentException("Division ID is required");
		}
		regionRepo.delete(id);
	}

	// --------- District -----------
	@Override
	@Cacheable(value = "district", key = "'all'")
	// @Cacheable(value = "state", key = "#user.tenantId + ':all'")
	public List<District> findAllDistrict() {
		return districtRepo.findAll(null);
	}

	@Override
	@Cacheable(value = "district", key = "#id")
	public Optional<District> getDistrictById(UserBean user, int id) {
		return districtRepo.findById(user, id);
	}

	@Override
	@Cacheable(value = "districtByState", key = "#stateId")
	public List<District> getdistrictByStateId(UserBean user, int stateId) {
		return districtRepo.findByStateId(user, stateId);
	}

	@Override
	@CachePut(value = "district", key = "#result.id")
	public int createDistrict(UserBean user, District district) {
		return districtRepo.save(user, district) ;
		
	}

	@Override
	@CachePut(value = "district", key = "#district.id")
	public int updateDistrict(UserBean user, District district) {
		return districtRepo.update(user, district);
	}

	// --------- Taluka -----------
	@Override
	@Cacheable(value = "talukas", key = "'all'")
	// @Cacheable(value = "state", key = "#user.tenantId + ':all'")
	public List<Taluka> findAllTaluka() {
		return talukaRepo.findAll(null);
	}

	@Override
	@Cacheable(value = "talukas", key = "#id")
	public Optional<Taluka> getTalukaById(UserBean user, int id) {
		return talukaRepo.findById(user, id);
	}

	@Override
	@Cacheable(value = "talukasByDistrict", key = "#districtId")
	public List<Taluka> getTalukasByDistrictId(UserBean user, int districtId) {
		return talukaRepo.findByDistrictId(user, districtId);
	}
    @Override
    public List<Taluka> getTalukasByStateId(UserBean user, int stateId) {
        return talukaRepo.getTalukasByStateId(user, stateId);
    }
	@Override
	@CachePut(value = "talukas", key = "#result.id")
	public int createTaluka(UserBean user, Taluka taluka) {
		return talukaRepo.insert(user, taluka);
	}

	@Override
	@CachePut(value = "talukas", key = "#taluka.id")
	public int updateTaluka(UserBean user, Taluka taluka) {
		return talukaRepo.update(user, taluka);
	}

	// Zone

	@Override
	public Zone createZone(Zone zone) {
		return zoneRepository.save(zone);
	}

	@Override
	public Optional<Zone> getZoneById(int id) {
		return zoneRepository.findById(id);
	}

	@Override
	public List<Zone> getAllZones() {
		return zoneRepository.findAll();
	}

	@Override
	public List<Zone> getZonesByTenant(Long tenantId) {
		return zoneRepository.findByTenant(tenantId);
	}

	@Override
	public Zone updateZone(Zone zone) {
		zoneRepository.update(zone);
		return zone;
	}



    // 🔁 Manual cache refresh
	public void refreshAllMasterDataCache(UserBean user) {
		// 1. Evict all caches
		evictAllCaches();

		// 2. Warm up (reload) cache
		List<State> states = stateRepo.findAll(user);
		states.forEach(state -> this.getStateById(user, state.getId()));

		states.forEach(state -> {
			List<Division> division = regionRepo.findByStateId(user, state.getId());
			division.forEach(region -> this.getRegionById(user, region.getId()));

			List<District> districts = districtRepo.findByStateId(user, state.getId());
			districts.forEach(district -> {
				this.getDistrictById(user, district.getId());
				List<Taluka> talukas = talukaRepo.findByDistrictId(user, district.getId());
				talukas.forEach(taluka -> this.getTalukaById(user, taluka.getId()));
			});
		});
	}

	private void evictAllCaches() {
		for (String cacheName : cacheManager.getCacheNames()) {
			cacheManager.getCache(cacheName).clear();
		}
	}
}
