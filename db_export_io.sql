CREATE TABLE "courses" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(255),
  "code" varchar(255),
  "description" text,
  "date_created" date
);

CREATE TABLE "course_moderator" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" bigint,
  "course_id" bigint
);

CREATE TABLE "materials" (
  "id" BIGSERIAL PRIMARY KEY,
  "title" varchar(255),
  "is_parent" boolean,
  "parent_id" bigint,
  "type" bigint,
  "created_by" bigint,
  "date_created" date,
  "is_published" boolean,
  "description" text,
  "approved_by" bigint,
  "course_id" bigint,
  "file_id" bigint,
  "question_id" bigint
);

CREATE TABLE "questions" (
  "id" BIGSERIAL PRIMARY KEY,
  "description" text,
  "answer" text
);

CREATE TABLE "files" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(255),
  "download_url" varchar,
  "data" bytea,
  "date_uploaded" datetime,
  "virus_total_link" text,
  "safe" boolean
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(255),
  "password" varchar(255),
  "email" varchar(255),
  "date_created" datetime,
  "is_activated" boolean,
  "role_id" bigint
);

CREATE TABLE "comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "description" text,
  "upvotes" int,
  "downvotes" int,
  "created_by" bigint,
  "date_posted" datetime,
  "material_id" bigint
);

CREATE TABLE "roles" (
  "id" BIGSERIAL PRIMARY KEY,
  "role_name" varchar(255)
);

CREATE TABLE "types" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(255)
);

ALTER TABLE "course_moderator" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "course_moderator" ADD FOREIGN KEY ("course_id") REFERENCES "courses" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("type") REFERENCES "types" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("approved_by") REFERENCES "users" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("course_id") REFERENCES "courses" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("file_id") REFERENCES "files" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("question_id") REFERENCES "questions" ("id");

ALTER TABLE "users" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("material_id") REFERENCES "materials" ("id");
