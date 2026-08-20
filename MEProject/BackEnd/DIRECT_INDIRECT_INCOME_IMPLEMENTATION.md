# Direct and Indirect Income Processing Implementation

## Overview
A complete system for processing Direct Income and Indirect Income across 4 levels of downline hierarchy with commission calculations based on package types and referral counts.

## Implementation Summary

### 1. Database Layer Updates

#### ApplicationUserRepository (Updated)
- **New Method Added:** `findByUserParentId(Long userParentId)`
- **Purpose:** Fetch all child users for a given parent user ID
- **Location:** `com.mahaexam.tenant.management.repository.ApplicationUserRepository`

#### ApplicationUserRepositoryImpl (Updated)
- **Implementation Added:** SQL query to fetch users where `user_parent_id` matches the given userId
- **Query:** `SELECT * FROM application_user WHERE user_parent_id = ? AND deleted = '0'`

---

### 2. Bean Layer (New Classes Created)

#### IndirectEarningDetailBean
**Purpose:** Represents earning details for a single user within a downline level
**Fields:**
- `userId`: Long - User identifier
- `userName`: String - Username
- `userFullName`: String - Full name of user
- `totalReferrals`: int - Total students referred by this user
- `totalEarning`: BigDecimal - Total commission earned
- `breakdown`: List<EarningBreakdownBean> - Package-wise commission breakdown

**Location:** `com.mahaexam.tenant.management.bean.IndirectEarningDetailBean`

---

#### DownLineLevelEarningBean
**Purpose:** Consolidates earnings for all users in a specific downline level
**Fields:**
- `levelNumber`: Integer - Level number (1, 2, 3, or 4)
- `levelName`: String - Human-readable level name ("Level 1", "Level 2", etc.)
- `totalDownlineUsers`: int - Count of users in this level
- `totalReferralsAcrossLevel`: int - Total referrals across all users in level
- `totalEarningForLevel`: BigDecimal - Total commission for entire level
- `userEarnings`: List<IndirectEarningDetailBean> - List of individual user earnings

**Location:** `com.mahaexam.tenant.management.bean.DownLineLevelEarningBean`

---

#### IndirectIncomeEarningBean
**Purpose:** Complete income summary combining direct and all indirect (downline) earnings
**Fields:**
- `userId`: Long - User identifier
- `userName`: String - Username
- `userFullName`: String - Full name
- `directEarning`: BigDecimal - Direct earnings from own referrals
- `indirectEarning`: BigDecimal - Total indirect earnings from all 4 downline levels
- `totalEarning`: BigDecimal - Sum of direct + indirect earnings
- `downlineLevels`: List<DownLineLevelEarningBean> - Breakdown by each downline level

**Location:** `com.mahaexam.tenant.management.bean.IndirectIncomeEarningBean`

---

### 3. Service Layer Updates

#### EarningService Interface (Updated)
**Package:** `com.mahaexam.tenant.management.service`

**Methods:**
1. `computeEarningSummary(Long channelPartnerId)` - Existing method for direct earnings
2. `computeIndirectEarning(Long userId)` - **NEW** Method for indirect earnings across all 4 levels

---

#### EarningServiceImpl (Updated)
**Package:** `com.mahaexam.tenant.management.service`

**New Dependencies Injected:**
- `ApplicationUserRepository` - For fetching downline users
- `UserHierarchyLevelService` - For getting hierarchy levels
- `ApplicationUserService` - For user details

**New Methods Implemented:**

##### `computeIndirectEarning(Long userId)`
**Purpose:** Main method to compute both direct and indirect earnings
**Process:**
1. Validates user exists
2. Fetches direct earnings using `computeEarningSummary()`
3. Retrieves all 4 levels of downline users
4. Processes earnings for each downline level
5. Consolidates all data into IndirectIncomeEarningBean

**Returns:** `IndirectIncomeEarningBean` with complete income breakdown

---

##### `getAllDownlineLevel1(Long userId)`
**Purpose:** Fetch direct children (Level 1 downline)
**Process:**
- Calls `applicationUserRepository.findByUserParentId(userId)`
- Returns all users where `user_parent_id = userId`

**Returns:** `List<ApplicationUser>`

---

##### `getAllDownlineLevel2(List<ApplicationUser> downline1)`
**Purpose:** Fetch Level 2 downline (children of Level 1)
**Process:**
1. Iterates through each Level 1 user
2. For each user, fetches their children
3. Collects all children into single list

