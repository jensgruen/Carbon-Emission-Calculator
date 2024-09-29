/*
Creating tables for Project
*/

create type emission_goal_status as enum ('achieved', 'not achieved');

create table users (
	user_id int primary key,
	username varchar(50),
	email varchar(50),
	password_hash varchar(50)
);

create table activities (
	activity_id int primary key,
	name varchar(50),
	description text
);

create table emission_factors (
	factor_id int primary key,
	activity_id int,
	factor decimal,
	unit varchar(30),
	constraint fk_activity foreign key(activity_id) references activities(activity_id)
);

create table user_emissions (
	emission_id int primary key,
	user_id int,
	activity_id int,
	quantity decimal,
	emission decimal,
	date date,
	constraint fk_user foreign key(user_id) references users(user_id),
	constraint fk_activity_emissions foreign key(activity_id) references activities(activity_id)
);

create table emission_goals (
	goal_id int primary key,
	user_id int,
	target_emission decimal,
	start_date date,
	end_date date,
	status emission_goal_status,
	constraint fk_users_emission_goals foreign key(user_id) references users(user_id)
);




	