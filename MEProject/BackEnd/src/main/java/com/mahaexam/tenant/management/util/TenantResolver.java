package com.mahaexam.tenant.management.util;

import java.util.Objects;

public class TenantResolver {
	public static final Long DEFAULT_TENANT_ID = 101L;

	public static Long resoveTenant(String url) {
		if (Objects.isNull(url)) {
			return DEFAULT_TENANT_ID;
		}
		return DEFAULT_TENANT_ID;
	}
}
