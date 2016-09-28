/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  yglee
 * Created: 15-Sep-2016
 */
-- Make sure we are using ejd database
USE ejd;
-- delete tables if already exist
DROP TABLE IF EXISTS Course, Student, CourseStudent;
-- create tables
CREATE TABLE Course
(
  id  VARCHAR(9) NOT NULL PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  credit DOUBLE NOT NULL
);

CREATE TABLE Student
(
  id  VARCHAR(9) NOT NULL PRIMARY KEY,
  firstName VARCHAR(25) NOT NULL,
  lastName VARCHAR(25) NOT NULL
);

-- create jenction table
CREATE TABLE CourseStudent
(
  courseId  VARCHAR(9) NOT NULL REFERENCES Course(id), 
  studentId  VARCHAR(9) NOT NULL REFERENCES Student(id),  
  PRIMARY KEY (courseId, studentId)   
  );



-- populate records
INSERT INTO Course VALUES
('PROG10000', 'Java Programming', 6.0),
('DBAS20000', 'Database Design', 3.0),
('MATH30000', 'Linear Algebra', 2.0);


INSERT INTO Student VALUES
('000000001', 'Alex', 'Abby'),
('000000002', 'Bob', 'Brown'),
('000000003', 'Caroline', 'Cabot'),
('000000004', 'Damian', 'David'),
('000000005', 'Ed', 'Edison'),
('000000006', 'Fiona', 'Fox');



INSERT INTO CourseStudent (courseId, studentId)
VALUES
('PROG10000', '000000001'),
('PROG10000', '000000003'),
('PROG10000', '000000005'),
('DBAS20000', '000000002'),
('DBAS20000', '000000004'),
('DBAS20000', '000000006'),
('MATH30000', '000000001'),
('MATH30000', '000000004'),
('MATH30000', '000000005'),
('MATH30000', '000000006');


   -- select id, firstname, lastName from studentinner join CourseStudent 
  --  on student.id = CourseStudent.studentId where CourseStudent.courseId = 'PROG10000';