**Returns:** `List<ApplicationUser>`

---

##### `getAllDownlineLevel3(List<ApplicationUser> downline2)`
**Purpose:** Fetch Level 3 downline (children of Level 2)
**Process:** Same as Level 2 - iterates and collects children
**Returns:** `List<ApplicationUser>`

---

##### `getAllDownlineLevel4(List<ApplicationUser> downline3)`
**Purpose:** Fetch Level 4 downline (children of Level 3)
**Process:** Same as Level 2 & 3 - iterates and collects children
**Returns:** `List<ApplicationUser>`

---

##### `processDownlineLevel(Integer levelNumber, List<ApplicationUser> downlineUsers)`
**Purpose:** Calculate earnings for all users in a specific downline level
**Process:**
1. For each user in the level:
   - Fetches students referred by the user
   - Gets commission configs for the user's hierarchy level
   - Calculates commission based on package types
   - Creates IndirectEarningDetailBean for the user
2. Consolidates into DownLineLevelEarningBean with:
   - Total users in level
   - Total referrals across level
   - Total earning for level
   - Individual user earnings

**Returns:** `DownLineLevelEarningBean`

---

##### `getCommissionConfigsByPackageType(Long userId)`
**Purpose:** Fetch and map commission configs by package type
**Process:**
1. Fetches user's hierarchy levels from `UserHierarchyLevelService`
2. For each hierarchy level, fetches commission configs
3. Maps configs by package type for quick lookup

**Returns:** `Map<String, CommissionConfigRequest>`

---

##### `processUserPackagesAndCommission(List<StudentDetailsBean> students, Map<String, CommissionConfigRequest> configByPackageType)`
**Purpose:** Calculate total commission for all packages of a user's referrals
**Process:**
1. Groups packages by type and student:
   ```
   packagesByTypeAndStudent = {
       "packageType1": {
           "studentId1": [package1, package2],
           "studentId2": [package3]
       }
   }
   ```
2. For each package type:
   - Counts referrals
   - Calculates total package amount
   - Finds applicable commission slab based on referral count
   - Calculates commission (PERCENTAGE or FIXED)
3. Sums commission across all package types

**Returns:** `EarningBreakdownBean` with consolidated commission

---

### 4. Controller Layer Updates

#### EarningController (Updated)
**Package:** `com.mahaexam.tenant.management.controller`
**Base Path:** `/api/earnings`

**New API Endpoints:**

##### 1. GET `/api/earnings/direct/{userId}`
**Purpose:** Get direct earnings for a user (earnings from own referrals)
**Request Parameter:** 
- `userId` (Path Variable) - Long - User ID
**Response:** `EarningSummaryBean`
**Status Codes:**
- 200 OK - Success
- 400 Bad Request - Invalid userId
**Example:**
```
GET /api/earnings/direct/123
Response:
{
    "channelPartnerId": 123,
    "totalReferrals": 5,
    "totalEarning": 50000.00,
    "breakdown": [...]
}
```

---

##### 2. GET `/api/earnings/indirect/{userId}`
**Purpose:** Get indirect earnings (4 levels of downline) + direct earnings
**Request Parameter:**
- `userId` (Path Variable) - Long - User ID
**Response:** `IndirectIncomeEarningBean`
**Status Codes:**
- 200 OK - Success
- 400 Bad Request - Invalid userId
**Example:**
```
GET /api/earnings/indirect/123
Response:
{
    "userId": 123,
    "userName": "john_doe",
    "userFullName": "John Doe",
    "directEarning": 50000.00,
    "indirectEarning": 125000.00,
    "totalEarning": 175000.00,
    "downlineLevels": [
        {
            "levelNumber": 1,
            "levelName": "Level 1",
            "totalDownlineUsers": 5,
            "totalReferralsAcrossLevel": 25,
            "totalEarningForLevel": 50000.00,
            "userEarnings": [...]
        },
        {
            "levelNumber": 2,
            "levelName": "Level 2",
            "totalDownlineUsers": 15,
            "totalReferralsAcrossLevel": 45,
            "totalEarningForLevel": 40000.00,
            "userEarnings": [...]
        },
        ...
    ]
}
```

---

## Data Flow Diagram

