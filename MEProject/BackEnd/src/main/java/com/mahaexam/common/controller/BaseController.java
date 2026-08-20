package com.mahaexam.common.controller;

import java.util.Map;

import com.mahaexam.tenant.management.util.TenantResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private ServletContext servletContext;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<Void> getResponseEntity(Map<String, Object> jsonResponse) {
		return new ResponseEntity(jsonResponse, HttpStatus.OK);
	}

	public UserBean getUser() {
		HttpSession session = request.getSession(true);
        return (UserBean) session.getAttribute(AppConstants.USER_CONTEXT);
	}

    public Long getCurrentTenantId(){
        String referer = request.getHeader("referer");
        return TenantResolver.resoveTenant(referer);
    }

    public long parseFileSize(String fileSize) {
        fileSize = fileSize.toUpperCase();
        if (fileSize.endsWith("MB")) {
            return Long.parseLong(fileSize.replace("MB", "").trim()) * 1024 * 1024;
        } else if (fileSize.endsWith("KB")) {
            return Long.parseLong(fileSize.replace("KB", "").trim()) * 1024;
        } else {
            return Long.parseLong(fileSize.trim());
        }
    }

}
