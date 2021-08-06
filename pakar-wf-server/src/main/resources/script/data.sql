INSERT INTO public.r_wf_process(id, created_by, created_date, deleted, modify_by, modify_date, name)
VALUES('ARTICLE_REVIEW', 'system', now()::DATE, false, NULL, NULL, 'PROCESS ARTICLE PUBLISHING');

INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('START', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE FOR STARTING ARTICLE CREATION', 'ARTICLE_REVIEW', 0);
INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('DRAFT', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE ARTICLE IN DRAFT CONDITION', 'ARTICLE_REVIEW', 0);
INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('PENDING', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE ARTICLE IN PENDING CONDITION', 'ARTICLE_REVIEW', 0);
INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('PUBLISHED', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE ARTICLE IN PUBLISHED CONDITION', 'ARTICLE_REVIEW', 0);
INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('REJECTED', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE ARTICLE IN REJECTED CONDITION', 'ARTICLE_REVIEW', 0);
INSERT INTO public.r_wf_state(code, created_by, created_date, deleted, modify_by, modify_date, name, process_id, optlock)
VALUES ('CANCELLED', 'SYSTEM', now()::DATE, 'f', NULL, NULL, 'STATE ARTICLE IN CANCELLED CONDITION', 'ARTICLE_REVIEW', 0);

INSERT INTO public.r_wf_action_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(1, 'system', now()::date, false, null, null, 'APPROVE', 0);
INSERT INTO public.r_wf_action_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(2, 'system', now()::date, false, null, null, 'DENIED ', 0);
INSERT INTO public.r_wf_action_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(3, 'system', now()::date, false, null, null, 'CANCEL', 0);
INSERT INTO public.r_wf_action_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(4, 'system', now()::date, false, null, null, 'RESOLVE', 0);
INSERT INTO public.r_wf_action_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(5, 'system', now()::date, false, null, null, 'DRAFT', 0);

INSERT INTO public.r_wf_action(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, action_type, process)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 'USER START NEW DRAFT ARTICLE', '', 0, 5, 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_action(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, action_type, process)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 'USER EDIT DRAFT ARTICLE', '', 0, 5, 'ARTICLE_REVIEW');

INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state)
VALUES (1, 'system', now()::date, 'f', NULL, NULL, 0, 'CREATE NEW ARTICLE DRAFT', 'START', 'DRAFT');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state)
VALUES (2, 'system', now()::date, 'f', NULL, NULL, 0, 'WAITING REVIEW', 'DRAFT', 'PENDING');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state)
VALUES (3, 'system', now()::date, 'f', NULL, NULL, 0, 'APPROVE ARTICLE', 'PENDING', 'PUBLISHED');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state)
VALUES (4, 'system', now()::date, 'f', NULL, NULL, 0, 'EDIT ARTICLE DRAFT ONLY', 'DRAFT', 'DRAFT');

INSERT INTO public.r_wf_transition_action(id, created_by, created_date, deleted, modify_by, modify_date, optlock, "action", transition)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 0, 1, 1);
INSERT INTO public.r_wf_transition_action(id, created_by, created_date, deleted, modify_by, modify_date, optlock, "action", transition)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 0, 2, 4);

INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 'READER', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 'EDITOR', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(3, 'system', now()::DATE, false, NULL, NULL, 'PUBLISHER', 'ARTICLE_REVIEW');

