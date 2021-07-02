-- public.r_theme definition

-- Drop table

-- DROP TABLE public.r_theme;

CREATE TABLE public.r_theme (
	id int8 NOT NULL,
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	deleted bool NOT NULL,
	modify_by varchar(255) NULL,
	modify_date timestamp NULL,
	background varchar(255) NULL,
	color varchar(255) NULL,
	hover varchar(255) NULL,
	CONSTRAINT r_theme_pkey PRIMARY KEY (id)
);

-- public.r_theme_component_homepage definition

-- Drop table

-- DROP TABLE public.r_theme_component_homepage;

CREATE TABLE public.r_theme_component_homepage (
	id int8 NOT NULL,
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	deleted bool NOT NULL,
	modify_by varchar(255) NULL,
	modify_date timestamp NULL,
	component_name varchar(255) NULL,
	order_flag int8 NULL,
	CONSTRAINT r_theme_component_homepage_pkey PRIMARY KEY (id)
);

--r_theme
INSERT INTO public.r_theme (id,created_by,created_date,deleted,modify_by,modify_date,background,color,hover) VALUES
	 (1,'system',now()::DATE,false,'system',now()::DATE,'#1995D1','white','red');
COMMIT;

--r_theme_component_homepage
INSERT INTO public.r_theme_component_homepage (id,created_by,created_date,deleted,modify_by,modify_date,component_name,order_flag) VALUES
(1,'system',now()::DATE,false,'system',now()::DATE,'search',1);

INSERT INTO public.r_theme_component_homepage (id,created_by,created_date,deleted,modify_by,modify_date,component_name,order_flag) VALUES
(2,'system',now()::DATE,false,'system',now()::DATE,'category',2);

INSERT INTO public.r_theme_component_homepage (id,created_by,created_date,deleted,modify_by,modify_date,component_name,order_flag) VALUES
(3,'system',now()::DATE,false,'system',now()::DATE,'recommendation',3);

INSERT INTO public.r_theme_component_homepage (id,created_by,created_date,deleted,modify_by,modify_date,component_name,order_flag) VALUES
(4,'system',now()::DATE,false,'system',now()::DATE,'news',4);

INSERT INTO public.r_theme_component_homepage (id,created_by,created_date,deleted,modify_by,modify_date,component_name,order_flag) VALUES
(5,'system',now()::DATE,false,'system',now()::DATE,'popular',5);

COMMIT;

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(10, 'system', now()::DATE, false, 'system', now()::DATE, '', 'default_top.svg', '/themes/default_top.svg');

INSERT INTO public.r_images
(id, created_by, created_date, deleted, modify_by, modify_date, image_description, image_name, uri)
VALUES(11, 'system', now()::DATE, false, 'system', now()::DATE, '', 'default_login.svg', '/themes/default_login.svg');
INSERT INTO public.r_theme_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_type, image_id)
VALUES(1, 'system', now()::DATE, false, 'system', now()::DATE, 'header', 10);

INSERT INTO public.r_theme_image
(id, created_by, created_date, deleted, modify_by, modify_date, image_type, image_id)
VALUES(2, 'system', now()::DATE, false, 'system', now()::DATE, 'login', 11);

COMMIT;
	 

