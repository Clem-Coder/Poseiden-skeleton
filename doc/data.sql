
CREATE DATABASE demo;

USE demo;

CREATE TABLE bid_list (
  bid_list_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (bid_list_id))
  ENGINE=INNODB;

CREATE TABLE trade (
  trade_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (trade_id))
  ENGINE=INNODB;

CREATE TABLE curve_point (
  id tinyint(4)  NOT NULL AUTO_INCREMENT, 
  curve_id tinyint (4),
  as_of_date TIMESTAMP,
  term DOUBLE,
  value DOUBLE,
  creation_date TIMESTAMP ,
  PRIMARY KEY (id))
  ENGINE=INNODB;

CREATE TABLE rating (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sandP_rating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number tinyint,
  PRIMARY KEY (id))
  ENGINE=INNODB;

CREATE TABLE rule_name (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),
  PRIMARY KEY (id))
  ENGINE=INNODB;

CREATE TABLE users (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),
  enabled TINYINT(4),
  PRIMARY KEY (id))
  ENGINE=INNODB;

insert into users(fullname, username, password, role, enabled) values("Clement Autin", "admin", "$2a$10$QJbsc45K4OVkNnEVl3nEk.jTLSR7JWI3D1HtnmKzcjjVYzDtQVgUu", "ROLE_ADMIN", 1);
insert into users(fullname, username, password, role, enabled) values("Pauline Germain", "user", "$2a$10$T9vwrwHSbE/g6zcn1e6E5e13Nnlh0lukqZaWWJ74PbVm9DxdSD3Ei", "ROLE_USER", 1);

insert into bid_list(account, ask, bid, bid_quantity, ask_quantity, type) values ("jewelry", 5.2, 5.2, 6.0, 7.3, "bid");
insert into bid_list(account, ask, bid, bid_quantity, ask_quantity, type) values ("wapons", 59, 5, 6.0, 7.8, "bid");
insert into bid_list(account, ask, bid, bid_quantity, ask_quantity, type) values ("test", 4.6, 5.9, 6.0, 7.9, "bid");

insert into curve_point(curve_id, term, value) values (3, 10.5, 58 );
insert into curve_point(curve_id, term, value) values (0, 4, 15 );
insert into curve_point(curve_id, term, value) values (4, 8, 19 );

insert into rating(moodys_rating, sandP_rating, fitch_rating, order_number) values ("ascending", "ascending", "ascending", 4 );
insert into rating(moodys_rating, sandP_rating, fitch_rating, order_number) values ("descending", "ascending", "descending", 3 );
insert into rating(moodys_rating, sandP_rating, fitch_rating, order_number) values ("ascending", "descending", "ascending", 9 );

insert into trade(account, type, buy_quantity, sell_quantity) values ("Apple", "purchase",  135, 0.5 );
insert into trade(account, type, buy_quantity, sell_quantity) values ("Google", "sale",  125.4, 0.8 );
insert into trade(account, type, buy_quantity, sell_quantity) values ("Amazon", "sale",  236.8, 2.3 );


insert into rule_name(name, description, json, template, sql_str, sql_part) values ("purchase", "transaction number 05184", "{JSON}", "NO NAME", "SQL PART", "SQL STRING" );
insert into rule_name(name, description, json, template, sql_str, sql_part) values ("sale", "transaction number 54167", "{JSON}", "NO NAME", "SQL PART", "SQL STRING" );
insert into rule_name(name, description, json, template, sql_str, sql_part) values ("sale", "transaction number 98685", "{JSON}", "NO NAME", "SQL PART", "SQL STRING" );






