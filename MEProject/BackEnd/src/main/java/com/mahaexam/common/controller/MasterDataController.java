package com.mahaexam.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.District;
import com.mahaexam.common.model.Division;
import com.mahaexam.common.model.State;
import com.mahaexam.common.model.Taluka;
import com.mahaexam.common.model.Zone;
import com.mahaexam.common.service.MasterDataService;

@RestController
@RequestMapping("/api/masterdata")
public class MasterDataController extends BaseController {

	@Autowired
	private MasterDataService masterDataService;

	@PostMapping("/cache/refresh")
	public ResponseEntity<String> refreshCache() {
		UserBean user = getUser();
		masterDataService.refreshAllMasterDataCache(user);
		return ResponseEntity.ok("MasterData cache cleared successfully.");
	}

	// ========== State ==========

	@GetMapping("/states")
	public List<State> findAllstate() {
		return masterDataService.findAllstate();
	}

	@GetMapping("/state/{id}")
	public ResponseEntity<?> getStateById(@PathVariable int id) {
		return masterDataService.getStateById(getUser(), id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/state")
	public ResponseEntity<?> createState(@RequestBody State state) {
		int result = masterDataService.createState(getUser(), state);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PutMapping("/state/{id}")
	public ResponseEntity<?> updateState(@PathVariable int id, @RequestBody State state) {
		state.setId(id);
		int result = masterDataService.updateState(getUser(), state);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/state/{id}")
	public void deleteState(@PathVariable Integer id){
		masterDataService.deleteState(id);
	}

	// ========== Region ==========

	@GetMapping("/divisions")
	public List<Division> findAlldivision() {
		return masterDataService.findAlldivision();
	}

	@GetMapping("/division/{id}")
	public ResponseEntity<?> getRegionById(@PathVariable int id) {
		return masterDataService.getRegionById(getUser(), id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/state/{stateId}/division")
	public ResponseEntity<?> getdivisionByStateId(@PathVariable int stateId) {
		return ResponseEntity.ok(masterDataService.getdivisionByStateId(getUser(), stateId));
	}

	@PostMapping("/divisions")
	public ResponseEntity<?> createRegion(@RequestBody Division region) {
		int result = masterDataService.createRegion(getUser(), region);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PutMapping("/division/{id}")
	public ResponseEntity<?> updateRegion(@PathVariable int id, @RequestBody Division region) {
		region.setId(id);
		int result = masterDataService.updateRegion(getUser(), region);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id){
		masterDataService.delete(id);
	}

	// ========== District ==========
	
	@GetMapping("/districts")
	public List<District> findAllDistricts() {
		return masterDataService.findAllDistrict();
	}

	@GetMapping("/district/{id}")
	public ResponseEntity<?> getDistrictById(@PathVariable int id) {
		return masterDataService.getDistrictById(getUser(), id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/state/{stateId}/district")
	public ResponseEntity<?> getdistrictByStateId(@PathVariable int stateId) {
		return ResponseEntity.ok(masterDataService.getdistrictByStateId(getUser(), stateId));
	}

	@PostMapping("/districts")
	public ResponseEntity<?> createDistrict(@RequestBody District district) {
		int result = masterDataService.createDistrict(getUser(), district);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PutMapping("/district/{id}")
	public ResponseEntity<?> updateDistrict(@PathVariable int id, @RequestBody District district) {
		district.setId(id);
		int result = masterDataService.updateDistrict(getUser(), district);
		return ResponseEntity.ok(result);
	}

	// ========== Taluka ==========
	
	@GetMapping("/talukas")
	public List<Taluka> findAllTalukas() {
		return masterDataService.findAllTaluka();
	}

	
	@GetMapping("/taluka/{id}")
	public ResponseEntity<?> getTalukaById(@PathVariable int id) {
		return masterDataService.getTalukaById(getUser(), id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/district/{districtId}/talukas")
	public ResponseEntity<?> getTalukasByDistrictId(@PathVariable int districtId) {
		return ResponseEntity.ok(masterDataService.getTalukasByDistrictId(getUser(), districtId));
	}

    @GetMapping("/state/{stateId}/talukas")
    public ResponseEntity<?> getTalukasByState(@PathVariable int stateId) {
        return ResponseEntity.ok(masterDataService.getTalukasByStateId(getUser(), stateId));
    }

	@PostMapping("/taluka")
	public ResponseEntity<?> createTaluka(@RequestBody Taluka taluka) {
		int result = masterDataService.createTaluka(getUser(), taluka);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PutMapping("/taluka/{id}")
	public ResponseEntity<?> updateTaluka(@PathVariable int id, @RequestBody Taluka taluka) {
		taluka.setId(id);
		int result = masterDataService.updateTaluka(getUser(), taluka);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/taluka/{id}")
	public void deleteTaluka(@PathVariable Integer id){
		masterDataService.deleteTaluka(id);
	}

	// --- Zone APIs ---

	@PostMapping("/zones")
	public ResponseEntity<Zone> createZone(@RequestBody Zone zone) {
		Zone created = masterDataService.createZone(zone);
		return ResponseEntity.ok(created);
	}

	@GetMapping("/zones/{id}")
	public ResponseEntity<Zone> getZone(@PathVariable int id) {
		return masterDataService.getZoneById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/zones")
	public ResponseEntity<List<Zone>> getAllZones() {
		return ResponseEntity.ok(masterDataService.getAllZones());
	}

	@GetMapping("/zones/tenant/{tenantId}")
	public ResponseEntity<List<Zone>> getZonesByTenant(@PathVariable Long tenantId) {
		return ResponseEntity.ok(masterDataService.getZonesByTenant(tenantId));
	}

	@PutMapping("/zones/{id}")
	public ResponseEntity<Zone> updateZone(@PathVariable int id, @RequestBody Zone zone) {
		zone.setId(id);
		Zone updated = masterDataService.updateZone(zone);
		return ResponseEntity.ok(updated);
	}


    // --- Medium APIs ---
    //api/masterdata/mediums
    @GetMapping("/mediums")
    public ResponseEntity<List<String>> getAllMediums() {
        return ResponseEntity.ok(List.of("English",  "Marathi", "Semi-English"));
    }


}