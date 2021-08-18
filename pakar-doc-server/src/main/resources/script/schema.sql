create table r_article_category (id int8 not null, created_by varchar(255) not null, created_date timestamp not null, deleted boolean not null, modify_by varchar(255), modify_date timestamp, description varchar(255), title varchar(255) not null, primary key (id));

--- view my pages
CREATE OR replace VIEW public.v_my_pages AS
SELECT tbl2.* FROM (
	SELECT ta.*, tas.receiver, tas.fn_receiver, tas.receiver_state, tas.sender, tas.fn_sender, tas.sender_state,
		(SELECT tbl_parent.location FROM
				(SELECT string_agg(name, ' > ') AS location
					FROM
					(	WITH RECURSIVE rec AS
						(
		  					SELECT tree.id, tree.name, tree.parent, tree.level FROM r_structure tree WHERE id=ta.id
		  					UNION ALL
		  					SELECT tree.id, tree.name, tree.parent, tree.level FROM rec, r_structure tree WHERE tree.id = rec.parent
		  				)
						SELECT id, name, level, 1 AS grouper
						FROM rec
						ORDER by LEVEL
						ASC
					)
					AS tbl
					GROUP BY grouper
				) tbl_parent
) FROM T_ARTICLE ta
LEFT JOIN public.t_article_state tas ON tas.article_id = ta.id
ORDER by ta.id
) AS tbl2;