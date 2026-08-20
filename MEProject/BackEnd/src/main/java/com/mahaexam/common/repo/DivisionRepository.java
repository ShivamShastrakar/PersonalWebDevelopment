package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Division;

public interface DivisionRepository {
    List<Division> findAll(UserBean user);
    Optional<Division> findById(UserBean user,Integer id);
    Optional<Division> findByName(UserBean user, String name);
    List<Division> findByStateId(UserBean user,int stateId);
    int insert(UserBean user,Division region);
    int update(UserBean user,Division region);
    void delete(Integer id);
    boolean existsByNameAndIdNot(UserBean user, String name, Long divisionId);

}