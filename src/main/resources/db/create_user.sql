insert into food_ordering_app_user (user_id, user_name, email, password, active)
values (1, 'tomek_owner', 'tomek1@gmail.com', '$2a$12$f.LusQhWzlG8tkHf5ItqEONN6MtR/pjPzzOwAo3Csp2PLf3.Z6.Se', true);

UPDATE owner SET user_id = 1 WHERE email = 'tomek1@gmail.com';

insert into food_ordering_app_role (role_id, role) values (1, 'OWNER'), (2, 'CUSTOMER');

insert into food_ordering_app_user_role (user_id, role_id) values (1, 1);