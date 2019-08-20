CREATE TABLE batch_job_instance (
	job_instance_id int8 NOT NULL,
	"version" int8 NULL,
	job_name varchar(100) NOT NULL,
	job_key varchar(32) NOT NULL,
	PRIMARY KEY (job_instance_id),
	UNIQUE (job_name, job_key)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE batch_job_execution (
	job_execution_id int8 NOT NULL,
	"version" int8 NULL,
	job_instance_id int8 NOT NULL,
	create_time timestamp NOT NULL,
	start_time timestamp NULL,
	end_time timestamp NULL,
	status varchar(10) NULL,
	exit_code varchar(2500) NULL,
	exit_message varchar(2500) NULL,
	last_updated timestamp NULL,
	job_configuration_location varchar(2500) NULL,
	PRIMARY KEY (job_execution_id),
	FOREIGN KEY (job_instance_id) REFERENCES batch_job_instance(job_instance_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE batch_job_execution_context (
	job_execution_id int8 NOT NULL,
	short_context varchar(2500) NOT NULL,
	serialized_context text NULL,
	PRIMARY KEY (job_execution_id),
	FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE batch_job_execution_params (
	job_execution_id int8 NOT NULL,
	type_cd varchar(6) NOT NULL,
	key_name varchar(100) NOT NULL,
	string_val varchar(2500) NULL,
	date_val timestamp NULL,
	long_val int8 NULL,
	double_val float8 NULL,
	identifying bpchar(1) NOT NULL,
	FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE batch_step_execution (
	step_execution_id int8 NOT NULL,
	"version" int8 NOT NULL,
	step_name varchar(100) NOT NULL,
	job_execution_id int8 NOT NULL,
	start_time timestamp NOT NULL,
	end_time timestamp NULL,
	status varchar(10) NULL,
	commit_count int8 NULL,
	read_count int8 NULL,
	filter_count int8 NULL,
	write_count int8 NULL,
	read_skip_count int8 NULL,
	write_skip_count int8 NULL,
	process_skip_count int8 NULL,
	rollback_count int8 NULL,
	exit_code varchar(2500) NULL,
	exit_message varchar(2500) NULL,
	last_updated timestamp NULL,
	PRIMARY KEY (step_execution_id),
	FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE batch_step_execution_context (
	step_execution_id int8 NOT NULL,
	short_context varchar(2500) NOT NULL,
	serialized_context text NULL,
	PRIMARY KEY (step_execution_id),
	FOREIGN KEY (step_execution_id) REFERENCES batch_step_execution(step_execution_id)
)
WITH (
	OIDS=FALSE
) ;

-- SEQUENCES

CREATE SEQUENCE batch_job_execution_seq
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1;

CREATE SEQUENCE batch_job_seq
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1;

CREATE SEQUENCE batch_step_execution_seq
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1;



