drop table if exists paciente;
create table paciente (
	id SERIAL PRIMARY KEY,
    nome varchar(150) not null,
    cpf varchar(14),
    rg varchar(12),
    data_nascimento date,
    escolaridade varchar(100), 
    profissao varchar(10), 
    genero varchar(50), 
    endereco varchar (100),
    status varchar (50),
    observacao varchar(500)
);