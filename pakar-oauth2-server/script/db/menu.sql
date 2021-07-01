--r_menu
INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10001,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'Home PAKAR','top',0,1,'/homepage');
	 
INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10002,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'Kembali ke myBCAportal','top',0,2,'http://klikbca.co.id');

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10003,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'Biaya dan Wewenang','top',0,3,'/pakar-info');
	 
INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10004,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'PAKAR Info','top',0,4,'/pakar-info');
	 
INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10005,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'Contents','top',0,5,'/contents');

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10006,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'My Pages','top',0,6,'/my-pages');

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10013,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',2,NULL,NULL,NULL,NULL,'Biaya dan Wewenang 1','top',10003,1,'/pakar-info'),

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (10023,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',2,NULL,NULL,NULL,NULL,'Biaya dan Wewenang 2','top',10003,2,'/pakar-info'),

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (11001,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'PAKAR PDF','bottom',0,100,'/pakar-pdf'),

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (11002,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',1,NULL,NULL,NULL,NULL,'FAQ','bottom',0,100,'/faq');

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (11003,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',2,NULL,NULL,NULL,NULL,'FAQ Operasional','bottom',0,100,'http://mybcaportal/sites/op/Lists/FAQ_Operasional/AllItems.aspx');

INSERT INTO public.r_menu (id,created_by,created_date,deleted,modify_by,modify_date,"level",description,edit,"location",location_text,"name",nav,parent,sort,uri) VALUES
	 (11004,'system','2021-06-29 00:00:00',false,'system','2021-06-29 00:00:00',2,NULL,NULL,NULL,NULL,'FAQ Kredit','bottom',0,100,'http://mybcaportal/sites/op/Lists/FAQ_Kredit/AllItems.aspx');

--r_images
INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/homepage');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, '', '', 'http://klikbca.co.id');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(3, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/pakar-info');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(4, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/contents');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(5, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/my-pages');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(6, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/pakar-pdf');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(7, 'system', now()::DATE, false, 'system', now()::DATE, '', '', 'http://mybcaportal/sites/op/Lists/FAQ_Operasional/AllItems.aspx');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(8, 'system', now()::DATE, false, 'system', now()::DATE, '', '', 'http://mybcaportal/sites/op/Lists/FAQ_Kredit/AllItems.aspx');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(9, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '/faq');


--r_menu_image
INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10001);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, 2, 10002);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(3, 'system', now()::DATE, false, 'system', now()::DATE, 3, 10003);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(4, 'system', now()::DATE, false, 'system', now()::DATE, 3, 10013);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(5, 'system', now()::DATE, false, 'system', now()::DATE, 3, 10013);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(6, 'system', now()::DATE, false, 'system', now()::DATE, 3, 10023);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(7, 'system', now()::DATE, false, 'system', now()::DATE, 3, 10004);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(8, 'system', now()::DATE, false, 'system', now()::DATE, 4, 10005);

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(9, 'system', now()::DATE, false, 'system', now()::DATE, 5, 10006); 

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(10, 'system', now()::DATE, false, 'system', now()::DATE, 6, 11001); 

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(11, 'system', now()::DATE, false, 'system', now()::DATE, 9, 11002); 

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(12, 'system', now()::DATE, false, 'system', now()::DATE, 7, 11003); 

INSERT INTO public.r_menu_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_id, menu_id)
VALUES(13, 'system', now()::DATE, false, 'system', now()::DATE, 7, 11004); 
COMMIT;

--r_icons
INSERT INTO public.r_icons
(id, created_by, created_date, deleted, modify_by, modify_date, icon_description, icon_name, uri)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, '', 'content.svg', '/menus/content.svg');

INSERT INTO public.r_icons
(id, created_by, created_date, deleted, modify_by, modify_date, icon_description, icon_name, uri)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '');

INSERT INTO public.r_icons
(id, created_by, created_date, deleted, modify_by, modify_date, icon_description, icon_name, uri)
VALUES(3, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '');

INSERT INTO public.r_icons
(id, created_by, created_date, deleted, modify_by, modify_date, icon_description, icon_name, uri)
VALUES(4, 'system', now()::DATE, false, 'system', now()::DATE, '', '', '');

--r_menu_icon
INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10001);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10002);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(3, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10003);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(4, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10013);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(5, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10023);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(6, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10004);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(7, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10005);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(8, 'system', now()::DATE, false, 'system', now()::DATE, 1, 10006);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(9, 'system', now()::DATE, false, 'system', now()::DATE, 1, 11001);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(10, 'system', now()::DATE, false, 'system', now()::DATE, 1, 11002);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(11, 'system', now()::DATE, false, 'system', now()::DATE, 1, 11003);

INSERT INTO public.r_menu_icon
(id, created_by, created_date, deleted, modify_by, modify_date, icon_id, menu_id)
VALUES(12, 'system', now()::DATE, false, 'system', now()::DATE, 1, 11004);
COMMIT;





