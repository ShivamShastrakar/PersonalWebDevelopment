package com.mahaexam.tenant.management.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mahaexam.tenant.management.bean.AddressBean;
import com.mahaexam.tenant.management.model.Address;

public class AddressConverter {
    // Placeholder state mapping (replace with actual service or database lookup)
//    private static final Map<String, Integer> STATE_TO_ID = new HashMap<>();
//    private static final Map<Integer, String> ID_TO_STATE = new HashMap<>();
    
//    static {
//        // Example mappings
//        STATE_TO_ID.put("Maharashtra", 1);
//        STATE_TO_ID.put("Karnataka", 2);
//        STATE_TO_ID.put("Delhi", 3);
//        ID_TO_STATE.put(1, "Maharashtra");
//        ID_TO_STATE.put(2, "Karnataka");
//        ID_TO_STATE.put(3, "Delhi");
//    }

    // Convert AddressBean to Address
    public static Address toAddress(AddressBean addressBean) {
        if (addressBean == null) {
            return null;
        }

        Address address = new Address();
        address.setAddressId(addressBean.getAddressId());
        // Map street to addressText (combine street and country if needed)
        String addressText = addressBean.getAddressText();
        if (addressBean.getCountry() != null) {
            addressText = addressText != null ? addressText + ", " + addressBean.getCountry() : addressBean.getCountry();
        }
        address.setAddressText(addressText);
        // Map city to place
        address.setPlace(addressBean.getPlace());
        // Map state to stateId (use placeholder mapping or null if not found)
        address.setStateId(addressBean.getStateId());
        // Map postalCode to pincode
        address.setPincode(addressBean.getPincode());
        // No equivalent for userId, districtId, talukaId in AddressBean
        address.setUserId(addressBean.getUserId());
        address.setDistrictId(addressBean.getDistrictId());
        address.setTalukaId(addressBean.getTalukaId());
        return address;
    }

    // Convert Address to AddressBean
    public static AddressBean toAddressBean(Address address) {
        if (address == null) {
            return null;
        }

        AddressBean addressBean = new AddressBean();
        addressBean.setAddressId(address.getAddressId());
        // Map addressText to street (assume addressText includes street-level details)
        addressBean.setAddressText(address.getAddressText());
        // Map place to city
        addressBean.setPlace(address.getPlace());
        // Map stateId to state (use placeholder mapping or null if not found)
        addressBean.setStateId(address.getStateId());
        // Map pincode to postalCode
        addressBean.setPincode(address.getPincode());
        // Set country to null (no equivalent in Address)
        addressBean.setCountry(address.getCountry());
        // Set createdAt to null (no equivalent in Address)
        addressBean.setCreatedAt(null);
        addressBean.setUserId(address.getUserId());
        addressBean.setDistrictId(address.getDistrictId());
        addressBean.setTalukaId(address.getTalukaId());
        addressBean.setDistrict(address.getDistrict());
        addressBean.setState(address.getState());
        addressBean.setTaluka(address.getTaluka());
        return addressBean;
    }

    // Convert List<AddressBean> to List<Address>
    public static List<Address> toAddressList(List<AddressBean> addressBeans) {
        if (addressBeans == null) {
            return null;
        }
        return addressBeans.stream()
                .filter(Objects::nonNull)
                .map(AddressConverter::toAddress)
                .collect(Collectors.toList());
    }

    // Convert List<Address> to List<AddressBean>
    public static List<AddressBean> toAddressBeanList(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }
        return addresses.stream()
                .filter(Objects::nonNull)
                .map(AddressConverter::toAddressBean)
                .collect(Collectors.toList());
    }
}