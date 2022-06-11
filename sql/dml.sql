/*
TRUNCATE books CASCADE;
TRUNCATE users CASCADE;
TRUNCATE orders CASCADE;
TRUNCATE orderItems CASCADE;
*/
INSERT INTO books (isbn, title, author, pages, cover_id, price)
VALUES ('978-5-17-072346-1', 'Master and Margarita', 'Michael Bulgakov', 415, 0, 34.68),
       ('978-5-38-904926-0', 'Crime and punishment', 'Fedor Dostoevsky', 608, 1, 11.54),
       ('978-5-69-994326-5', 'Little prince', 'Antoine de Saint-Exupery', 128, 0, 11.05),
       ('978-5-38-906294-8', 'Dogs heart', 'Michael Bulgakov', 384, 1, 11.54),
       ('978-5-17-066354-5', 'Three comrades', 'Erich Maria Remarque', 384, 1, 15.75),
       ('978-5-17-099056-6', 'The Picture of Dorian Grey', 'Oscar Wilde', 320, 1, 10.40),
       ('978-5-17-092791-3', 'Lord of the Rings', 'John Ronald Reuel Tolkien', 1696, 2, 70.96),
       ('978-5-96-687415-6', 'Godfather', 'Mario Puzo', 544, 0, 22.44),
       ('978-5-38-904731-0', 'Dead Souls', 'Nikolai Gogol', 352, 0, 11.54),
       ('978-5-17-083998-0', 'Solaris', 'Stanislav Lem', 288, 1, 13.08),
       ('978-5-69-993667-0', '451 degrees Fahrenheit', 'Ray Bradbury', 320, 1, 17.17),
       ('978-5-17-080115-2', '1984', 'George Orwell', 320, 1, 11.98),
       ('978-5-38-904816-4', 'Castle', 'Franz Kafka', 384, 1, 11.13),
       ('978-5-38-907435-4', 'Harry Potter and the philosopher stone', 'Joanne Rowling', 432, 0, 34.17),
       ('978-5-38-904730-3', 'Idiot', 'Fedor Dostoevsky', 640, 1, 11.54),
       ('978-5-17-065495-6', 'It', 'Stephen King', 1245, 0, 30.78),
       ('978-5-17-099626-1', 'Pet cemetery', 'Stephen King', 480, 0, 19.87),
       ('978-5-69-955699-1', 'Flowers for Algernon', 'Daniel Keyes', 382, 0, 17.73),
       ('978-5-90-589197-7', 'Uwe second life', 'Fredrik Backman', 384, 0, 27.49),
       ('978-5-00-131235-2', 'Anxious people', 'Fredrik Backman', 416, 0, 27.49);

INSERT INTO users (name, lastname, role_id, email, password, birthday)
VALUES ('Christopher', 'King', 0, 'chris025@gmail.com', '5e8f24540956c0cf364786bc52f64b72029b297d', '1987-11-5'),
       ('Terry', 'Jenkins', 0, 'jenkBest@mail.ru', '5a58e385169778868a3b257c76c24007b0941619', '1997-08-25'),
       ('Lauren', 'Thornton', 1, 'lauren@tut.by', 'cb852d950c935d46b5c884b6d2a31e90ccad280d','1985-07-03'),
       ('Miguel', 'Potter', 1, 'potter@gmail.com', '42b0c8e2906364723119b9d41d06380263fd4e8a', '2000-09-07'),
       ('Lewis', 'Caldwell', 1, 'lewis86@yandex.ru', '3cff26823ad997a59583fa9fb4aafefdaf877540', '1986-12-08'),
       ('William', 'Jones', 1, 'willjo@gmail.com', '5a2089877cfac6ed19ff3744c83a04cada045493', '1978-03-18'),
       ('Steve', 'Torres', 2, 'badboy1@mail.ru', '98467cc0ace084171fc22398fe9cec348f7bdb81', '2008-07-06'),
       ('Sandra', 'Jackson', 2, 'sanjack@gmail.com', 'bb131b99392447969fa17109f7f4537bcde6fe06', '2002-12-01'),
       ('Jason', 'Lee', 2, 'lee123@gmail.com', 'd01961fc938c05a14a4508ee9717acf2665687cb', '1999-10-03'),
       ('Richard', 'Matthews', 2, 'richchi@tut.by', 'dd97131240b22d9b5f0144eb90ef195f4779f3b', '2004-09-15'),
       ('Stanley', 'Peterson', 2, 'stan45@gmail.com', 'cd67f7f169a5ed4d3210bfd3939158b456b2ac9c', '1991-02-27'),
       ('Richard', 'Barnes', 2, 'bar007@tut.by', 'b9571a7cacc11e1b712bd7e4e5de6afc7b0551d5', '2003-06-30'),
       ('Sally', 'Ramirez', 2, 'sally01@mail.ru', '1a12a1dfa9a8ef7bd1b7b07455e93f877b3142e7', '1999-04-18'),
       ('Robin', 'Spencer', 2, 'nice_girl@gmail.com', '5e10a15ef3fd9cd5bfa4711f3532676f3f1bf42b', '2007-09-13'),
       ('Annie', 'Clark', 2, 'ann5@tut.by', 'f6b45878cef90ddf6c72162df47803102f832ba2', '2002-06-26'),
       ('Donna', 'Williams', 2, 'donn@gmail.com', '5a31cdde3811ca507eb84750ec766330e2f077f8', '1996-10-04'),
       ('Diane', 'Jones', 2, 'jondi@mail.ru', 'c935ea8a85b409996a7e3384cd0f4f563fb5117e', '1978-07-10'),
       ('Naomi', 'Jones', 2, 'naomi87@gmail.com', 'a55cedcea40fccb44ad2a1b7147874011086d885', '1987-08-14'),
       ('Margie', 'Keller', 2, 'margo7@tut.by', 'f14d5fa72459fd01fbb119f1819eddb37836d3bf', '1998-10-05'),
       ('Ida', 'Park', 2, 'park71@sdf.sd', '2546979bd1bd1e71706c59a99f49b20d92f50bf1', '1971-09-20');

