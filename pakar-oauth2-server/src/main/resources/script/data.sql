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
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','test1', 'password', 't');
INSERT INTO public.r_user
(created_by, created_date, deleted, username, password, enabled)
VALUES('system',now()::DATE, 'f','reader12', 'password', 't');


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
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(8, 'system',now()::DATE, 'f','test1', 'Test', '1', 'Test 1', 'reader@bca.co.id', '1234567', 'BCA', 'READER', './assets/media/users/default.jpg');
INSERT INTO public.r_user_profile
(id, created_by, created_date, deleted, username, firstname, lastname, fullname, email, phone, company_name, occupation, pic)
VALUES(9, 'system',now()::DATE, 'f','reader12', 'Reader12', 'Saja', 'Reader12 Saja', 'reader12@bca.co.id', '1234567', 'BCA', 'READER', './assets/media/users/default.jpg');



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
VALUES(7, 'system',now()::DATE, 'f','reader', 'READER');
INSERT INTO public.r_user_role
(id, created_by, created_date, deleted, username, role_id)
VALUES(8, 'system',now()::DATE, 'f','test1', 'READER');

INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10001, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Home PAKAR', '', 1, 1, 0, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10002, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Kembali ke myBCAportal', '', 1, 2, 0, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10003, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Biaya dan Wewenang', '', 1, 3, 0, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10013, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Biaya dan Wewenang 1', '', 2, 1, 10003, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10023, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Biaya dan Wewenang 2', '', 2, 2, 10003, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10004, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'PAKAR Info', '', 1, 4, 0, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10005, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'Contents', '', 1, 5, 0,'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(10006, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'My Pages', '', 1, 6, 0, 'top');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(11001, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'PAKAR PDF', '', 1, 100, 0, 'bottom');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(11002, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'FAQ', '', 1, 100, 0, 'bottom');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(11003, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'FAQ Operasional', '', 2, 100, 0, 'bottom');
INSERT INTO public.r_menu
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent, nav)
VALUES(11004, 'system', '2021-06-29 00:00:00.000', false, 'system', '2021-06-29 00:00:00.000', 'FAQ Kredit', '', 2, 100, 0, 'bottom');

-- Structure --
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(200, 'system',now()::DATE, 'f',null, null,'Aktivitas Cabang', 'Produk BCA yang digunakan langsung oleh nasabah seperti tabungan e-banking dll.', 1, 1, null);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(210, 'system',now()::DATE, 'f',null, null, 'Pengetahuan Perbankan', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 1, 200);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(220, 'system',now()::DATE, 'f',null, null, 'Layanan Khusus di Cabang', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 2, 200);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(230, 'system',now()::DATE, 'f',null, null, 'Pelaku cabang', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 3, 200);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(240, 'system',now()::DATE, 'f',null, null, 'Aktivitas Harian di Cabang', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 4, 200);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(250, 'system',now()::DATE, 'f',null, null, 'Pengelolaan nasabah', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 5, 200);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(231, 'system',now()::DATE, 'f',null, null, 'CS', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 1, 230);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(232, 'system',now()::DATE, 'f',null, null, 'Teller', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 2, 230);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(233, 'system',now()::DATE, 'f',null, null, 'AO SME', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 4, 230);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(234, 'system',now()::DATE, 'f',null, null, 'AO Komersial', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 5, 230);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(235, 'system',now()::DATE, 'f',null, null, 'RO', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 6, 230);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(239, 'system',now()::DATE, 'f',null, null, 'Pembukuan Rekening', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 4, 1, 231);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(238, 'system',now()::DATE, 'f',null, null, 'Perubahan Data', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 4, 2, 231);

