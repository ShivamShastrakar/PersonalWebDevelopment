package com.mahaexam.tenant.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.Role;
import com.mahaexam.tenant.management.repository.ApplicationUserRepository;
import com.mahaexam.tenant.management.repository.RoleRepository;
import com.mahaexam.tenant.management.repository.UserRepository;
import com.mahaexam.tenant.management.repository.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleRepository roleRepository;
	private final ApplicationUserRepository applicationUserRepository;

	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
			RoleRepository roleRepository,ApplicationUserRepository applicationUserRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.roleRepository = roleRepository;
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public UserBean save(UserBean user) {
		validateUser(user);
		if (user.getCreatedAt() == null) {
			user.setCreatedAt(LocalDateTime.now());
		}
		if (user.getIsActive() == null) {
			user.setIsActive(true);
		}
		return userRepository.save(user);
	}

	@Override
	public Optional<UserBean> findById(Long userId, boolean fullData) {
		Optional<UserBean> userBeanOpt = userRepository.findById(userId);
		if(fullData && userBeanOpt.isPresent()) {
			Optional<ApplicationUser> applicationUserOpt = applicationUserRepository.findByUserId(userBeanOpt.get().getUserId());
			ApplicationUser applicationUser = applicationUserOpt
				    .orElseThrow(() -> new IllegalArgumentException("ApplicationUser not found, invalid request."));
			userBeanOpt.get().setApplicationUser(applicationUser);
		}
		return userBeanOpt;
	}

	@Override
	public List<UserBean> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public UserBean update(UserBean user) {
		validateUser(user);
		return userRepository.update(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void delete(Long userId) {
		userRepository.delete(userId);
	}

	@Override
	public Optional<UserBean> findByUsername(String username, boolean fullData) {
//		return userRepository.findByUsername(username);
		Optional<UserBean> userBeanOpt = userRepository.findByUsername(username);
		if(fullData && userBeanOpt.isPresent()) {
			Optional<ApplicationUser> applicationUserOpt = applicationUserRepository.findByUserId(userBeanOpt.get().getUserId());
			ApplicationUser applicationUser = applicationUserOpt
				    .orElseThrow(() -> new IllegalArgumentException("ApplicationUser not found, invalid request."));
			userBeanOpt.get().setApplicationUser(applicationUser);
		}
		return userBeanOpt;
	}

    @Override
    public Optional<UserBean> findByMoblieNo(String mobileNo, boolean fullData) {
        Optional<UserBean> userBeanOpt = userRepository.findByMobileNo(mobileNo);
        if(fullData && userBeanOpt.isPresent()) {
            Optional<ApplicationUser> applicationUserOpt = applicationUserRepository.findByUserId(userBeanOpt.get().getUserId());
            ApplicationUser applicationUser = applicationUserOpt
                    .orElseThrow(() -> new IllegalArgumentException("ApplicationUser not found, invalid request."));
            userBeanOpt.get().setApplicationUser(applicationUser);
        }
        return userBeanOpt;
    }

	@Override
	@Transactional
	public void assignRole(Long userId, Long roleId) {
		if (userId == null || roleId == null) {
			throw new IllegalArgumentException("User ID and Role ID are required");
		}
		if (!userRepository.findById(userId).isPresent()) {
			throw new IllegalArgumentException("User with ID " + userId + " does not exist");
		}
		if (!roleRepository.findById(roleId).isPresent()) {
			throw new IllegalArgumentException("Role with ID " + roleId + " does not exist");
		}
		userRoleRepository.assignRole(userId, roleId);
	}
	
	@Override
	@Transactional
	public void assignRoles(Long userId, List<Long> roleIds) {
		if (userId == null || roleIds == null || roleIds.isEmpty()) {
			throw new IllegalArgumentException("User ID and Role ID are required");
		}
		if (!userRepository.findById(userId).isPresent()) {
			throw new IllegalArgumentException("User with ID " + userId + " does not exist");
		}
		List<Role> roles = roleRepository.findRolesByUserRoleIds(userId,roleIds);
		if (Objects.nonNull(roles) && !roles.isEmpty()) {
			List<String> roleNames = roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
			String message = "Role "+roleNames+" is alreday associated with given user.";
			throw new IllegalArgumentException(message);
		}
		userRoleRepository.assignRoles(userId, roleIds);
	}

    @Override
    public void assignRolesByName(Long userId, List<String> roleNames) {
        if (userId == null || roleNames == null || roleNames.isEmpty()) {
            throw new IllegalArgumentException("User ID and Role ID are required");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User with ID %d does not exist".formatted(userId));
        }
        List<Role> rolesByNames = roleRepository.findRolesByNames(roleNames);
        if(roleNames.size() != rolesByNames.size()) {
            throw new IllegalArgumentException("One of the provided role names id not exists.");
        }
        List<Long> roleIds = rolesByNames.stream()
                .map(Role::getRoleId)
                .distinct()
                .collect(Collectors.toList());
        List<Role> roles = roleRepository.findRolesByUserRoleIds(userId,roleIds);
        if (Objects.nonNull(roles) && !roles.isEmpty()) {
            List<String> roleNamesFromDb = roles.stream()
                    .map(Role::getName).
                    toList();
            String message = "Role %s is already associated with given user.".formatted(roleNamesFromDb);
            throw new IllegalArgumentException(message);
        }
        userRoleRepository.assignRoles(userId, roleIds);
    }

    @Override
	@Transactional
	public void unassignRole(Long userId, Long roleId) {
		if (userId == null || roleId == null) {
			throw new IllegalArgumentException("User ID and Role ID are required");
		}
		userRoleRepository.unassignRole(userId, roleId);
	}

	private void validateUser(UserBean user) {
		if (user.getTenantId() == null) {
			throw new IllegalArgumentException("Tenant ID is required");
		}
		if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
			throw new IllegalArgumentException("Username is required");
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			throw new IllegalArgumentException("Password hash is required");
		}
		// Check username uniqueness
		Optional<UserBean> existingUser = userRepository.findByUsername(user.getUserName());
		if (existingUser.isPresent() && !existingUser.get().getUserId().equals(user.getUserId())) {
			throw new IllegalArgumentException("Username is already taken");
		}
	}
}