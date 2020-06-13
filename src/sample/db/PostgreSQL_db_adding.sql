create table if not exists renter_type
(
	"Id_type" serial not null
		constraint renter_type_pk
			primary key,
	property_type varchar(255) not null
);

alter table renter_type owner to postgres;

create table if not exists currency_type
(
	"Id_currency" serial not null
		constraint currency_type_pk
			primary key,
	currency_type varchar(255) not null,
	value double precision
);

alter table currency_type owner to postgres;

create table if not exists operation_type
(
	"Id_operation" serial not null
		constraint operation_type_pk
			primary key,
	operation_type varchar(255) not null
);

alter table operation_type owner to postgres;

create table if not exists "Recvizit"
(
	"Id_recvizit" serial not null
		constraint "Recvizit_pk"
			primary key,
	"INN" bigint not null,
	"KPP" integer not null,
	"BIC" integer not null
);

alter table "Recvizit" owner to postgres;

create table if not exists "Renters"
(
	"Acc_Id" integer not null
		constraint "Renters_pk"
			primary key,
	name varchar(255) not null,
	phone varchar(50) not null,
	email varchar(255) not null,
	"Recvizit" integer not null
		constraint "Renters_fk0"
			references "Recvizit",
	ren_type integer not null
		constraint "Renters_fk1"
			references renter_type
);

alter table "Renters" owner to postgres;

create table if not exists "Account"
(
	"Id" serial not null
		constraint "Account_pk"
			primary key
		constraint "Account_fk0"
			references "Renters",
	"Balance" double precision not null,
	"Saldo" double precision not null,
	last_payment date not null
);

alter table "Account" owner to postgres;

create table if not exists acc_operations
(
	"Id_op_num" serial not null
		constraint acc_operations_pk
			primary key,
	id_rentor integer not null
		constraint acc_operations_fk0
			references "Account",
	"Recvizit" integer not null
		constraint acc_operations_fk1
			references "Account",
	date date not null,
	"Amount" double precision not null,
	"Comment" varchar(255) not null,
	id_renter_type integer not null
		constraint acc_operations_fk2
			references renter_type,
	id_currency integer not null
		constraint acc_operations_fk3
			references currency_type,
	id_op_type integer not null
		constraint acc_operations_fk4
			references operation_type
);

alter table acc_operations owner to postgres;
