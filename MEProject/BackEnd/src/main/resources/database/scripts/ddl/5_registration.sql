--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE application_user (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned,
	user_type VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    gender ENUM('MALE', 'FEMALE', 'OTHER') NULL,
    date_of_birth DATE NULL,
    aadhar_number VARCHAR(25) NULL,
    registered_mobile_number VARCHAR(15) NOT NULL,
    whatsapp_number VARCHAR(15),
    email VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	KEY `user_id` (`user_id`),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE student (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,
    current_class_id INT,
    current_subject_group_id INT,
    target_final_exam_year INT,
    KEY `user_id` (`user_id`),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (current_class_id) REFERENCES `class`(id),
	FOREIGN KEY (current_subject_group_id) REFERENCES `subject_group`(group_id)
);

CREATE TABLE student_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

--- This table can be added later.
CREATE TABLE student_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	student_id BIGINT NOT NULL,
    class_id INT,
    FOREIGN KEY (student_id) REFERENCES student(student_id),
	FOREIGN KEY (class_id) REFERENCES `class`(id)
);

--- This table can be added later.
CREATE TABLE student_subject_group (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	student_id BIGINT NOT NULL,
	subject_group_id INT,
    FOREIGN KEY (student_id) REFERENCES student(student_id),
	FOREIGN KEY (subject_group_id) REFERENCES `subject_group`(group_id)
);



CREATE TABLE address (
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,
    address_text TEXT,
    state_id int(11),
    district_id int(11),
    taluka_id int(11),
    place VARCHAR(100),
    pincode VARCHAR(10),
	KEY `user_id` (`user_id`),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (state_id) REFERENCES state(id),
    FOREIGN KEY (district_id) REFERENCES district(id),
    FOREIGN KEY (taluka_id) REFERENCES taluka(id)
);

CREATE TABLE teacher (
    teacher_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,

    institute_index_number VARCHAR(50),
    in_service BOOLEAN,
    subject_id INT NOT NULL,
    total_experience_years INT NOT NULL,
    area_of_interest VARCHAR(255) NOT NULL,
    online_lecture_taken BOOLEAN,
    qualification VARCHAR(255),
	
  `teaching_experience` int(11) DEFAULT NULL,
  `valuation_experience` int(11) DEFAULT NULL,
  `moderation_experience` int(11) DEFAULT NULL,
  `chef_moderation_experience` int(11) DEFAULT NULL,
  `board_paper_setting_experience` int(11) DEFAULT NULL,
  `MHT_CET_paper_setting_experience`int(11) DEFAULT NULL,
  `NEET_paper_setting_experience` int(11) DEFAULT NULL,
  `JEE_paper_setting_experience` int(11) DEFAULT NULL,
  `KVPY_paper_setting_experience` int(11) DEFAULT NULL,
  `specialty_topics_subjects` text,
  `jee_exp` int(11) DEFAULT NULL,
  `mht_cet_exp` int(11) DEFAULT NULL,
  `neet_exp` int(11) DEFAULT NULL,
  `total_exp`int(11) DEFAULT NULL,
  `individual_ref_code` varchar(255) DEFAULT NULL,
  `ref_code` varchar(250) DEFAULT NULL,
   KEY `user_id` (`user_id`),
   FOREIGN KEY (user_id) REFERENCES users(user_id) 
);


CREATE TABLE channel_partner (
    partner_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,

    company_name VARCHAR(255),
    business_type VARCHAR(100),
    pan_number VARCHAR(20),
    tan_number VARCHAR(20),
    gst_number VARCHAR(20),

    business_exp_years INT,
    service_type VARCHAR(255),
    deeper_association_years INT,
	parent_partner_id BIGINT,
	KEY `user_id` (`user_id`),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (parent_partner_id) REFERENCES channel_partner(partner_id)
);
  

CREATE TABLE bank_account (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,
    account_number VARCHAR(30),
	`account_name` text,
    bank_name VARCHAR(100),
    branch_name VARCHAR(100),
    ifsc_code VARCHAR(20),
   `address` varchar(500) DEFAULT NULL,
   `phone_no` varchar(255) DEFAULT NULL,
   `comments` varchar(500) DEFAULT NULL,
    KEY `user_id` (`user_id`),
   FOREIGN KEY (user_id) REFERENCES users(user_id) 
);


CREATE INDEX idx_user_type ON application_user(user_type);
CREATE INDEX idx_mobile_number ON application_user(registered_mobile_number);
CREATE INDEX idx_email ON application_user(email);
CREATE INDEX idx_student_user_id ON student(user_id);
CREATE INDEX idx_teacher_user_id ON teacher(user_id);




