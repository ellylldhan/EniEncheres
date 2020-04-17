-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)
ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL

)
ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)
ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(20) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)
ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NULL,
    no_categorie                  INTEGER NOT NULL
)
ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) 
		REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE SET NULL
    ON UPDATE NO ACTION

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE NO ACTION

ALTER TABLE ENCHERES
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE CASCADE 
    ON UPDATE NO ACTION

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE NO ACTION

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE NO ACTION



ALTER TABLE UTILISATEURS
	ADD CONSTRAINT AK_Mail  UNIQUE (email)

ALTER TABLE UTILISATEURS
	ADD CONSTRAINT AK_Pseudo UNIQUE (pseudo)

ALTER TABLE UTILISATEURS
	ALTER COLUMN mot_de_passe VARCHAR(300) NOT NULL

	USE EniEncheres;

DELETE FROM RETRAITS;
DELETE FROM ENCHERES;
DELETE FROM ARTICLES_VENDUS;
DELETE FROM UTILISATEURS;
DELETE FROM CATEGORIES;

-- RESET PRIMARY KEY 
DBCC CHECKIDENT ('ARTICLES_VENDUS', RESEED, 1);
DBCC CHECKIDENT ('UTILISATEURS', RESEED, 1);
DBCC CHECKIDENT ('CATEGORIES', RESEED, 1);

SET DATEFORMAT YMD;

-- INSERT CATEGORIES
INSERT INTO CATEGORIES(libelle) VALUES('Ameublement');
INSERT INTO CATEGORIES(libelle) VALUES('Informatique');
INSERT INTO CATEGORIES(libelle) VALUES('Sport&Loisirs');
INSERT INTO CATEGORIES(libelle) VALUES('Vêtement');

-- INSERT UTILISATEURS
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('guillaume.sabin', 'SABIN', 'Guillaume', 'gsabin@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Guillaume', '35170', 'Bruz', '5833602FE827146EDDEAFA5B9A626BAE66708F850F7A1EC6612BAF5C156070496CE23B050F999294362ABCAE12B0CF7E3A66A6C099E7C4D652C286739BDF8CA7', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('loan.pirotais', 'PIROTAIS', 'Loan', 'lpirot@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Loan', '35700', 'Rennes', 'A472FA7B714DB37FF7E6D64955449608E869E27646AD4B8032C540AECEA13F4266582E357A85FA3006F9ABC758E5027E2A76DBF3D3BD000FF56FD95F7698F9F9', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('renaud.lizot', 'LIZOT', 'Renaud', 'rlizot@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Renaud', '35700', 'Rennes', '4CD1DC7C2FEEC0FB1424C4210984CA72E29D55393D4692CE74694B2F1282CDB5093FB4B8F46562E537EECCEC8D1A31FD4B5A50C6BF236A33F3129F5E51ACAA16', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('thomas.huet', 'HUET', 'Thomas', 'thuet@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Thomas', '35500', 'Vitré', 'AFC06892C9871F935F440489F579E5B0784F1C8B999B7A7DAEA448D177E3ADECFDFFA91191E4E482F1B5A99B4917C66F1EE4289C0152E493E195396D0B2BA493', 100, 1);


-- INSERT ARTICLES_VENDUS
-- Sport & Loisir, enchere en cours
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Haltères 10Kg', 'Paire d''haltères de 10Kg', '2020-04-07', '2020-04-17', 10, NULL, 1, 3);

-- Ameublement, enchere terminée
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Bureau', 'Bureau en bois', '2020-02-05', '2020-03-12', 60, 150, 4, 1);

-- Informatique, enchere non commencée 
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Disque dur 2To', 'Disque dur 2 To', '2022-04-12', '2022-05-12', 80, Null, 3, 2);

-- Sport & Loisirs, enchere future
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Vélo', 'VTT 27 pouces', '2020-06-21', '2020-08-21', 10, Null, 1, 3);

-- Informatique, enchere en cours
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Clavier Mécanique Logitech', 'Clavier bon état pas cher', '2020-01-01', '2020-08-12', 10, Null, 2, 2);

-- Vêtement, enchere en cours
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Pantalon', 'Beige', '2020-04-01', '2020-08-01', 15, Null, 4, 4);

-- Vêtement, enchere en cours
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Chemise', 'Rouge', '2020-04-01', '2020-08-01', 20, Null, 2, 4);

-- Vêtement, enchere terminée
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Chaussette gauche', 'Une affaire!', '2020-04-01', '2020-04-01', 1, 150, 3, 4);

-- Vêtement, enchere terminée
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Maillot de bain', 'Vert', '2020-04-01', '2020-04-02', 15, 20, 1, 4);

-- Vêtement, enchere nom commencée
INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES('Chaussures', 'taille 42', '2021-05-01', '2021-08-01', 15, Null, 4, 4);


-- INSERT ENCHERES
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(1, 1, '2020-04-16', 36);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(1, 8, '2020-04-17', 50);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(2, 2, '2020-04-15', 26);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(3, 2, '2020-04-08', 25);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(4, 1, '2020-04-08', 25);


-- INSERT RETRAITS
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(2, 'Rue du retrait', '35700', 'Rennes');
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(8, 'Rue du retrait', '35700', 'Rennes');
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(9, 'Rue du retrait', '35700', 'Rennes');



select * from ARTICLES_VENDUS
select * from ENCHERES
select * from CATEGORIES
select * from UTILISATEURS
select * from RETRAITS


