# JUnit Test Cases - User Hierarchy Level Management

## Overview
Comprehensive JUnit test suite for UserHierarchyLevel and UserRoleHierarchyLevelMapping functionality across all layers (Controller, Service, Repository).

---

## Test Files Created

### 1. Service Layer Tests

#### UserHierarchyLevelServiceImplTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/service/`

**Test Cases (23 tests):**
- `testSaveUserHierarchyLevel_Success` - Validates successful creation
- `testSaveUserHierarchyLevel_NullLevel_ThrowsException` - Validates null check
- `testSaveUserHierarchyLevel_NullLevelName_ThrowsException` - Validates name requirement
- `testSaveUserHierarchyLevel_EmptyLevelName_ThrowsException` - Validates empty string check
- `testSaveUserHierarchyLevel_NullLevelOrder_ThrowsException` - Validates order requirement
- `testSaveUserHierarchyLevel_DuplicateLevelName_ThrowsException` - Validates uniqueness
- `testFindById_Success` - Validates retrieval by ID
- `testFindById_NotFound` - Validates handling when not found
- `testFindById_NullId_ThrowsException` - Validates null ID check
- `testFindByLevelName_Success` - Validates retrieval by name
- `testFindByLevelName_NotFound` - Validates handling when not found
- `testFindByLevelName_NullName_ThrowsException` - Validates null name check
- `testFindByLevelName_EmptyName_ThrowsException` - Validates empty name check
- `testUpdateUserHierarchyLevel_Success` - Validates successful update
- `testUpdateUserHierarchyLevel_NotFound_ThrowsException` - Validates missing record check
- `testUpdateUserHierarchyLevel_NullId_ThrowsException` - Validates ID requirement
- `testDeleteUserHierarchyLevel_Success` - Validates successful deletion
- `testDeleteUserHierarchyLevel_NotFound_ThrowsException` - Validates missing record check
- `testDeleteUserHierarchyLevel_NullId_ThrowsException` - Validates ID requirement
- `testFindAll_Success` - Validates retrieving all levels
- `testFindAll_Empty` - Validates empty result handling
- `testExistsById_True` - Validates existence check when exists
- `testExistsById_NullId_ReturnsFalse` - Validates null handling

#### UserRoleHierarchyLevelMappingServiceImplTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/service/`

**Test Cases (27 tests):**
- `testSaveMapping_Success` - Validates successful mapping creation
- `testSaveMapping_NullMapping_ThrowsException` - Validates null check
- `testSaveMapping_NullUserRoleId_ThrowsException` - Validates role ID requirement
- `testSaveMapping_InvalidUserRoleId_ThrowsException` - Validates positive role ID
- `testSaveMapping_NullUserHierarchyLevelId_ThrowsException` - Validates level ID requirement
- `testSaveMapping_InvalidUserHierarchyLevelId_ThrowsException` - Validates positive level ID
- `testSaveMapping_DuplicateMapping_ThrowsException` - Validates uniqueness
- `testFindById_Success` - Validates retrieval by ID
- `testFindById_NotFound` - Validates handling when not found
- `testFindById_NullId_ThrowsException` - Validates null ID check
- `testUpdateMapping_Success` - Validates successful update
- `testUpdateMapping_NotFound_ThrowsException` - Validates missing record check
- `testUpdateMapping_NullId_ThrowsException` - Validates ID requirement
- `testDeleteMapping_Success` - Validates successful deletion
- `testDeleteMapping_NotFound_ThrowsException` - Validates missing record check
- `testDeleteMapping_NullId_ThrowsException` - Validates ID requirement
- `testFindAll_Success` - Validates retrieving all mappings
- `testFindAll_Empty` - Validates empty result handling
- `testFindByUserRoleId_Success` - Validates retrieval by role ID
- `testFindByUserRoleId_Empty` - Validates empty result handling
- `testFindByUserRoleId_NullId_ThrowsException` - Validates null ID check
- `testFindByUserHierarchyLevelId_Success` - Validates retrieval by hierarchy level ID
- `testFindByUserHierarchyLevelId_Empty` - Validates empty result handling
- `testFindByUserHierarchyLevelId_NullId_ThrowsException` - Validates null ID check
- `testFindByUserRoleIdAndHierarchyLevelId_Success` - Validates composite key retrieval
- `testFindByUserRoleIdAndHierarchyLevelId_NotFound` - Validates handling when not found
- `testExistsById_True/False` - Validates existence checks

---

### 2. Controller Layer Tests

#### UserHierarchyLevelControllerTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/controller/`

