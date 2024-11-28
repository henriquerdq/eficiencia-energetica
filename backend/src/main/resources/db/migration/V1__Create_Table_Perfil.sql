CREATE SEQUENCE perfil_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE TABLE perfil (
	id int8 DEFAULT nextval('perfil_seq'::regclass) NOT NULL,
	abreviacao varchar(3) NOT NULL,
	ativado bool NOT NULL,
	descricao varchar(250) NULL,
	nome varchar(100) NOT NULL,
	CONSTRAINT perfil_id_pkey PRIMARY KEY (id)
);