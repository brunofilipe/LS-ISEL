/****************************************************** TEACHER MANAGEMENT **********************************************************/

/************* POST / courses / {acr} / classes / {sem} / {num} / teachers *****************/

insert into Teaches values (3, '42N', 'LS', '1617', 'summer')

/************* GET / courses / {acr} / classes / {sem} / {num} /teachers *****************/

select * from Teacher where teacherId in (
	select teacherId from Teaches where (courseAcr = 'LS' and 
										 academicSemesterYear = '1617' and 
										 academicSemesterType = 'summer' and 
										 classId = '41D')
)
 
/************* GET /teachers/{num}/classes *****************/

select * from Class where classId in (
	select * from Teaches where teacherId = 1207 
)

select C.classId, C.courseAcr, C.academicSemesterType, C.academicSemesterYear
	from Class as C inner join Teaches as T
	on C.classId = T.classId and C.courseAcr = T.courseAcr and C.academicSemesterType = T.academicSemesterType and C.academicSemesterYear = T.academicSemesterYear
where T.teacherId = 1207