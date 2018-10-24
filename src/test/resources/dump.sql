PRAGMA foreign_keys=ON;
BEGIN TRANSACTION;

CREATE TABLE genres (
  id    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  genre VARCHAR(255),
  link  VARCHAR(255)
);
CREATE TABLE books (
  id           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  title        VARCHAR(255),
  genre        INT(11),
  author       VARCHAR(255),
  rate         INT(10),
  count_pages  INT(10),
  image        VARCHAR(255),
  path_to_file VARCHAR(255),

  FOREIGN KEY(genre) REFERENCES genres(id)
);
CREATE TABLE users (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  user_name  VARCHAR(255),
  user_email VARCHAR(255)
);
CREATE TABLE libraries (
  id           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  library_name VARCHAR(255)
);
CREATE TABLE voice_overs (
  id                      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  path_to_voice_over_file VARCHAR(255),
  book                    INT(11),

  FOREIGN KEY(book) REFERENCES books(id)
);


CREATE TABLE users_libraries (
  id      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  user    INT(11),
  library INT(11),

  FOREIGN KEY(library) REFERENCES libraries(id),
  FOREIGN KEY(user) REFERENCES users(id)
);
CREATE TABLE books_libraries (
  id       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  library  INT(11),
  book     INT(11),
  progress INT(10),

  FOREIGN KEY(book) REFERENCES books(id),
  FOREIGN KEY(library) REFERENCES libraries(id)
);

COMMIT;
