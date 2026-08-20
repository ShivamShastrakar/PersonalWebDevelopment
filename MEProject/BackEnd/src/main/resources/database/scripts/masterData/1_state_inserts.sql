--liquibase formatted sql
--changeset {narendra}:{id}
INSERT INTO state (state_name,state_alias_name,deleted_at,deleted) VALUES
	 ('ANDAMAN AND NICOBARR','AN',NULL,'0'),
	 ('ANDHRA PRADESH','AP',NULL,'0'),
	 ('ARUNACHAL PRADESH','AR',NULL,'0'),
	 ('ASSAM','AS',NULL,'0'),
	 ('BIHAR','BR',NULL,'0'),
	 ('CHANDIGARH','CH' ,NULL,'0'),
	 ('CHHATTISGARH','CG' ,NULL,'0'),
	 ('DADRA AND NAGAR HAVELI','DN' ,NULL,'0'),
	 ('DAMAN AND DIU','DD' ,NULL,'0'),
	 ('DELHI','DL' ,NULL,'0');
INSERT INTO state (state_name,state_alias_name, deleted_at,deleted) VALUES
	 ('GOA','GA' ,NULL,'0'),
	 ('GUJARAT','GJ' ,NULL,'0'),
	 ('HARYANA','HR' ,NULL,'0'),
	 ('HIMACHAL PRADESH','HP' ,NULL,'0'),
	 ('JAMMU AND KASHMIR','JK' ,NULL,'0'),
	 ('JHARKHAND','JH' ,NULL,'0'),
	 ('KARNATAKA','KA' ,NULL,'0'),
	 ('KERALA','KL' ,NULL,'0'),
	 ('LAKSHDWEEP','LD' ,NULL,'0'),
	 ('MADHYA PRADESH','MP' ,NULL,'0');
INSERT INTO state (state_name,state_alias_name, deleted_at,deleted) VALUES
	 ('MAHARASHTRA','MH' ,NULL,'0'),
	 ('MANIPUR','MN' ,NULL,'0'),
	 ('MEGHALAYA','ML' ,NULL,'0'),
	 ('MIZORAM','MZ' ,NULL,'0'),
	 ('NAGALAND','NL' ,NULL,'0'),
	 ('ODISHA','OD' ,NULL,'0'),
	 ('PUDUCHERRY','PY' ,NULL,'0'),
	 ('PUNJAB','PB' ,NULL,'0'),
	 ('RAJASTHAN','RJ' ,NULL,'0'),
	 ('SIKKIM','SK' ,NULL,'0');
INSERT INTO state (state_name,state_alias_name, deleted_at,deleted) VALUES
	 ('TAMIL NADU','TN' ,NULL,'0'),
	 ('TRIPURA','TR' ,NULL,'0'),
	 ('UTTAR PRADESH','UP' ,NULL,'0'),
	 ('UTTARAKHAND','UK' ,NULL,'0'),
	 ('WEST BENGAL','WB' ,NULL,'0');
