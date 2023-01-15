CONNECT 'jdbc:derby:TheLaserShop;create=true';
CREATE SCHEMA AUTHORIZATION mats;

create table mats.usr
(
    userID int not null GENERATED ALWAYS AS IDENTITY,
    login varchar(32) not null,
    password varchar(32) not null,
    isAdmin smallint not null default 0,
    position varchar(64),
    firstName varchar(32),
    secondName varchar(32),
    lastName varchar(32),
    primary key (userID)
);

alter table mats.usr add constraint uniq_name1 unique(login);

insert into mats.usr (login,password,isAdmin) values('admin','admin',1);

create table mats.mattype 
(
    mattypeID int not null generated always as identity,
    name varchar(255) not null,
    primary key (mattypeID)
);

alter table mats.mattype add constraint mats.uniq_name1 unique(name);

create table mats.item 
(
    itemID int not null generated always as identity,
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
    foreign key (mattypeID) references mats.mattype (mattypeID)
);

create table mats.income
(
    incomeID int not null generated always as identity,
    dtTime timestamp not null,
    incoming int not null default 0,
    restAfterIncome int not null default 0,
    itemID int not null,
    userID int not null,
    primary key (incomeID),
    foreign key (itemID) references mats.item (itemID),
    foreign key (userID) references mats.usr (userID)
);

create table mats.addfile
(
    addfileID int not null generated always as identity,
    name varchar(45),
    filetype varchar(45),
    filebody blob,
    description varchar(8192),
    userID int,
    itemID int,
    incomeID int,
    primary key (addfileID),
    foreign key (userID) references mats.usr (userID),
    foreign key (itemID) references mats.item (itemID),
    foreign key (incomeID) references mats.income (incomeID)
);

