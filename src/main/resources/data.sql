
INSERT INTO Role (name) values('ROLE_ADMIN');
INSERT INTO Role (name) values('ROLE_USER');

INSERT INTO user_tbl (username, password) VALUES ('admin', '$2a$10$kk1kQYzwkFsmDyAI4GBI0enRxTmZRkHvB.W6X7KTmsEbyRzZN3hNm');

INSERT INTO user_tbl_roles (user_tbl_id, roles_id) VALUES (1, 1);
INSERT INTO user_tbl_roles (user_tbl_id, roles_id) VALUES (1, 2);

