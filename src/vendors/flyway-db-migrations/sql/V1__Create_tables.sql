CREATE TYPE "weightOrVolume" AS ENUM ('ml','mg');
CREATE TYPE "measurementUnit" AS ENUM ('ml', 'mg', 'unit');

create table "user"(
    "ID" SERIAL PRIMARY KEY,
    "username" varchar(255) NOT NULL,
    "email" varchar(255) UNIQUE NOT NULL,
    "password" varchar(255) NOT NULL,
    "verified" boolean NOT NULL,
    "birthday" date NOT NULL
);

create table "brand" (
    "ID" SERIAL PRIMARY KEY,
    "creatorID" INT NOT NULL,
    "name" varchar(255) NOT NULL,
    unique ("creatorID", "name"),
    FOREIGN KEY ("creatorID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

create table "source"(
    "ID" SERIAL PRIMARY KEY,
    "creatorID" INT NOT NULL,
    "name" varchar(255) NOT NULL,
    unique ("creatorID", "name"),
    FOREIGN KEY ("creatorID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

create table "container" (
    "ID" SERIAL PRIMARY KEY,
    "creatorID" INT NOT NULL,
    "name" varchar(255) NOT NULL,
    "quantity" INT,
    "measurementUnit" "weightOrVolume",
    "thumbnailURL" varchar(255),
    unique ("creatorID", "name"),
    FOREIGN KEY ("creatorID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

create table "drug"(
    "ID" SERIAL PRIMARY KEY,
    "creatorID" INT NOT NULL,
    "brandID" INT,
    "sourceID" INT,
    "name" varchar(255) NOT NULL,
    "thumbnailURL" varchar(255),
    FOREIGN KEY ("creatorID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY ("brandID") REFERENCES "brand" ("ID") ON DELETE SET NULL ON UPDATE NO ACTION,
    FOREIGN KEY ("sourceID") REFERENCES "source" ("ID") ON DELETE SET NULL ON UPDATE NO ACTION
);

create table "drugComponent"(
    "drugID" INT NOT NULL,
    "componentID" INT NOT NULL,
    "purity" numeric(5,2),
    "quantity" INT,
    "measurementUnit" "weightOrVolume",
    PRIMARY KEY ("drugID", "componentID"),
    FOREIGN KEY ("drugID") REFERENCES "drug" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY ("componentID") REFERENCES "drug" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

create table "agendaEntry"(
    "ID" SERIAL NOT NULL,
    "creatorID" INT NOT NULL,
    "drugID" INT,
    "containerID" INT,
    "title" varchar(255) NOT NULL,
    "note" text,
    "quantity" INT,
    "measurementUnit" "measurementUnit",
    "consumedAt" timestamp NOT NULL,
    PRIMARY KEY("ID"),
    FOREIGN KEY ("creatorID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY ("containerID") REFERENCES "container" ("ID") ON DELETE SET NULL ON UPDATE NO ACTION,
    FOREIGN KEY ("drugID") REFERENCES "drug" ("ID") ON DELETE SET NULL ON UPDATE NO ACTION
);

create table "favoritedDrug"(
    "drugID" INT NOT NULL,
    "userID" INT NOT NULL,
    "order" SMALLINT NOT NULL,
    PRIMARY KEY ("drugID", "userID"),
    FOREIGN KEY ("drugID") REFERENCES "drug" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY ("userID") REFERENCES "user" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

create table "drugContainer"(
    "drugID" INT NOT NULL,
    "containerID" INT NOT NULL,
    PRIMARY KEY("drugID", "containerID"),
    FOREIGN KEY("drugID") REFERENCES "drug" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY("containerID") REFERENCES "container" ("ID") ON DELETE CASCADE ON UPDATE NO ACTION
);

-- DUMMY DATA INSERT
INSERT INTO "user" ("ID", username, email, password, verified, birthday) VALUES (1, 'jonty', 'jonty@gmail.com', '$2a$10$bIr9HKWuWAB6Xd.jKyN4EeI0VzIJ5/7LpIhjppB6UC/Bx1b4P1GVi', true, '2020-06-06');
INSERT INTO "user" ("ID", username, email, password, verified, birthday) VALUES (2, 'henk', 'henk@gmail.com', '$2a$10$tkJUZ53HCre0vFv07nyuJe5Xt4BBNu7uzdKCDGxbJcwMd4IU3ct3a', false, '2020-06-06');
INSERT INTO "user" ("ID", username, email, password, verified, birthday) VALUES (3, 'finn', 'finn@gmail.com', '$2a$10$Vhfk8Wy5tdn2BzKzJ747zuqdZgX9YyJ7ECSGgsDBn2M0psI5XanYu', false, '2020-06-06');

INSERT INTO "brand" ("ID", "creatorID", name) VALUES (1, 1, 'a brand');

INSERT INTO "source" ("ID", "creatorID", name) VALUES (1, 1, 'a source');

INSERT INTO "container" ("ID", "creatorID", name, quantity, "measurementUnit", "thumbnailURL") VALUES (1, 1, 'a container', 250, 'ml', 'https://www.kruizinga.nl/productsV2/1536/16000/16FT-02AC-aa.jpg');

INSERT INTO "drug" ("ID", "creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (1, 1, 1, 1, 'drug by jonty', null);
INSERT INTO "drug" ("ID", "creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (2, 2, 1, 1, 'drug by henk', 'https://upload.wikimedia.org/wikipedia/commons/1/1c/Water_molecule_3D.svg');
INSERT INTO "drug" ("ID", "creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (3, 3, null, null, 'drug by finn', 'https://upload.wikimedia.org/wikipedia/commons/1/1c/Water_molecule_3D.svg');

INSERT INTO "drugComponent" ("drugID", "componentID", purity, quantity, "measurementUnit") VALUES (2, 1, 50.00, 100, 'ml');
INSERT INTO "drugComponent" ("drugID", "componentID", purity, quantity, "measurementUnit") VALUES (3, 1, 20.00, 50, 'ml');
INSERT INTO "drugComponent" ("drugID", "componentID", purity, quantity, "measurementUnit") VALUES (3, 2, 90.00, null, 'mg');

INSERT INTO "drugContainer" ("drugID", "containerID") VALUES (3, 1);
