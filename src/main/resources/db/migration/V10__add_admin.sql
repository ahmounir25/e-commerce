INSERT INTO users (first_name, last_name, email, password)
VALUES("admin","user","admin@admin.com","$2a$12$GJOyhh78oSq84.Ri5IAQuOf3iCN5i1CTlWO.VKo1fr4xFL/o0fpKO");

INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'admin@admin.com'),
    (SELECT id FROM roles WHERE role = 'ADMIN')
);