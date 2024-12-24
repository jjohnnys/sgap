drop table if exists paciente_responsavel;
drop table if exists responsavel;
drop table if exists paciente;

create table paciente (
	id SERIAL PRIMARY KEY,
    nome varchar(150) not null,
    cpf_cnpj varchar(14),
    rg varchar(12),
    fisica_juridica varchar(1),
    data_nascimento date,
    escolaridade varchar(100), 
    genero varchar(50),
    profissao varchar(10),      
    endereco varchar (100),
    status varchar (50),
    observacao varchar(500),
    dependente boolean
);

create table responsavel (
	id SERIAL PRIMARY KEY,
    nome varchar(150) not null,
    cpf_cnpj varchar(14),
    rg varchar(12),
    fisica_juridica varchar(1),
    data_nascimento date,
    profissao varchar(10),
    endereco varchar (100),
    observacao varchar(500)
);


create table paciente_responsavel (
    id_paciente SERIAL REFERENCES paciente(id),
    id_responsavel SERIAL REFERENCES responsavel(id),
    CONSTRAINT paciente_responsavel_pk PRIMARY KEY (id_paciente, id_responsavel)
);