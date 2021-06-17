INSERT INTO public.oauth_client_details
(client_id, resource_ids, client_secret, "scope", authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES('8C21EBEEB1AA3FBFE05400144FFBD319', '', '$2a$10$g/mjcT64cjnvcwud.LRD8e2iHenTiSyKoUJlqiW99X41zUbzDmduu', 'read,write', 'password,refresh_token', '', '', 43200, 2592000, '{}', 'true');

---- user ---
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','reader', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','editor', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','publisher', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','superadmin', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','admin', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','test', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','guest', 'password', 't');


---- user profile ---
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(1, 'system',now()::DATE, 'f','editor', 'Editor', 'Baru', 'Editor Baru', 'email@local.host', '1234567', 'BCA', 'STAFF', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(2, 'system',now()::DATE, 'f','publisher', 'Publisher', 'Muda', 'Publisher Muda', 'publisher@local.host', '1234567', 'BCA', 'STAFF', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(3, 'system',now()::DATE, 'f','admin', 'Admin', '', 'Admin', 'admin@local.host', '1234567', 'BCA', 'ADMIN', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(4, 'system',now()::DATE, 'f','guest', 'Manu', 'Ginobill', 'Manu G', 'guest@demo.com', '1234567', 'BCA', 'GUEST', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(5, 'system',now()::DATE, 'f','test', 'Putu', 'Sari', 'Putu Ayu Sruti Peramata Sari', 'test@bca.co.id', '1234567', 'BCA', 'STAFF', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(6, 'system',now()::DATE, 'f','superadmin', 'Super', 'Admin', 'Super Admin', 'superadmin@bca.co.id', '1234567', 'BCA', 'ADMIN', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(7, 'system',now()::DATE, 'f','reader', 'Reader', 'Saja', 'Reader Saja', 'reader@bca.co.id', '1234567', 'BCA', 'READER', './assets/media/users/default.jpg');


---- role ---
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','READER', '');
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','EDITOR', '');
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','PUBLISHER', '');
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','SUPERADMIN', '');
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','ADMIN', '');


---- user role ---
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(1, 'system',now()::DATE, 'f','editor', 'EDITOR');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(2, 'system',now()::DATE, 'f','publisher', 'PUBLISHER');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(3, 'system',now()::DATE, 'f','superadmin', 'SUPERADMIN');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(4, 'system',now()::DATE, 'f','test', 'READER');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(5, 'system',now()::DATE, 'f','admin', 'SUPERADMIN');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(6, 'system',now()::DATE, 'f','guest', 'SUPERADMIN');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(6, 'system',now()::DATE, 'f','reader', 'READER');