-- PRODUK UNTUK NASABAH--
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort,parent)
VALUES(100, 'system',now()::DATE, 'f',null, null, 'Produk Untuk Nasabah', 'Daftar operasional cabang yang dikategorikan berdasarkan tugasnya.', 1, 2, null);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(110, 'system',now()::DATE, 'f',null, null, 'Produk Dana', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 1, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(120, 'system',now()::DATE, 'f',null, null, 'Produk Investasi & Asuransi', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 2, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(130, 'system',now()::DATE, 'f',null, null, 'Produk Digital', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 3, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(140, 'system',now()::DATE, 'f',null, null, 'Produk Kerja sama', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 4, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(150, 'system',now()::DATE, 'f',null, null, 'Produk Layanan Perbankan', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 5, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(160, 'system',now()::DATE, 'f',null, null, 'Produk Kartu Kredit', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 6, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(170, 'system',now()::DATE, 'f',null, null, 'Produk Kredit Konsumtif', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 7, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(180, 'system',now()::DATE, 'f',null, null, 'Produk Kredit Produktif', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 8, 100);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(125, 'system',now()::DATE, 'f',null, null, 'Obligasi Negara', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 1, 120);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(126, 'system',now()::DATE, 'f',null, null, 'Sertifikat Berharga BI', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 2, 120);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(141, 'system',now()::DATE, 'f',null, null, 'Kerja Sama Copart', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 1, 140);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(142, 'system',now()::DATE, 'f',null, null, 'Kerja Sama Merchant', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 2, 140);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(143, 'system',now()::DATE, 'f',null, null, 'Kerja Sama Institusi', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 4, 140);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(144, 'system',now()::DATE, 'f',null, null, 'Kerja Sama Perusahaan Anak', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 5, 140);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(145, 'system',now()::DATE, 'f',null, null, 'Kerja Sama Perusahaan Khusus', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 6, 140);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(151, 'system',now()::DATE, 'f',null, null, 'Perbankan Internasional', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 1, 150);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(152, 'system',now()::DATE, 'f',null, null, 'Treasuri', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 2, 150);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(153, 'system',now()::DATE, 'f',null, null, 'Perbankan Domestik', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 3, 150);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(181, 'system',now()::DATE, 'f',null, null, 'SME', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 1, 180);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(182, 'system',now()::DATE, 'f',null, null, 'Komersial', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 2, 180);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(183, 'system',now()::DATE, 'f',null, null, 'Korporasi', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 3, 3, 180);


---- APLIKASI MESIN---
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(1, 'system',now()::DATE, 'f',null, null, 'Aplikasi/Mesin', 'Daftar operasional cabang yang dikategorikan berdasarkan tugasnya.', 1, 3, null);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(2, 'system',now()::DATE, 'f',null, null, 'Pendukung Transaksi Umum', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 1, 1);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(3, 'system',now()::DATE, 'f',null, null, 'Pendukung Transaksi Internasional', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 2, 1);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(330, 'system',now()::DATE, 'f',null, null, 'Pendukung Transaksi Kredit', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 3, 1);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(340, 'system',now()::DATE, 'f',null, null, 'Internal BCA', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 4, 1);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(350, 'system',now()::DATE, 'f',null, null, 'Regulator', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 5, 1);
INSERT INTO public.r_structure
(id, created_by, created_date, deleted, modify_by, modify_date, name, description, level, sort, parent)
VALUES(360, 'system',now()::DATE, 'f',null, null, 'Pendukung Operasional', 'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt ut labore.', 2, 6, 1);

-------- MENU ROLE ------------
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(1, 'system',now()::DATE, 'f',null, null, 10001, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(2, 'system',now()::DATE, 'f',null, null, 10001, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(3, 'system',now()::DATE, 'f',null, null, 10001, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(4, 'system',now()::DATE, 'f',null, null, 10001, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(5, 'system',now()::DATE, 'f',null, null, 10002, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(6, 'system',now()::DATE, 'f',null, null, 10002, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(7, 'system',now()::DATE, 'f',null, null, 10002, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(8, 'system',now()::DATE, 'f',null, null, 10002, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(9, 'system',now()::DATE, 'f','system',now()::DATE, 10003, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(10, 'system',now()::DATE, 'f','system',now()::DATE, 10003, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(11, 'system',now()::DATE, 'f','system',now()::DATE, 10003, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(12, 'system',now()::DATE, 'f','system',now()::DATE, 10003, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(13, 'system',now()::DATE, 'f','system',now()::DATE, 10004, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(14, 'system',now()::DATE, 'f','system',now()::DATE, 10004, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(15, 'system',now()::DATE, 'f','system',now()::DATE, 10004, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(16, 'system',now()::DATE, 'f','system',now()::DATE, 10004, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(17, 'system',now()::DATE, 'f','system',now()::DATE, 10005, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(18, 'system',now()::DATE, 'f','system',now()::DATE, 10005, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(19, 'system',now()::DATE, 'f','system',now()::DATE, 10005, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(20, 'system',now()::DATE, 'f','system',now()::DATE, 10006, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(21, 'system',now()::DATE, 'f','system',now()::DATE, 10006, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(22, 'system',now()::DATE, 'f','system',now()::DATE, 10006, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(23, 'system',now()::DATE, 'f','system',now()::DATE, 10013, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(24, 'system',now()::DATE, 'f','system',now()::DATE, 10013, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(25, 'system',now()::DATE, 'f','system',now()::DATE, 10013, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(26, 'system',now()::DATE, 'f','system',now()::DATE, 10013, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(27, 'system',now()::DATE, 'f','system',now()::DATE, 10023, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(28, 'system',now()::DATE, 'f','system',now()::DATE, 10023, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(29, 'system',now()::DATE, 'f','system',now()::DATE, 10023, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(30, 'system',now()::DATE, 'f','system',now()::DATE, 10023, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(31, 'system',now()::DATE, 'f','system',now()::DATE, 11001, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(32, 'system',now()::DATE, 'f','system',now()::DATE, 11001, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(33, 'system',now()::DATE, 'f','system',now()::DATE, 11001, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(34, 'system',now()::DATE, 'f','system',now()::DATE, 11001, 'SUPERADMIN');

INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(35, 'system',now()::DATE, 'f','system',now()::DATE, 11002, 'READER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(36, 'system',now()::DATE, 'f','system',now()::DATE, 11002, 'EDITOR');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(37, 'system',now()::DATE, 'f','system',now()::DATE, 11002, 'PUBLISHER');
INSERT INTO public.r_menu_role
(id, created_by, created_date, deleted, modify_by, modify_date, menu_id, role_id)
VALUES(38, 'system',now()::DATE, 'f','system',now()::DATE, 11002, 'SUPERADMIN');


