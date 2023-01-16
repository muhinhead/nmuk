drop database materials;

create database materials;

use materials; 

create table mats_usr
(
    userID int not null auto_increment,
    login varchar(32) not null,
    password varchar(32) not null,
    isAdmin smallint not null default 0,
    position varchar(64),
    firstName varchar(32),
    secondName varchar(32),
    lastName varchar(32),
    primary key (userID)
);

alter table mats_usr add constraint uniq_name1 unique(login);

insert into mats_usr (login,password,isAdmin) values('admin','admin',1);

create table mats_mattype 
(
    mattypeID int not null auto_increment,
    name varchar(255) not null,
    primary key (mattypeID)
);

alter table mats_mattype add constraint mats_uniq_name1 unique(name);

create table mats_item 
(
    itemID int not null auto_increment,
    invCode varchar(12) not null,
    name varchar(255) not null,
    colour varchar(32),
    thickness int not null,
    quantity int not null default 0,
    sheetSize varchar(32) not null,
    sheetCost decimal(10,2) not null,
    sheetMarkup int not null default 0,
    mattypeID int not null,
    primary key (itemID),
    foreign key (mattypeID) references mats_mattype (mattypeID)
);

create table mats_income
(
    incomeID int not null auto_increment,
    dtTime timestamp not null,
    incoming int not null default 0,
    restAfterIncome int not null default 0,
    itemID int not null,
    userID int not null,
    primary key (incomeID),
    foreign key (itemID) references mats_item (itemID) on delete cascade,
    foreign key (userID) references mats_usr (userID)
);

create table mats_addfile
(
    addfileID int not null auto_increment,
    name varchar(45),
    filetype varchar(45),
    filebody mediumblob,
    description varchar(8192),
    userID int,
    itemID int,
    incomeID int,
    primary key (addfileID),
    foreign key (userID) references mats_usr (userID) on delete cascade,
    foreign key (itemID) references mats_item (itemID) on delete cascade,
    foreign key (incomeID) references mats_income (incomeID) on delete cascade
);

