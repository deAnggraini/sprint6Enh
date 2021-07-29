INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('editor02', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('admin002', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('reader02', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('publish2', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('editor03', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('admin003', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('reader03', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('publish3', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('editor04', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('admin004', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('reader04', 'system', now()::DATE, false, null, null, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('publish4', 'system', now()::DATE, false, null, null, false, '12345678abc');

-- public.user_profile_seq definition

DROP SEQUENCE public.user_profile_seq;

CREATE SEQUENCE public.user_profile_seq
	INCREMENT BY 1
	MINVALUE 5
	MAXVALUE 9223372036854775807
	START 5
	CACHE 1
	NO CYCLE;
	
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'editor02@bca.co.id', 'Editor', 'Editor Pakar', 'Pakar', '', '', '', 'editor02');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'admin002@bca.co.id', 'Administrator', 'Administrator Pakar', 'Pakar', '', '', '', 'admin002');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'reader02@bca.co.id', 'Reader', 'Reader Pakar', 'Pakar', '', '', '', 'reader02');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'publish2@bca.co.id', 'Publisher', 'Publisher Pakar', 'Pakar', '', '', '', 'publish2');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'editor03@bca.co.id', 'Editor', 'Editor Pakar', 'Pakar', '', '', '', 'editor03');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'admin003@bca.co.id', 'Administrator', 'Administrator Pakar', 'Pakar', '', '', '', 'admin003');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'reader03@bca.co.id', 'Reader', 'Reader Pakar', 'Pakar', '', '', '', 'reader03');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'publish3@bca.co.id', 'Publisher', 'Publisher Pakar', 'Pakar', '', '', '', 'publish3');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'editor04@bca.co.id', 'Editor', 'Editor Pakar', 'Pakar', '', '', '', 'editor04');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'admin004@bca.co.id', 'Administrator', 'Administrator Pakar', 'Pakar', '', '', '', 'admin004');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'reader04@bca.co.id', 'Reader', 'Reader Pakar', 'Pakar', '', '', '', 'reader04');

INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, modify_by, modify_date, company_name, email, firstname, fullname, lastname, occupation, phone, pic, username)
VALUES(NEXTVAL('user_profile_seq'), 'system', now()::DATE, false, null, null, 'BCA', 'publish4@bca.co.id', 'Publisher', 'Publisher Pakar', 'Pakar', '', '', '', 'publish4');

-- public.user_role_seq definition

DROP SEQUENCE public.user_role_seq;

CREATE SEQUENCE public.user_role_seq
	INCREMENT BY 1
	MINVALUE 5
	MAXVALUE 9223372036854775807
	START 5
	CACHE 1
	NO CYCLE;

INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'SUPERADMIN', 'admin002');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'READER', 'reader02');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'EDITOR', 'editor02');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'PUBLISHER', 'publish2');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'SUPERADMIN', 'admin003');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'READER', 'reader03');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'EDITOR', 'editor03');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'PUBLISHER', 'publish3');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'SUPERADMIN', 'admin004');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'READER', 'reader04');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'EDITOR', 'editor04');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(NEXTVAL('user_role_seq'), 'system', now()::DATE, false, null, null, 'PUBLISHER', 'publish4');
