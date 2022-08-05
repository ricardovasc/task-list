INSERT INTO TASK_LIST (id, name) 
VALUES (1, 'List #1'), (2, 'List #2'), (3, 'List #3');

INSERT INTO TASK (id, description, status, task_list_id) 
VALUES (1, 'Task #1-1', 'TO_DO', 1), (2, 'Task #2-1', 'TO_DO', 1);

INSERT INTO TASK (id, description, status, task_list_id) 
VALUES (3, 'Task #1-2', 'DONE', 2), (4, 'Task #2-2', 'TO_DO', 2), (5, 'Task #3-2', 'TO_DO', 3);

INSERT INTO TASK (id, description, status, task_list_id) 
VALUES (6, 'Task #1-3', 'DONE', 3);
