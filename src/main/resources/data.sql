insert into member(id, username, password)
values (1, 'customer', '$2a$10$77qO57qa9Rmbtcy7eDojke0bTW29FXk.I6L2zsAJTJ9JPw7vdfxzq');

insert into member(id, username, password)
values (2, 'partner', '$2a$10$77qO57qa9Rmbtcy7eDojke0bTW29FXk.I6L2zsAJTJ9JPw7vdfxzq');

insert into member_roles(member_id, roles)
values(1, 'CUSTOMER');

insert into member_roles(member_id, roles)
values(2, 'PARTNER');