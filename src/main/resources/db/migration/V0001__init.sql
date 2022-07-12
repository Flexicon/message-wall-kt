CREATE TABLE messages (
    id serial PRIMARY KEY,
    text text NOT NULL,
    author varchar(255) NOT NULL,
    created_at timestamp NOT NULL
);
