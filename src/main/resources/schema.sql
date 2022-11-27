drop table if exists request;
drop table if exists api;

create table api (id int not null auto_increment, nome varchar(255), url_base varchar(100), primary key(id));
create table request (id int not null auto_increment, nome varchar(255), verbo_http varchar(10), quantidade_threads int, quantidade_requests int, path varchar(255), headers varchar(1024), body varchar(1024), api_id int, primary key(id));
alter table request add constraint Fkapi_id foreign key(api_id) references api;

