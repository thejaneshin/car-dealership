create table c_user (
	username varchar(25) primary key,
	pword varchar(50) not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	u_role varchar(30) not null
);

create table car (
	vin varchar(17) primary key,
	make varchar(50) not null,
	model varchar(50) not null,
	car_year integer not null,
	color varchar(50) not null,
	car_status varchar(30) not null,
	car_owner varchar(25),
	foreign key (car_owner) references c_user (username)
);

create table offer (
	offer_id serial primary key,
	offer_amount decimal not null,
	offer_status varchar(30) not null,
	offered_car varchar(17) not null,
	offerer varchar(25) not null,
	unique (offered_car, offerer),
	foreign key (offered_car) references car (vin) on delete cascade,
	foreign key (offerer) references c_user (username)
);

create table payment (
	payment_id serial primary key,
	payment_amount decimal not null,
	p_month integer not null,
	paid_car varchar(17) not null,
	payer varchar(25) not null,
	unique (p_month, paid_car, payer),
	foreign key (paid_car) references car (vin),
	foreign key (payer) references c_user (username)
);


-- stored procedure
create or replace procedure deleteTestUsersAndCars()
	as $$
	begin
		delete from c_user where username like 'test%';
		delete from car where vin like 'test%';
	end;
$$ language plpgsql;

call deleteTestUsersAndCars();