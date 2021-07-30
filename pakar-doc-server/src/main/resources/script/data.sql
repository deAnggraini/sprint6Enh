---- article Template ---
INSERT INTO public.t_article_template
(id, created_by, created_date, deleted, modify_by, modify_date, description, template_name, structure_id)
VALUES(1, 'system',now()::DATE, 'f','system',now()::DATE, 'Pilih template ini jika template yang direkomendasikan kurang sesuai dengan atribut page yang akan Kamu buat.', 'Empty');
INSERT INTO public.t_article_template
(id, created_by, created_date, deleted, modify_by, modify_date, description, template_name, structure_id)
VALUES(2, 'system',now()::DATE, 'f','system',now()::DATE, 'Pilih template ini jika template yang direkomendasikan kurang sesuai dengan atribut page yang akan Kamu buat.', 'Basic');

---- t_article_template_structure ---
INSERT INTO public.t_article_template_structure
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, structure_id)
VALUES(nextval('public.article_template_structure_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 2, 2);
INSERT INTO public.t_article_template_structure
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, structure_id)
VALUES(nextval('public.article_template_structure_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 1, 2);

INSERT INTO public.t_article_template_role
(id, created_by, created_date, deleted, role_id, template_id)
VALUES(nextval('public.article_template_role_seq'), 'system',now()::DATE, false, 'EDITOR', 2);
INSERT INTO public.t_article_template_role
(id, created_by, created_date, deleted, role_id, template_id)
VALUES(nextval('public.article_template_role_seq'), 'system',now()::DATE, false, 'SUPERADMIN', 1);
INSERT INTO public.t_article_template_role
(id, created_by, created_date, deleted, role_id, template_id)
VALUES(nextval('public.article_template_role_seq'), 'system',now()::DATE, false, 'SUPERADMIN', 2);

---- article Template Content ----
INSERT INTO public.t_article_template_content
(id, created_by, created_date, deleted, modify_by, modify_date, description, level, name, parent, sort, article_template_id)
VALUES(nextval('article_template_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.', 1, 'Ketentuan Produk', null, 1, 2);
INSERT INTO public.t_article_template_content
(id, created_by, created_date, deleted, modify_by, modify_date, description, level, name, parent, sort, article_template_id)
VALUES(nextval('article_template_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.', 1, 'Prosedur  Produk', null, 2, 2);
INSERT INTO public.t_article_template_content
(id, created_by, created_date, deleted, modify_by, modify_date, description, level, name, parent, sort, article_template_id)
VALUES(nextval('article_template_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.', 1, 'Formulir Produk', null, 3, 2);


--- r_images kebutuhan di r_template dan r_template_thumbnail---
INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(50, 'system',now()::DATE, 'f','system',now()::DATE, null, null, '/templates/empty.svg');
INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(51, 'system',now()::DATE, 'f','system',now()::DATE, null, null, '/templates/empty_thumb.svg');
INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(52, 'system',now()::DATE, 'f','system',now()::DATE, null, null, '/templates/basic.jpeg');
INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(53, 'system',now()::DATE, 'f','system',now()::DATE, null, null, '/templates/basic_thumb.svg');

----- t_article_template_thumbnail---
INSERT INTO public.t_article_template_thumbnail
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, image_id)
VALUES(1, 'system',now()::DATE, 'f','system',now()::DATE, 1, 51);
INSERT INTO public.t_article_template_thumbnail
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, image_id)
VALUES(2, 'system',now()::DATE, 'f','system',now()::DATE, 2, 53);

--- r_template ---
INSERT INTO public.r_template
(id, created_by, created_date, edited, modify_by, modify_date, name, description, image_id)
VALUES(1, 'system', now()::DATE, 'f','system', now()::DATE, 'testing 1', 'desc testing 1', 50);
INSERT INTO public.r_template
(id, created_by, created_date, edited, modify_by, modify_date, name, description, image_id)
VALUES(2, 'system', now()::DATE, 'f','system', now()::DATE, 'testing 2', 'desc testing 2', 52);

--- t_article ---
INSERT INTO public.t_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, article_used_by, title, short_desc, structure_id)
VALUES(1, 'system', now()::DATE, 'f','system', now()::DATE, 1, 'Nasabah', 'Tahapan', null, 100);
INSERT INTO public.t_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, article_used_by, title, short_desc, structure_id)
VALUES(2, 'system', now()::DATE, 'f','system', now()::DATE, 2, 'Nasabah', 'Tahapan', null, 100);


--- t_article_template_image ---
INSERT INTO public.t_article_template_image
(id , created_by, created_date, deleted, modify_by, modify_date, article_template_id, image_id)
VALUES(nextval('article_template_image_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 1, 50);
INSERT INTO public.t_article_template_image
(id, created_by, created_date, deleted, modify_by, modify_date, article_template_id, image_id)
VALUES(nextval('article_template_image_seq'), 'system',now()::DATE, 'f','system',now()::DATE, 2, 52);

INSERT INTO public.r_sk_reff
(id, created_by, created_date, deleted, modify_by, modify_date, title, sk_number)
VALUES(nextval('public.sk_reff_seq'), 'system', now()::DATE, false, NULL, NULL, 'Perihal Ketentuan Tahapan 1', 'no: 025/SKSE/TL/2020');
(id, created_by, created_date, deleted, modify_by, modify_date, title, sk_number)
VALUES(nextval('public.sk_reff_seq'), 'system', now()::DATE, false, NULL, NULL, 'Perihal Ketentuan Tahapan 2', 'no: 026/SKSE/TL/2020');

INSERT INTO public.t_article_sk_reff
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, sk_reff_id)
VALUES(nextval('public.article_sk_reff_seq'), 'system', now()::DATE, false, NULL, NULL, 32, 1);
INSERT INTO public.t_article_sk_reff
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, sk_reff_id)
VALUES(nextval('public.article_sk_reff_seq'), 'system', now()::DATE, false, NULL, NULL, 32, 2);


-- Mapping article ke suggestion article
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 55, 110, 5);
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 86, 220, 10);
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 87, 110, 12);
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 73, 126, 12);
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 65, 482, 12);
INSERT INTO public.t_suggestion_article
(id, created_by, created_date, deleted, modify_by, modify_date, article_id, structure_id, hit_count)
VALUES(nextval('public.suggestion_article_seq'), 'system', now()::DATE, false, NULL, NULL, 64, 482, 13);