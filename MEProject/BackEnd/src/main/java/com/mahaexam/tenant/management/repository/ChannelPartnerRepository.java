package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.ChannelPartner;

public interface ChannelPartnerRepository {
    ChannelPartner save(ChannelPartner channelPartner);
    Optional<ChannelPartner> findById(Long partnerId);
    List<ChannelPartner> findAll();
    ChannelPartner update(ChannelPartner channelPartner);
    void delete(Long partnerId);
    Optional<ChannelPartner> findByUserId(Long userId);
    ChannelPartner registerCP(ChannelPartner channelPartner);
}