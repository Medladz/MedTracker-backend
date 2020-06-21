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
INSERT INTO "user" (username, email, password, verified, birthday) VALUES ('jonty', 'jonty@gmail.com', '$2a$10$qoHZKTxkKl67lwiFeyxjquLIFYqjf1XrsxdT4m3E3H901anQP2lNu', true, '2001-09-11');
INSERT INTO "user" (username, email, password, verified, birthday) VALUES ('henk', 'henk@gmail.com', '$2a$10$51JdwlTOwnNqLatV3vi7wueV2xscQLTV/hlOFOVdCxVdoxTwj2F.C', true, '2001-09-11');
INSERT INTO "user" (username, email, password, verified, birthday) VALUES ('finn', 'finn@gmail.com', '$2a$10$Oi3eemYlbdfdiHj4k13qHO3r1eEKVeDDpG0LGWfKZy7z61ZFYXnRi', true, '2001-09-11');
INSERT INTO "user" (username, email, password, verified, birthday) VALUES ('diego', 'diego@gmail.com', '$2a$10$.T.bqjCttyAC1Fmf.b28du/pMwlf/eMsNOKUli0R2sex/S.FLE7LW', true, '2001-09-11');

INSERT INTO "brand" ("creatorID", name) VALUES (1, 'Hertog Jan');
INSERT INTO "brand" ("creatorID", name) VALUES (2, 'Triangle pharma');
INSERT INTO "brand" ("creatorID", name) VALUES (3, 'Mylan');
INSERT INTO "brand" ("creatorID", name) VALUES (4, 'Etos');

INSERT INTO "source" ("creatorID", name) VALUES (1, 'Jumbo');
INSERT INTO "source" ("creatorID", name) VALUES (1, 'Aldi');
INSERT INTO "source" ("creatorID", name) VALUES (1, 'UMC');
INSERT INTO "source" ("creatorID", name) VALUES (1, 'Etos');

INSERT INTO "container" ("creatorID", name, quantity, "measurementUnit", "thumbnailURL") VALUES (1, 'Flesje', 300, 'ml', 'https://www.horecagoedkoop.nl/media/catalog/product/cache/9cc2c482024b4760e358fdda180042b7/h/e/hertog-jan.png');
INSERT INTO "container" ("creatorID", name, quantity, "measurementUnit", "thumbnailURL") VALUES (1, 'Paracetamol tablet', 250, 'mg', 'https://www.kruizinga.nl/productsV2/1536/16000/16FT-02AC-aa.jpg');
INSERT INTO "container" ("creatorID", name, quantity, "measurementUnit", "thumbnailURL") VALUES (1, 'Ritalin tablet', 10, 'mg', 'https://lh3.googleusercontent.com/proxy/WG1dNYN_YheYOjdx0-1q6ayjs-6uudZ4yQbM7q8cX8Kx9wbJRjW_wwxH3eiD-XcV0z2_Mmx3aZr0PjffvcVogxDo74pXXLAGbOW-K0iyLuxSGpw');
INSERT INTO "container" ("creatorID", name, quantity, "measurementUnit", "thumbnailURL") VALUES (1, 'Ibuprofen tablet', 400, 'mg', 'https://www.kruidvat.nl/medias/sys_master/front-prd/front-prd/hc9/he9/12058532642846/Kruidvat-Ibuprofen-200mg-Omhulde-Tabletten-2061571-2.jpg');

INSERT INTO "drug" ("creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (1, 1, 1, 'Hertog Jan pilsener', 'https://www.horecagoedkoop.nl/media/catalog/product/cache/9cc2c482024b4760e358fdda180042b7/h/e/hertog-jan.png');
INSERT INTO "drug" ("creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (2, 2, 2, 'Paracetamol', 'https://www.aldi.nl/content/dam/aldi/netherlands/aldi_merken/2334_011.png/_jcr_content/renditions/opt.1250w.png.res/1542631679002/opt.1250w.png');
INSERT INTO "drug" ("creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (2, 3, 3, 'Ritalin', 'https://ritalinkopen.online/wp-content/uploads/2019/09/mylan-ritalin-product.png');
INSERT INTO "drug" ("creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (4, 4, 4, 'Ibuprofen', 'https://www.etos.nl/on/demandware.static/-/Sites-etos-master-catalog/default/dwfa3968a9/images/111178231/111178231_MCM_PACK_960627.png');
INSERT INTO "drug" ("creatorID", "brandID", "sourceID", name, "thumbnailURL") VALUES (1, null, null, 'Ethenol', 'https://upload.wikimedia.org/wikipedia/commons/c/cd/Ethenol-2D.png');

INSERT INTO "drugComponent" ("drugID", "componentID", purity, quantity, "measurementUnit") VALUES (5, 1, 5.10, null, null);

INSERT INTO "drugContainer" ("drugID", "containerID") VALUES (1, 1);
INSERT INTO "drugContainer" ("drugID", "containerID") VALUES (2, 2);
INSERT INTO "drugContainer" ("drugID", "containerID") VALUES (3, 3);
INSERT INTO "drugContainer" ("drugID", "containerID") VALUES (4, 4);
