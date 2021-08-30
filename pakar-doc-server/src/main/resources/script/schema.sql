create table r_article_category (id int8 not null, created_by varchar(255) not null, created_date timestamp not null, deleted boolean not null, modify_by varchar(255), modify_date timestamp, description varchar(255), title varchar(255) not null, primary key (id));

DROP VIEW IF EXISTS public.v_my_pages CASCADE;
CREATE OR replace VIEW public.v_my_pages AS
SELECT ta.*, tas.receiver, tas.fn_receiver, tas.receiver_state, tas.sender, tas.fn_sender, tas.sender_state, (SELECT string_agg(name, ' > ') AS location
FROM
(WITH RECURSIVE rec as
	(
	  SELECT tree.id, tree.name, tree.parent, tree.level from r_structure tree where tree.id=ta.structure_id
	  UNION ALL
	  SELECT tree.id, tree.name, tree.parent, tree.level from rec, r_structure tree where tree.id = rec.parent
	  )
	SELECT id, name, level, 1 AS grouper
	FROM rec
	order by LEVEL
	asc
)
tbl
GROUP BY grouper
) FROM t_article ta
LEFT JOIN public.t_article_state tas ON tas.article_id = ta.id
ORDER BY ta.id
desc
;

--- DROP NOT NULL
ALTER TABLE public.t_article_content_clone ALTER COLUMN optlock DROP NOT NULL;

ALTER TABLE public.t_article_version ALTER COLUMN optlock DROP NOT NULL;
ALTER TABLE public.t_article_version ALTER COLUMN id TYPE varchar USING id::varchar;

ALTER TABLE public.t_article_content_version ALTER COLUMN optlock DROP NOT NULL;
ALTER TABLE public.t_article_content_version ALTER COLUMN id TYPE varchar USING id::varchar;
ALTER TABLE public.t_article_content_version DROP CONSTRAINT fkr5h9iy8eem78l9fu0fdxlmcrq;
ALTER TABLE public.t_article_content_version ALTER COLUMN article_version_id TYPE varchar USING article_version_id::varchar;

ALTER TABLE public.t_article ALTER COLUMN optlock DROP NOT NULL;

ALTER TABLE public.t_article_content ALTER COLUMN optlock DROP NOT NULL;

ALTER TABLE public.t_article ALTER COLUMN optlock DROP NOT NULL;

ALTER TABLE public.t_article_version DROP COLUMN release_version;

DROP TABLE IF EXISTS public.t_article_clone CASCADE;

