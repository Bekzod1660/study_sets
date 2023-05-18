-- create default user
INSERT INTO d_user(status, created_date, firstname, lastname, middle_name, password, role, phone_number)
VALUES
      ('CREATED', now(), 'admin', 'admin', 'admin','$2a$10$w6S94NXwDHBDpruVCs8yZuwvFu57yylRDz2BCfx0he0BZv8Nu77iC','ADMIN', '123')