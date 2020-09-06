create database if not exists banco_chaves;

create table banco_chaves.teste_simples(
  id int primary key not null,
  nome varchar(256) not null,
  sobrenome varchar(256) not null);


create table banco_chaves.teste_relacionamento(
  id int  primary key not null,
  id_nome int not null,
  nome_relacionamento varchar(256),
  constraint relacionamento_simples  foreign key (id_nome) references banco_chaves.teste_simples(id));


insert into banco_chaves.teste_simples values (1,'Dirceu','Semighini');
insert into banco_chaves.teste_simples values (2,'Roberto','Asnesio');
insert into banco_chaves.teste_simples values (3,'Seu','Madruga');
insert into banco_chaves.teste_simples values (4,'João','da Silva');
insert into banco_chaves.teste_simples values (5,'José','Augusto Xavier');


  insert into banco_chaves.teste_relacionamento values (1,1,'Primeiro Relacionamento');
  insert into banco_chaves.teste_relacionamento values (2,1,'Segundo Relacionamento');