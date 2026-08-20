package com.mahaexam.tenant.management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.tenant.management.bean.ChannelPartnerRegistrationBean;
import com.mahaexam.tenant.management.service.ChannelPartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/channelpartner")
public class ChannelPartnerRegistrationController {
	private static final Logger logger = LogManager.getLogger(StudentRegistrationController.class);
	private final ChannelPartnerService channelPartnerService;

	public ChannelPartnerRegistrationController(ChannelPartnerService channelPartnerService) {
		this.channelPartnerService = channelPartnerService;
	}

	@PostMapping("/cpregister")
	public ResponseEntity<SuccessResponseBean> registerChannelPartner(
			@Valid @RequestBody ChannelPartnerRegistrationBean cpregistrationBean) {
		try {
			channelPartnerService.resiterCP(cpregistrationBean,true);
			return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Channel partner registered successfully")
							.build());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

    @PostMapping("/addChannelPartner")
    public ResponseEntity<SuccessResponseBean> addChannelPartner(
            @Valid @RequestBody ChannelPartnerRegistrationBean cpregistrationBean) {
        try {
            cpregistrationBean.setPassword(cpregistrationBean.getRegisteredMobileNumber());
            cpregistrationBean.setReTypePassword(cpregistrationBean.getPassword());
            channelPartnerService.resiterCP(cpregistrationBean,false);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                                    "Channel partner registered successfully")
                            .build());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

}
