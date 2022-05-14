/*
TRUNCATE books CASCADE;
TRUNCATE covers CASCADE;
TRUNCATE users CASCADE;
TRUNCATE roles CASCADE;
*/

INSERT INTO roles (name)
VALUES ('ADMIN'),
		('MANAGER'),
		('CUSTOMER');

INSERT INTO covers (name)
VALUES('SOFT'),
	('HARD'),
	('GIFT');

INSERT INTO books (isbn, title, author, pages, cover_id, price)
VALUES ('978-5-17-072346-1', 'Master and Margarita', 'Michael Bulgakov', 415, (SELECT id FROM covers WHERE name = 'HARD'), 34.68),
	('978-5-38-904926-0', 'Crime and punishment', 'Fedor Dostoevsky', 608, (SELECT id FROM covers WHERE name = 'SOFT'), 11.54),
	('978-5-69-994326-5', 'Little prince', 'Antoine de Saint-Exupery', 128, (SELECT id FROM covers WHERE name = 'HARD'), 11.05),
	('978-5-38-906294-8', 'Dogs heart', 'Michael Bulgakov', 384, (SELECT id FROM covers WHERE name = 'SOFT'), 11.54),
	('978-5-17-066354-5', 'Three comrades', 'Erich Maria Remarque', 384, (SELECT id FROM covers WHERE name = 'SOFT'), 15.75),
	('978-5-17-099056-6', 'The Picture of Dorian Grey', 'Oscar Wilde', 320, (SELECT id FROM covers WHERE name = 'SOFT'), 10.40),
	('978-5-17-092791-3', 'Lord of the Rings', 'John Ronald Reuel Tolkien', 1696, (SELECT id FROM covers WHERE name = 'GIFT'), 70.96),
	('978-5-96-687415-6', 'Godfather', 'Mario Puzo', 544, (SELECT id FROM covers WHERE name = 'HARD'), 22.44),
	('978-5-38-904731-0', 'Dead Souls', 'Nikolai Gogol', 352, (SELECT id FROM covers WHERE name = 'HARD'), 11.54),
	('978-5-17-083998-0', 'Solaris', 'Stanislav Lem', 288, (SELECT id FROM covers WHERE name = 'SOFT'), 13.08),
	('978-5-69-993667-0', '451 degrees Fahrenheit', 'Ray Bradbury', 320, (SELECT id FROM covers WHERE name = 'SOFT'), 17.17),
	('978-5-17-080115-2', '1984', 'George Orwell', 320, (SELECT id FROM covers WHERE name = 'SOFT'), 11.98),
	('978-5-38-904816-4', 'Castle', 'Franz Kafka', 384, (SELECT id FROM covers WHERE name = 'SOFT'), 11.13),
	('978-5-38-907435-4', 'Harry Potter and the philosopher stone', 'Joanne Rowling', 432, (SELECT id FROM covers WHERE name = 'HARD'), 34.17),
	('978-5-38-904730-3', 'Idiot', 'Fedor Dostoevsky', 640, (SELECT id FROM covers WHERE name = 'SOFT'), 11.54),
	('978-5-17-065495-6', 'It', 'Stephen King', 1245, (SELECT id FROM covers WHERE name = 'HARD'), 30.78),
	('978-5-17-099626-1', 'Pet cemetery', 'Stephen King', 480, (SELECT id FROM covers WHERE name = 'HARD'), 19.87),
	('978-5-69-955699-1', 'Flowers for Algernon', 'Daniel Keyes', 382, (SELECT id FROM covers WHERE name = 'HARD'), 17.73),
	('978-5-90-589197-7', 'Uwe second life', 'Fredrik Backman', 384, (SELECT id FROM covers WHERE name = 'HARD'), 27.49),
	('978-5-00-131235-2', 'Anxious people', 'Fredrik Backman', 416, (SELECT id FROM covers WHERE name = 'HARD'), 27.49);

INSERT INTO users (name, lastname, role_id, email, password, birthday)
VALUES ('Christopher', 'King', (SELECT id FROM roles WHERE name = 'ADMIN'), 'chris025@gmail.com', 'P4Eo2j1j', '1987-11-5'),
	('Terry', 'Jenkins', (SELECT id FROM roles WHERE name = 'MANAGER'), 'jenkBest@mail.ru', '9TGd2h5w', '1997-08-25'),
	('Lauren', 'Thornton', (SELECT id FROM roles WHERE name = 'MANAGER'), 'lauren@tut.by', '24JZB2kn','1985-07-03'),
	('Miguel', 'Potter', (SELECT id FROM roles WHERE name = 'MANAGER'), 'potter@gmail.com', '9JBRO0Jn', '2000-09-07'),
	('Lewis', 'Caldwell', (SELECT id FROM roles WHERE name = 'MANAGER'), 'lewis86@yandex.ru', 'Jqc1brQm', '1986-12-08'),
	('William', 'Jones', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'willjo@gmail.com', 'FVoIVVsf', '1978-03-18'),
	('Steve', 'Torres', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'badboy1@mail.ru', '7PEcabFV', '2008-07-06'),
	('Sandra', 'Jackson', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'sanjack@gmail.com', 'rDmx0cO4', '2002-12-01'),
	('Jason', 'Lee', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'lee123@gmail.com', 'YRfVoaxm', '1999-10-03'),
	('Richard', 'Matthews', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'richchi@tut.by', 'wiEdhdil', '2004-09-15'),
	('Stanley', 'Peterson', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'stan45@gmail.com', 'hPN32jRR', '1991-02-27'),
	('Richard', 'Barnes', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'bar007@tut.by', '20N4bIde', '2003-06-30'),
	('Sally', 'Ramirez', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'sally01@mail.ru', 'EEpqz8Cb', '1999-04-18'),
	('Robin', 'Spencer', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'nice_girl@gmail.com', '4jdfUqUh', '2007-09-13'),
	('Annie', 'Clark', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'ann5@tut.by', 'fXOq9LFR', '2002-06-26'),
	('Donna', 'Williams', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'donn@gmail.com', 'zXLYsVQa', '1996-10-04'),
	('Diane', 'Jones', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'jondi@mail.ru', 'DX5fK4AC', '1978-07-10'),
	('Naomi', 'Jones', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'naomi87@gmail.com', 'N8iiyREa', '1987-08-14'),
	('Margie', 'Keller', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'margo7@tut.by', 'GjTKv51o', '1998-10-05'),
	('Ida', 'Park', (SELECT id FROM roles WHERE name = 'CUSTOMER'), 'park71', 'tyliFqgO', '1971-09-20');