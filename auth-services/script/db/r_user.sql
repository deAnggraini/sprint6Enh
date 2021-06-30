INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('admin123', 'system', now()::DATE, false, 'system', now()::DATE, false, '12345678');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('reader12', 'system', now()::DATE, false, 'system', now()::DATE, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('editor12', 'system', now()::DATE, false, 'system', now()::DATE, false, '12345678abc');

INSERT INTO public.r_user
(username, created_by, created_date, deleted, modify_by, modify_date, enabled, "password")
VALUES('publish1', 'system', now()::DATE, false, 'system', now()::DATE, false, '12345678abc');