```
User (userId)
    ↓
computeIndirectEarning(userId)
    ↓
├─→ [Direct Earning] computeEarningSummary(userId)
    ↓
├─→ [Downline Level 1] getAllDownlineLevel1(userId)
│   └─→ processDownlineLevel(1, downline1List)
│       ├─→ For each user in level:
│       │   ├─→ Get referred students
│       │   ├─→ Get commission configs
│       │   └─→ Calculate commission
│       └─→ Consolidate to DownLineLevelEarningBean
    ↓
├─→ [Downline Level 2] getAllDownlineLevel2(downline1List)
│   └─→ processDownlineLevel(2, downline2List)
    ↓
├─→ [Downline Level 3] getAllDownlineLevel3(downline2List)
│   └─→ processDownlineLevel(3, downline3List)
    ↓
├─→ [Downline Level 4] getAllDownlineLevel4(downline3List)
│   └─→ processDownlineLevel(4, downline4List)
    ↓
└─→ Consolidate all levels
    ↓
    Return IndirectIncomeEarningBean
```

---

## Commission Calculation Logic

For each downline user's referrals:

1. **Group Packages by Type:**
   - Collect all packages purchased by referred students
   - Group by package type

2. **For Each Package Type:**
   - Count referrals for that package type
   - Sum total package amount
   - Find applicable commission slab:
     ```
     if (referralCount >= slab.fromCount && referralCount <= slab.toCount)
         applySlab()
     ```

3. **Calculate Commission:**
   - **If PERCENTAGE:** `commission = totalAmount × (percentage/100)`
   - **If FIXED:** `commission = fixedAmount × referralCount`

4. **Consolidate:**
   - Sum commission across all package types
   - Store in EarningBreakdownBean

---

## Error Handling

All methods include:
- Null/empty list checks
- User existence validation
- Try-catch blocks for package processing
- Logging at each step
- Descriptive error messages

**Validation Exceptions:**
- Invalid User ID (≤ 0)
- User not found
- Invalid commission configurations

---

## Performance Considerations

1. **Database Queries:** 
   - Uses indexed queries on `user_parent_id`
   - Caches commission configs in memory

2. **Batch Processing:**
   - Processes downline users in batches
   - Handles large downline structures efficiently

3. **Error Resilience:**
   - Continues processing even if one user fails
   - Logs individual errors without stopping entire process

---

## Files Modified/Created

### New Files (3):
1. `IndirectEarningDetailBean.java`
2. `DownLineLevelEarningBean.java`
3. `IndirectIncomeEarningBean.java`

### Updated Files (5):
1. `EarningService.java` - Added new method signature
2. `EarningServiceImpl.java` - Added implementation (400+ lines)
3. `ApplicationUserRepository.java` - Added new method
4. `ApplicationUserRepositoryImpl.java` - Added implementation
5. `EarningController.java` - Added 2 new API endpoints

---

## Testing Recommendations

### Test Cases:
1. **Direct Earnings API:**
   - Valid userId → Returns EarningSummaryBean
   - Invalid userId → Returns 400 Bad Request
   - User with no referrals → Returns 0 earnings

2. **Indirect Earnings API:**
   - User with multiple downline levels → Returns complete hierarchy
   - User with no downline → Returns only direct earnings
   - Different package types → Correctly calculates per-type commission

3. **Downline Fetching:**
   - Correctly identifies all 4 levels
   - Handles gaps in hierarchy
   - Prevents circular references

4. **Commission Calculation:**
   - PERCENTAGE commission calculated correctly
   - FIXED commission calculated correctly
   - Slab matching works accurately

---

## Usage Examples

### Example 1: Get Direct Earning
```bash
curl -X GET "http://localhost:8080/api/earnings/direct/123"
```

### Example 2: Get Indirect Earning with All Levels
```bash
curl -X GET "http://localhost:8080/api/earnings/indirect/123"
```

### Example 3: Client-Side Processing
```javascript
// Fetch indirect earnings
const response = await fetch('/api/earnings/indirect/123');
const earnings = await response.json();

// Access by level
earnings.downlineLevels.forEach(level => {
    console.log(`Level ${level.levelNumber}: ${level.totalEarningForLevel}`);
});

// Calculate total
const totalEarning = earnings.totalEarning;
```

---

## Conclusion

This implementation provides a complete, hierarchical earning calculation system that:
- ✅ Processes direct earnings from own referrals
- ✅ Processes indirect earnings across 4 downline levels
- ✅ Supports multiple commission types (PERCENTAGE, FIXED)
- ✅ Includes comprehensive error handling and logging
- ✅ Exposes RESTful APIs for client consumption
- ✅ Handles complex package type mappings
- ✅ Maintains data integrity and consistency
