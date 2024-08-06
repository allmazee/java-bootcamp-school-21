INSERT INTO users (login, password)
VALUES 
    ('pebodya', 'qwerty'),
    ('countfla', 'not_qwerty_333'),
    ('maplesar', 'bavlycity123'),
    ('hiiii', 'fepjfkmn431'),
    ('whoami', '123456789');
INSERT INTO chatrooms (name, owner) 
VALUES 
    ('mainroom', 1),
    ('flood', 2),
    ('rules', 3),
    ('hiroom', 4),
    ('memes', 5);
INSERT INTO messages (author, room, text, datetime)
VALUES 
    (1, 1, 'this is main room', '2024-08-07 19:30:54'),
    (2, 2, 'this is flood room', '2024-08-07 20:00:16'),
    (3, 3, 'this is rules room', '2024-08-07 20:08:00'),
    (4, 4, 'this is hi room', '2024-08-07 20:47:12'),
    (1, 5, 'this is memes room', '2024-08-07 22:30:09');