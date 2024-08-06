CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login TEXT NOT NULL,
    password TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS chatrooms (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    owner INTEGER NOT NULL REFERENCES users (id)
);
CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    author INTEGER REFERENCES users (id),
    room INTEGER REFERENCES chatrooms (id),
    text TEXT,
    datetime TIMESTAMP
);