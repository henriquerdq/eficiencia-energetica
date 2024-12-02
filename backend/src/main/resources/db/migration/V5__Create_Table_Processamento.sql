CREATE SEQUENCE processamento_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE TABLE processamento (
	id int8 DEFAULT nextval('processamento_seq'::regclass) NOT NULL,
	data_start timestamp(6) NULL,
	tipo_processamento int4 NULL,
	executado bool NULL,
	reprocessar bool NULL,
	qtd_reprocessar int4 NULL,
	id_usuario int8 NULL,
	resultado_amigavel varchar(200) NULL,
	parametro varchar(200) NULL,
	qtd_reprocessado int4 NULL,
	resultado text NULL,
	inventario int8 NULL,
	arquivo_a_processar varchar(200) NULL,
	arquivo_processado varchar(200) NULL,
	data_inicio timestamp NULL,
	data_fim timestamp NULL,
	tamanho varchar(200) NULL,
	CONSTRAINT processamento_pk PRIMARY KEY (id),
	CONSTRAINT fk_processamento_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);