create table api (id integer generated by default as identity, nome varchar(255), url_base varchar(255), primary key (id));
create table request (id integer generated by default as identity, body varchar(255), nome varchar(255), path varchar(255), verbo_http integer, api_id integer not null, primary key (id));
alter table request add constraint FKkrgv6krqtimpuostpjyihsfen foreign key (api_id) references api;
