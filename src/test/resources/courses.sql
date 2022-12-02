DELETE FROM courses;
DELETE FROM course_infos;
DELETE FROM course_types;

INSERT
INTO
  course_types
  (id, type_name)
VALUES
  ('1', 'dummy course type 1'),
  ('2', 'dummy course type 2');

INSERT
INTO
  course_infos
  (id, duration, level)
VALUES
  ('CI10', 30, 2);

INSERT
INTO
  courses
  (id, title, description, slug, course_type_id, course_info_id)
VALUES
  ('C10', 'Dummy Course 10', 'Dummy Course 10 Description', 'dummy-10', '1', 'CI10');