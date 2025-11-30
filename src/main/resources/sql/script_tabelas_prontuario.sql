CREATE TABLE IF NOT EXISTS enfermeiro (

	COREN varchar (10),
	nome varchar (50) NOT NULL,
	email varchar (30) NOT NULL,
	senha varchar (10) NOT NULL,

	PRIMARY KEY (COREN)
);

CREATE TABLE IF NOT EXISTS paciente (

	CPF_Paciente varchar (11),
	nome varchar (15) NOT NULL,
	sobrenome varchar (40) NOT NULL,
	estadoCivil varchar (10),
	endereco varchar (150),
	sexo char CHECK (sexo in ('F', 'f', 'M', 'm')),
	peso float,
	dataNascimento date,
	dataSaida timestamptz,
	dataEntrada timestamptz,

	PRIMARY KEY (CPF_Paciente)
);

CREATE TABLE IF NOT EXISTS cuida (

	COREN varchar (10),
	CPF_Paciente varchar (11),

	PRIMARY KEY (COREN, CPF_Paciente),
	FOREIGN KEY (COREN) REFERENCES enfermeiro (COREN)
		ON DELETE cascade ON UPDATE cascade,

	FOREIGN KEY (CPF_Paciente) REFERENCES paciente (CPF_Paciente)
		ON DELETE cascade ON UPDATE cascade
);

CREATE TABLE IF NOT EXISTS acompanhante (

	CPF_Acompanhante varchar (11),
	CPF_Paciente varchar (11),
	nomeAcompanhante varchar (40) NOT NULL,
	relacao varchar (20),

	PRIMARY KEY (CPF_Acompanhante),

	FOREIGN KEY (CPF_Paciente) REFERENCES paciente (CPF_Paciente)
		ON DELETE cascade ON UPDATE cascade 
);

CREATE TABLE IF NOT EXISTS fatorRisco (

	Id_FatorRisco serial,
	convulsoes boolean,
	deficitMobilidade boolean,
	quimioterapia boolean,
	turgorPele boolean,
	hipotensao boolean,
	visaoAudicao boolean,
	imunoDepressao boolean,
	limitacaoMobilidade boolean,
	criancaIdosoGestante boolean,
	peleUmida boolean,
	medHiperosmolar boolean,
	cisalhamento boolean,
	delirium boolean,
	deficitNutricional boolean,
	foraDeRisco boolean,
	alteracaoConsciencia boolean,
	fragilidadeCapilar boolean,
    usoAlcoolDrogas boolean,
	CPF_Paciente varchar (11),

	PRIMARY KEY (Id_FatorRisco),

	FOREIGN KEY (CPF_Paciente) REFERENCES paciente (CPF_Paciente)
		ON DELETE set null ON UPDATE cascade

);

CREATE TABLE IF NOT EXISTS historicoSaude (

	Id_HistoricoSaude serial,
	tabagismo boolean,
	diabetes boolean,
	hipertensao boolean,
	doencaAutoimune boolean,
	doencaRespiratoria boolean,
	dislipidemia boolean,
	etilismo boolean,
	viroseInfancia boolean,
	transfusaoSanguinea boolean,
	doencasInfectocontagiosas boolean,
	neoplasia boolean,
	doencaRenal boolean,
	doencaCardiovascular boolean,
	CPF_Paciente varchar (11),

	PRIMARY KEY (Id_HistoricoSaude),
	FOREIGN KEY (CPF_Paciente) REFERENCES paciente (CPF_Paciente)
		ON DELETE set null ON UPDATE cascade
);

CREATE TABLE IF NOT EXISTS prontuario (

	Id_Prontuario serial,
	observacao varchar (300),
	motivoOncologico varchar (20) NOT NULL,
	dataEmissao timestamptz,
	COREN varchar (10),
	Id_FatorRisco integer,
	Id_HistoricoSaude integer,

	PRIMARY KEY (Id_Prontuario),

	FOREIGN KEY (COREN) REFERENCES enfermeiro (COREN)
		ON DELETE set null ON UPDATE cascade,

	FOREIGN KEY (Id_FatorRisco) REFERENCES fatorRisco (Id_FatorRisco)
		ON DELETE set null ON UPDATE cascade,

	FOREIGN KEY (Id_HistoricoSaude) REFERENCES historicoSaude (Id_HistoricoSaude)
		ON DELETE set null ON UPDATE cascade

);