INSERT INTO orders (user_id, total_cost, status_id)
VALUES ((SELECT id FROM users WHERE email = 'lauren@tut.by'), 176.6 , 2),
       ((SELECT id FROM users WHERE email = 'willjo@gmail.com'), 82.5 , 0),
       ((SELECT id FROM users WHERE email = 'chris025@gmail.com'), 82.47 , 1),
       ((SELECT id FROM users WHERE email = 'lauren@tut.by'), 151.02 , 3),
       ((SELECT id FROM users WHERE email = 'badboy1@mail.ru'), 61.25 , 4),
       ((SELECT id FROM users WHERE email = 'bar007@tut.by'), 43.04 , 2),
       ((SELECT id FROM users WHERE email = 'naomi87@gmail.com'), 29.15 , 3),
       ((SELECT id FROM users WHERE email = 'jondi@mail.ru'), 54.98 , 4);

INSERT INTO orderItems (order_id, book_id, quantity, price)
VALUES(1, (SELECT id FROM books WHERE isbn = '978-5-17-072346-1'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-072346-1')),
      (1, (SELECT id FROM books WHERE isbn = '978-5-17-092791-3'), 2, (SELECT price FROM books WHERE isbn = '978-5-17-092791-3')),
      (2, (SELECT id FROM books WHERE isbn = '978-5-38-906294-8'), 1, (SELECT price FROM books WHERE isbn = '978-5-38-906294-8')),
      (2, (SELECT id FROM books WHERE isbn = '978-5-17-092791-3'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-092791-3')),
      (3, (SELECT id FROM books WHERE isbn = '978-5-00-131235-2'), 3, (SELECT price FROM books WHERE isbn = '978-5-00-131235-2')),
      (4, (SELECT id FROM books WHERE isbn = '978-5-17-065495-6'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-065495-6')),
      (4, (SELECT id FROM books WHERE isbn = '978-5-38-907435-4'), 3, (SELECT price FROM books WHERE isbn = '978-5-38-907435-4')),
      (4, (SELECT id FROM books WHERE isbn = '978-5-69-955699-1'), 1, (SELECT price FROM books WHERE isbn = '978-5-69-955699-1')),
      (5, (SELECT id FROM books WHERE isbn = '978-5-17-099626-1'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-099626-1')),
      (5, (SELECT id FROM books WHERE isbn = '978-5-69-993667-0'), 1, (SELECT price FROM books WHERE isbn = '978-5-69-993667-0')),
      (5, (SELECT id FROM books WHERE isbn = '978-5-38-904816-4'), 1, (SELECT price FROM books WHERE isbn = '978-5-38-904816-4')),
      (5, (SELECT id FROM books WHERE isbn = '978-5-17-083998-0'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-083998-0')),
      (6, (SELECT id FROM books WHERE isbn = '978-5-17-066354-5'), 2, (SELECT price FROM books WHERE isbn = '978-5-17-066354-5')),
      (6, (SELECT id FROM books WHERE isbn = '978-5-38-904926-0'), 1, (SELECT price FROM books WHERE isbn = '978-5-38-904926-0')),
      (7, (SELECT id FROM books WHERE isbn = '978-5-69-993667-0'), 1, (SELECT price FROM books WHERE isbn = '978-5-69-993667-0')),
      (7, (SELECT id FROM books WHERE isbn = '978-5-17-080115-2'), 1, (SELECT price FROM books WHERE isbn = '978-5-17-080115-2')),
      (8, (SELECT id FROM books WHERE isbn = '978-5-00-131235-2'), 2, (SELECT price FROM books WHERE isbn = '978-5-00-131235-2'));