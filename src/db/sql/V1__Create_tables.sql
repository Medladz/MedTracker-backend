CREATE TYPE weightOrVolume AS ENUM ('ml','mg');
CREATE TABLE "user"(
    ID SERIAL PRIMARY KEY,
    username varchar(255) UNIQUE NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    verfied boolean NOT NULL,
    birthdate date NOT NULL
);
create table "brand" (
    ID SERIAL PRIMARY KEY,
    creatorID SERIAL NOT NULL,
    name varchar(255) NOT NULL,
    unique (creatorID, name),
    FOREIGN KEY (creatorID) REFERENCES "user" (ID)
);
create table "source"(
    ID SERIAL PRIMARY KEY,
    creatorID SERIAL NOT NULL,
    name varchar(255) NOT NULL,
    unique (creatorID, name),
    FOREIGN KEY (creatorID) REFERENCES "user" (ID)
);
create table "container" (
    ID SERIAL PRIMARY KEY,
    creatorID SERIAL NOT NULL,
    name varchar(255) NOT NULL,
    quantity INT NOT NULL,
    measurementUnit weightOrVolume NOT NULL,
    thumbnailURL varchar(255),
    unique (creatorID, name),
    FOREIGN KEY (creatorID) REFERENCES "user" (ID)
);
create table "drug"(
    ID SERIAL PRIMARY KEY,
    creatorID SERIAL NOT NULL,
    brandID SERIAL,
    sourceID SERIAL,
    name varchar(255) NOT NULL,
    thumbnailURL varchar(255),
    FOREIGN KEY (creatorID) REFERENCES "user" (ID),
    FOREIGN KEY (brandID) REFERENCES "brand" (ID),
    FOREIGN KEY (sourceID) REFERENCES "source" (ID)
);
create table "drugComponent"(
    drugID SERIAL NOT NULL,
    componentID SERIAL NOT NULL,
    purity numeric(5,2),
    quantity INT,
    measurementUnit weightOrVolume NOT NULL,
    PRIMARY KEY (drugID, componentID),
    FOREIGN KEY (drugID) REFERENCES "drug" (ID),
    FOREIGN KEY (componentID) REFERENCES "drug" (ID)
);
create table "agendaEntry"(
    ID SERIAL NOT NULL,
    creatorID SERIAL NOT NULL,
    drugID SERIAL NOT NULL,
    titel varchar(255),
    note text,
    quantity INT NOT NULL,
    measurementUnit weightOrVolume,
    consumedAt timestamp,
    FOREIGN KEY (creatorID) REFERENCES "user" (ID),
    FOREIGN KEY (drugID) REFERENCES "drug" (ID)
);
create table "favoritedDrug"(
    drugID SERIAL NOT NULL,
    userID SERIAL NOT NULL,
    "order" SMALLINT NOT NULL,
    PRIMARY KEY (drugID, userID),
    FOREIGN KEY (drugID) REFERENCES "drug" (ID),
    FOREIGN KEY (userID) REFERENCES "user" (ID)
);
create table "drugContainer"(
    drugID SERIAL NOT NULL,
    containerID SERIAL NOT NULL,
    PRIMARY KEY(drugID, containerID),
    FOREIGN KEY(drugID) REFERENCES "drug" (ID),
    FOREIGN KEY(containerID) REFERENCES "container" (ID)
);
