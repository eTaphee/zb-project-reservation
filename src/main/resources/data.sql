-- partner
set @pwd = '$2a$10$77qO57qa9Rmbtcy7eDojke0bTW29FXk.I6L2zsAJTJ9JPw7vdfxzq';

insert into member(username, password, created_at, registered_at)
values ('partner', @pwd, now(), now());

insert into member(username, password, created_at, registered_at)
values ('partner2', @pwd, now(), now());

insert into member(username, password, created_at, registered_at)
values ('partner3', @pwd, now(), now());

insert into member(username, password, created_at, registered_at)
values ('customer', @pwd, now(), now());

insert into member(username, password, created_at, registered_at)
values ('customer2', @pwd, now(), now());

insert into member(username, password, created_at, registered_at)
values ('customer3', @pwd, now(), now());

set @partner = (select id from member where username = 'partner');
set @partner2 = (select id from member where username = 'partner2');
set @partner3 = (select id from member where username = 'partner3');
set @customer = (select id from member where username = 'customer');
set @customer2 = (select id from member where username = 'customer2');
set @customer3 = (select id from member where username = 'customer3');

insert into member_roles(member_id, roles) values (@partner, 'PARTNER');
insert into member_roles(member_id, roles) values (@partner2, 'PARTNER');
insert into member_roles(member_id, roles) values (@partner3, 'PARTNER');
insert into member_roles(member_id, roles) values (@customer, 'CUSTOMER');
insert into member_roles(member_id, roles) values (@customer2, 'CUSTOMER');
insert into member_roles(member_id, roles) values (@customer3, 'CUSTOMER');

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('해심', '일식당', '0507-1307-7483', '서울 강남구 역삼로19길 7', 37.49547505004701, 127.03643928462348, 0.0, 0, now(), now(), @partner);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('그옛날손짜장', '중식당', '02-562-6747', '서울 강남구 역삼로19길 5', 37.495668330920296, 127.03678027414216, 0.0, 0, now(), now(), @partner);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('세겹', '돼지고기구이', '0507-1364-3292', '서울 강남구 논현로75길 7 1층', 37.49655983304902, 127.03833562223473, 0.0, 0, now(), now(), @partner);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('대낚식당', '한식', '0507-1316-5561', '서울 강남구 역삼로 137 1층', 37.494497711282456, 127.03449545743088, 0.0, 0, now(), now(), @partner);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('구스아일랜드 브루하우스', '스테이크,립', '02-6205-1785', '서울 강남구 역삼로 118', 37.49340813936048, 127.03220509286403, 0.0, 0, now(), now(), @partner);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('고메램 강남점', '양갈비', '0507-1330-7805', '서울 서초구 강남대로 359 대우도씨에빛 2차 지하 1층', 37.49477636885231, 127.02850225337812, 0.0, 0, now(), now(), @partner2);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('도툼횟집', '생선회', '02-554-0443', '서울 강남구 강남대로84길 15 지하1층 105~108호', 37.496947423850706, 127.03000145492368, 0.0, 0, now(), now(), @partner2);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('안사부', '중식당', '0507-1365-8662', '서울 강남구 논현로94길 3 죽암빌딩 1층', 37.502374098634064, 127.03668743410097, 0.0, 0, now(), now(), @partner2);

insert into store (name, description, tel, address, latitude, longitude, star_rating, review_count, registered_at, created_at, partner_id)
values('트리가 역삼점', '스페인음식', '0507-1337-0608', '서울 강남구 테헤란로25길 46', 37.503460381735266, 127.03479928189068, 0.0, 0, now(), now(), @partner2);