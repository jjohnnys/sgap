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
    depen_resp varchar(20)
);