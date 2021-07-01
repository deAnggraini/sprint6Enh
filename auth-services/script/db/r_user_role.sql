INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, 'SUPERADMIN', 'admin123');

INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, 'READER', 'reader12');

INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(3, 'system', now()::DATE, false, 'system', now()::DATE, 'EDITOR', 'editor12');

INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, modify_by, modify_date, role_id, username)
VALUES(4, 'system', now()::DATE, false, 'system', now()::DATE, 'PUBLISHER', 'publish1');
COMMIT;