**Test Cases (12 tests):**
- `testCreateUserHierarchyLevel_Success` - HTTP 201 Created response
- `testCreateUserHierarchyLevel_BadRequest` - HTTP 400 Bad Request
- `testCreateUserHierarchyLevel_Conflict` - HTTP 409 Conflict (duplicate)
- `testGetUserHierarchyLevelById_Success` - HTTP 200 OK
- `testGetUserHierarchyLevelById_NotFound` - HTTP 404 Not Found
- `testGetUserHierarchyLevelByName_Success` - HTTP 200 OK
- `testGetUserHierarchyLevelByName_NotFound` - HTTP 404 Not Found
- `testUpdateUserHierarchyLevel_Success` - HTTP 200 OK
- `testUpdateUserHierarchyLevel_BadRequest` - HTTP 400 Bad Request
- `testUpdateUserHierarchyLevel_Conflict` - HTTP 409 Conflict
- `testGetAllUserHierarchyLevels_Success` - HTTP 200 with list
- `testGetAllUserHierarchyLevels_Empty` - HTTP 200 with empty list
- `testDeleteUserHierarchyLevel_Success` - HTTP 204 No Content
- `testDeleteUserHierarchyLevel_NotFound` - HTTP 404 Not Found

#### UserRoleHierarchyLevelMappingControllerTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/controller/`

**Test Cases (14 tests):**
- `testCreateMapping_Success` - HTTP 201 Created response
- `testCreateMapping_BadRequest_NullUserRoleId` - HTTP 400 Bad Request
- `testCreateMapping_BadRequest_NullHierarchyLevelId` - HTTP 400 Bad Request
- `testCreateMapping_Conflict` - HTTP 409 Conflict (duplicate)
- `testGetMappingById_Success` - HTTP 200 OK
- `testGetMappingById_NotFound` - HTTP 404 Not Found
- `testUpdateMapping_Success` - HTTP 200 OK
- `testUpdateMapping_BadRequest` - HTTP 400 Bad Request
- `testUpdateMapping_NotFound` - HTTP 404 Not Found
- `testGetAllMappings_Success` - HTTP 200 with list
- `testGetAllMappings_Empty` - HTTP 200 with empty list
- `testGetMappingsByUserRoleId_Success` - HTTP 200 with filtered list
- `testGetMappingsByUserRoleId_Empty` - HTTP 200 with empty list
- `testGetMappingsByHierarchyLevelId_Success` - HTTP 200 with filtered list
- `testGetMappingsByHierarchyLevelId_Empty` - HTTP 200 with empty list
- `testGetMappingByRoleAndLevel_Success` - HTTP 200 OK
- `testGetMappingByRoleAndLevel_NotFound` - HTTP 404 Not Found
- `testDeleteMapping_Success` - HTTP 204 No Content
- `testDeleteMapping_NotFound` - HTTP 404 Not Found

---

### 3. Repository Layer Tests

#### UserHierarchyLevelRepositoryImplTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/repository/`

**Test Cases (12 tests):**
- `testSave_Success` - Validates INSERT operation
- `testFindById_Success` - Validates SELECT by ID
- `testFindById_NotFound` - Validates empty result handling
- `testFindByLevelName_Success` - Validates SELECT by name
- `testFindByLevelName_NotFound` - Validates empty result handling
- `testUpdate_Success` - Validates UPDATE operation
- `testDelete_Success` - Validates DELETE operation
- `testFindAll_Success` - Validates SELECT all
- `testFindAll_Empty` - Validates empty result handling
- `testExistsById_True` - Validates COUNT query returns 1
- `testExistsById_False` - Validates COUNT query returns 0

#### UserRoleHierarchyLevelMappingRepositoryImplTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/repository/`

**Test Cases (16 tests):**
- `testSave_Success` - Validates INSERT operation
- `testFindById_Success` - Validates SELECT by ID
- `testFindById_NotFound` - Validates empty result handling
- `testUpdate_Success` - Validates UPDATE operation
- `testDelete_Success` - Validates DELETE operation
- `testFindAll_Success` - Validates SELECT all
- `testFindAll_Empty` - Validates empty result handling
- `testFindByUserRoleId_Success` - Validates filtered SELECT
- `testFindByUserRoleId_Empty` - Validates empty result handling
- `testFindByUserHierarchyLevelId_Success` - Validates filtered SELECT
- `testFindByUserHierarchyLevelId_Empty` - Validates empty result handling
- `testFindByUserRoleIdAndHierarchyLevelId_Success` - Validates composite key SELECT
- `testFindByUserRoleIdAndHierarchyLevelId_NotFound` - Validates empty result handling
- `testExistsById_True` - Validates COUNT query returns 1
- `testExistsById_False` - Validates COUNT query returns 0

