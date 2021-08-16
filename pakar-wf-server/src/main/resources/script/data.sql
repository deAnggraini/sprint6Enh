INSERT INTO public.r_wf_process(id, created_by, created_date, deleted, modify_by, modify_date, name)
VALUES('ARTICLE_REVIEW', 'system', now()::DATE, false, NULL, NULL, 'PROCESS ARTICLE PUBLISHING');

INSERT INTO public.r_wf_state_type(id, created_by, created_date, deleted, modify_by, modify_date, name, optlock)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 'Start', 0);
INSERT INTO public.r_wf_state_type(id, created_by, created_date, deleted, modify_by, modify_date, name, optlock)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 'Normal', 0);
INSERT INTO public.r_wf_state_type(id, created_by, created_date, deleted, modify_by, modify_date, name, optlock)
VALUES(3, 'system', now()::DATE, false, NULL, NULL, 'Complete', 0);
INSERT INTO public.r_wf_state_type(id, created_by, created_date, deleted, modify_by, modify_date, name, optlock)
VALUES(4, 'system', now()::DATE, false, NULL, NULL, 'Denied', 0);
INSERT INTO public.r_wf_state_type(id, created_by, created_date, deleted, modify_by, modify_date, name, optlock)
VALUES(5, 'system', now()::DATE, false, NULL, NULL, 'Cancelled', 0);

INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('DRAFT', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN DRAFT CONDITION', 0, 'ARTICLE_REVIEW', 1);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('PENDING', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN PENDING CONDITION', 0, 'ARTICLE_REVIEW', 2);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('PUBLISHED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN PUBLISHED CONDITION', 0, 'ARTICLE_REVIEW', 3);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('DENIED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN REJECTED CONDITION', 0, 'ARTICLE_REVIEW', 4);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('CANCELLED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN CANCELLED CONDITION', 0, 'ARTICLE_REVIEW', 5);

INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('DRAFT', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN DRAFT CONDITION', 0, 'ARTICLE_REVIEW', 1);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('PENDING', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN PENDING CONDITION', 0, 'ARTICLE_REVIEW', 2);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('PUBLISHED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN PUBLISHED CONDITION', 0, 'ARTICLE_REVIEW', 3);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('CANCELLED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN CANCELLED CONDITION', 0, 'ARTICLE_REVIEW', 5);
INSERT INTO public.r_wf_state
(code, created_by, created_date, deleted, modify_by, modify_date, "name", optlock, process_id, state_type)
VALUES('DENIED', 'SYSTEM', '2021-08-06 00:00:00.000', false, NULL, NULL, 'STATE ARTICLE IN REJECTED CONDITION', 0, 'ARTICLE_REVIEW', 4);

INSERT INTO public.r_wf_user_task_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(1, 'system', now()::date, false, null, null, 'APPROVE', 0);
INSERT INTO public.r_wf_user_task_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(2, 'system', now()::date, false, null, null, 'DENY ', 0);
INSERT INTO public.r_wf_user_task_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
VALUES(3, 'system', now()::date, false, null, null, 'CANCEL', 0);
--INSERT INTO public.r_wf_user_task_type(id, created_by, created_date, deleted, modify_by, modify_date, "name", optlock)
--VALUES(4, 'system', now()::date, false, null, null, 'RESOLVE', 0);

INSERT INTO public.r_wf_user_task(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, user_task_type, process)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 'EDITOR EDIT DRAFT ARTICLE', '', 0, 1, 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_user_task(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, user_task_type, process)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 'EDITOR SEND DRAFT ARTICLE', '', 0, 1, 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_user_task(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, user_task_type, process)
VALUES(3, 'system', now()::DATE, false, NULL, NULL, 'PUBLISHER APPROVE ARTICLE TO BE PUBLISHED', '', 0, 1, 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_user_task(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, user_task_type, process)
VALUES(4, 'system', now()::DATE, false, NULL, NULL, 'PUBLISHER DENY ARTICLE', '', 0, 2, 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_user_task(id, created_by, created_date, deleted, modify_by, modify_date, description, "name", optlock, user_task_type, process)
VALUES(5, 'system', now()::DATE, false, NULL, NULL, 'EDITOR CANCEL ARTICLE WAS SEND TO PUBLISHER', '', 0, 3, 'ARTICLE_REVIEW');

INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state, process_id)
VALUES (1, 'system', now()::date, 'f', NULL, NULL, 0, 'WAITING REVIEW', 'DRAFT', 'PENDING', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state, process_id)
VALUES (2, 'system', now()::date, 'f', NULL, NULL, 0, 'APPROVE ARTICLE', 'PENDING', 'PUBLISHED', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state, process_id)
VALUES (3, 'system', now()::date, 'f', NULL, NULL, 0, 'EDIT ARTICLE DRAFT ONLY', 'DRAFT', 'DRAFT', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_transition(id, created_by, created_date, deleted, modify_by, modify_date, optlock, name, current_state, next_state, process_id)
VALUES (4, 'system', now()::date, 'f', NULL, NULL, 0, 'DENY ARTICLE', 'PENDING', 'DRAFT', 'ARTICLE_REVIEW');

INSERT INTO public.r_wf_transition_user_task(id, created_by, created_date, deleted, modify_by, modify_date, optlock, user_task, transition)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 0, 1, 2);
INSERT INTO public.r_wf_transition_user_task(id, created_by, created_date, deleted, modify_by, modify_date, optlock, user_task, transition)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 0, 2, 3);
INSERT INTO public.r_wf_transition_user_task(id, created_by, created_date, deleted, modify_by, modify_date, optlock, user_task, transition)
VALUES(3, 'system', now()::DATE, false, NULL, NULL, 0, 3, 1);

INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(1, 'system', now()::DATE, false, NULL, NULL, 'READER', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(2, 'system', now()::DATE, false, NULL, NULL, 'EDITOR', 'ARTICLE_REVIEW');
INSERT INTO public.r_wf_group(id, created_by, created_date, deleted, modify_by, modify_date, "name", process_id)
VALUES(3, 'system', now()::DATE, false, NULL, NULL, 'PUBLISHER', 'ARTICLE_REVIEW');

