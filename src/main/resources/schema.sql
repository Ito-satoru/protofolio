CREATE TABLE public.users (
	id serial4 NOT NULL,
	username varchar(255) NULL,
	"password" varchar(255) NULL,
	email varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
-- public.to_detail definition

-- Drop table

-- DROP TABLE public.to_detail;

CREATE TABLE public.to_detail (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	title varchar(255) NULL,
	detail text NULL,
	duedate date NULL,
	status varchar(255) NULL,
	CONSTRAINT to_detail_pkey PRIMARY KEY (id)
);


-- public.to_detail foreign keys

ALTER TABLE public.to_detail ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;
