create table Teacher (
	teacherName varchar (150),
	teacherEmail varchar (50) unique,
	teacherId int primary key
)

create table Course (
	courseName  varchar (150) unique,
	courseAcr varchar (10) primary key,
	coordinatorId int,
	foreign key (coordinatorId) references Teacher (teacherId)
)

create table Programme (
	programmeName varchar (150) unique,
	programmeAcr varchar (10) primary key,
	programmeSemesters int
)

create table CourseAvailability (
	courseAvailabilitySemester int primary key
)

create table Student (
	studentName varchar (150),
	studentEmail varchar (50) unique,
	studentId int primary key,
	programmeAcr varchar (10),
	foreign key(programmeAcr) references Programme (programmeAcr)
)

create table Class (
	classId varchar (10),
	courseAcr varchar (10),
	academicSemesterYear varchar (4),
	academicSemesterType varchar (6),
	check (academicSemesterType = 'winter' or academicSemesterType = 'summer'), 
	foreign key (courseAcr) references Course (courseAcr),
	primary key (classId, courseAcr, academicSemesterYear, 
					academicSemesterType)
)

create table Teaches (
	teacherId int,
	classId varchar (10),
	courseAcr varchar (10),
	academicSemesterYear varchar (4),
	academicSemesterType varchar (6),
	check (academicSemesterType = 'winter' or academicSemesterType = 'summer'), 
	foreign key (classId,courseAcr, academicSemesterYear,academicSemesterType ) 
		references Class (classId,courseAcr, academicSemesterYear, academicSemesterType),
	foreign key (teacherId) references Teacher (teacherId),
	primary key(teacherId,classId,courseAcr,academicSemesterYear,
				academicSemesterType)
)

create table Has (
	courseAcr varchar (10),
	programmeAcr varchar (10),
	courseAvailabilitySemester int,
	hasMandatory bit,
	foreign key (courseAcr) references Course (courseAcr),
	foreign key (programmeAcr) references Programme (programmeAcr),
	foreign key (courseAvailabilitySemester) references CourseAvailability (courseAvailabilitySemester),
	primary key (courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory)
)

create table Belongs (
	classId varchar (10), 
	courseAcr varchar (10), 
	academicSemesterYear varchar (4), 
	academicSemesterType varchar (6),
	studentId int,
	foreign key (classId,courseAcr,academicSemesterYear,academicSemesterType) references Class (classId,courseAcr,academicSemesterYear,academicSemesterType),
	foreign key (studentId) references Student (studentId),
	primary key (classId,courseAcr,academicSemesterYear,academicSemesterType,studentId)
)