package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.bean.ChannelPartnerRegistrationBean;
import com.mahaexam.tenant.management.model.ChannelPartner;

public interface ChannelPartnerService {
    ChannelPartner save(ChannelPartnerRegistrationBean channelPartnerRegBean);
    Optional<ChannelPartner> findById(Long partnerId);
    List<ChannelPartner> findAll();
    ChannelPartner update(ChannelPartner channelPartner);
    void delete(Long partnerId);
    Optional<ChannelPartner> findByUserId(Long userId);
	void resiterCP(ChannelPartnerRegistrationBean channelPartnerRegistrationBean, Boolean validateOtp);
}