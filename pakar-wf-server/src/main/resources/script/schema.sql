-- public.r_wf_group_transition definition

-- Drop table

-- DROP TABLE public.r_wf_group_transition;

CREATE TABLE public.r_wf_group_transition (
	id int8 NOT NULL,
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	deleted bool NOT NULL,
	modify_by varchar(255) NULL,
	modify_date timestamp NULL,
	rcv_group int8 NULL,
	transition int8 NULL,
	CONSTRAINT r_wf_group_transition_pkey PRIMARY KEY (id)
);


-- public.r_wf_group_transition foreign keys

ALTER TABLE public.r_wf_group_transition ADD CONSTRAINT fkkf5lvvsha2ve07v11eb3j3w5s FOREIGN KEY (rcv_group) REFERENCES r_wf_group(id);
ALTER TABLE public.r_wf_group_transition ADD CONSTRAINT fksf3lkvgvfah6gpw75lqd8ag1a FOREIGN KEY (transition) REFERENCES r_wf_transition(id);