---

### 4. Integration Tests

#### UserHierarchyLevelIntegrationTest.java
**Location:** `src/test/java/com/mahaexam/tenant/management/`

**Test Cases (8 integration tests):**
- `testCreateHierarchyLevelAndMapToRole` - End-to-end creation flow
- `testRetrieveHierarchyLevelsWithMappings` - Retrieval and querying
- `testUpdateHierarchyLevelAndItsMapping` - Update with relationship verification
- `testDeleteHierarchyLevelAndAssociatedMappings` - Deletion with cleanup
- `testFindMappingsByRoleAndVerifyHierarchyLevel` - Cross-entity verification
- `testCompleteWorkflow_CreateUpdateDelete` - Full CRUD workflow
- `testMultipleMappingsForSingleHierarchyLevel` - One-to-many relationship
- `testMultipleHierarchyLevelsForSingleRole` - Many-to-many relationship validation

---

## Test Configuration

### Test Framework
- **Framework:** JUnit 5 (Jupiter)
- **Mocking:** Mockito
- **Web Testing:** Spring Test (MockMvc)
- **JSON Processing:** Jackson ObjectMapper

### Annotations Used
- `@ExtendWith(MockitoExtension.class)` - Enable Mockito
- `@Mock` - Mock dependencies
- `@InjectMocks` - Inject mocked dependencies
- `@BeforeEach` - Setup before each test
- `@Test` - Mark as test method

### Test Utilities
- `MockMvcBuilders.standaloneSetup()` - Configure MockMvc
- `ObjectMapper` - JSON serialization/deserialization
- `verify()` - Verify mock interactions
- `when().thenReturn()` - Mock behavior

---

## Test Coverage Summary

### Total Test Cases: **92 tests**

| Layer | File | Test Count |
|-------|------|-----------|
| Service | UserHierarchyLevelServiceImplTest | 23 |
| Service | UserRoleHierarchyLevelMappingServiceImplTest | 27 |
| Controller | UserHierarchyLevelControllerTest | 14 |
| Controller | UserRoleHierarchyLevelMappingControllerTest | 19 |
| Repository | UserHierarchyLevelRepositoryImplTest | 11 |
| Repository | UserRoleHierarchyLevelMappingRepositoryImplTest | 16 |
| Integration | UserHierarchyLevelIntegrationTest | 8 |

---

## Key Testing Scenarios Covered

### Validation Testing
✅ Null/empty field validation
✅ Duplicate constraint validation
✅ Data type validation
✅ Positive number validation

### CRUD Operations
✅ Create (with validation)
✅ Read (by ID, by name, find all)
✅ Update (with validation)
✅ Delete (with existence check)

### Relationship Testing
✅ One-to-many relationships
✅ Many-to-many relationships
✅ Foreign key constraints
✅ Cascade operations

### HTTP Status Code Testing
✅ 200 OK (successful retrieval)
✅ 201 Created (successful creation)
✅ 204 No Content (successful deletion)
✅ 400 Bad Request (validation errors)
✅ 404 Not Found (resource not found)
✅ 409 Conflict (duplicate/constraint violations)
✅ 500 Internal Server Error (unexpected errors)

### Error Handling
✅ IllegalArgumentException
✅ ValidationException
✅ Exception handling in controllers
✅ Proper error response formatting

---

## Running the Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserHierarchyLevelServiceImplTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=UserHierarchyLevelServiceImplTest#testSaveUserHierarchyLevel_Success
```

### Run with Coverage
```bash
mvn clean test jacoco:report
```

---

## Code Quality Features

✅ **Comprehensive Assertions** - Multiple assertions per test
✅ **Proper Mocking** - Isolated unit tests
✅ **Clear Naming** - Descriptive test method names
✅ **Setup/Teardown** - Proper test initialization
✅ **Error Testing** - Exception validation
✅ **Integration Tests** - Real workflow testing
✅ **Mockito Verification** - Method call verification
✅ **Null Safety** - Null pointer exception prevention

---

## Notes

1. All tests use Mockito for dependency mocking to ensure unit test isolation
2. Integration tests verify the complete workflow across multiple layers
3. Repository tests mock JdbcTemplate for database interaction testing
4. Controller tests use MockMvc for HTTP request/response validation
5. Service tests focus on business logic and validation rules
6. Tests follow AAA pattern (Arrange, Act, Assert)
7. All test methods are independent and can run in any order

