USE ENCHERES;

-- RESET TABLES
DELETE FROM RETRAITS;
DELETE FROM ENCHERES;
DELETE FROM ARTICLES_VENDUS;
DELETE FROM UTILISATEURS;
DELETE FROM CATEGORIES;

-- RESET PRIMARY KEY 
DBCC CHECKIDENT ('ARTICLES_VENDUS', RESEED, 0);
DBCC CHECKIDENT ('UTILISATEURS', RESEED, 0);
DBCC CHECKIDENT ('CATEGORIES', RESEED, 0);

-- corrige un pb de date chez moi (@Reno)
SET DATEFORMAT YMD;

-- INSERT CATEGORIES
INSERT INTO CATEGORIES(libelle) VALUES('Ameublement');
INSERT INTO CATEGORIES(libelle) VALUES('Informatique');
INSERT INTO CATEGORIES(libelle) VALUES('Sport&Loisirs');
INSERT INTO CATEGORIES(libelle) VALUES('Vêtement');

-- INSERT UTILISATEURS
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('guillaume.sabin', 'SABIN', 'Guillaume', 'gsabin@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Guillaume', '35170', 'Bruz', 'mdpguillaume', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('loan.pirotais', 'PIROTAIS', 'Loan', 'lpirot@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Loan', '35700', 'Rennes', 'mdploan', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('renaud.lizot', 'LIZOT', 'Renaud', 'rlizot@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Renaud', '35700', 'Rennes', 'mdprenaud', 100, 1);
INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES('thomas.huet', 'HUET', 'Thomas', 'thuet@campus-eni.fr', 'XXXXXXXXXX', 'Rue de Thomas', '35500', 'Vitré', 'mdpthomas', 100, 1);


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
VALUES(4, 1, '2020-04-08', 25);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(2, 2, '2020-04-15', 26);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(3, 2, '2020-04-08', 25);
INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) 
VALUES(1, 8, '2020-04-17', 50);


-- INSERT RETRAITS
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(2, 'Rue du retrait', '35700', 'Rennes');
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(8, 'Rue du retrait', '35700', 'Rennes');
INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(9, 'Rue du retrait', '35700', 'Rennes');


select * from ARTICLES_VENDUS
select * from ENCHERES
select * from CATEGORIES
select * from UTILISATEURS
select * from RETRAITS
