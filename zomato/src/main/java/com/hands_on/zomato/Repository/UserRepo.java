package com.hands_on.zomato.Repository;

import com.hands_on.zomato.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {

}