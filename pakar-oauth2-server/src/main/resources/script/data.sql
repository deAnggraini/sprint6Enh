INSERT INTO public.oauth_client_details
(client_id, resource_ids, client_secret, "scope", authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES('8C21EBEEB1AA3FBFE05400144FFBD319', '', '$2a$10$g/mjcT64cjnvcwud.LRD8e2iHenTiSyKoUJlqiW99X41zUbzDmduu', 'read,write', 'password,refresh_token', '', '', 43200, 2592000, '{}', 'read,write');

---- user ---
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','editor', 'password', 't');

---- user profile ---
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(1, 'system',now()::DATE, 'f','editor', 'Editor', 'Baru', 'Editor Baru', 'email@local.host', '1234567', 'BCA', 'STAFF', './assets/media/users/default.jpg');

---- role ---
INSERT INTO public.r_role
(created_by, created_date, deleted, role_id, description)
VALUES('system',now()::DATE, 'f','EDITOR', '');

---- user role ---
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(1, 'system',now()::DATE, 'f','editor', 'EDITOR');

