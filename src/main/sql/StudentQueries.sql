/****************************************************** STUDENT MANAGEMENT **********************************************************/

/************* GET /courses/{acr}/classes/{sem}/{num}/students *****************/

select * from Student where studentId in (
		select studentId from Belongs where (courseAcr = 'LS' and academicSemesterType = 'summer' and academicSemesterYear = '1617' and classId = '41D')
		)

/************* GET /courses/{acr}/classes/{sem}/{num}/students/sorted *****************/

select * from Student where studentId in (
		select studentId from Belongs where (courseAcr = 'LS' and academicSemesterType = 'summer' and academicSemesterYear = '1617' and classId = '41D')
		)
order by studentId

/************* POST /courses/{acr}/classes/{sem}/{num}/students *****************/

insert into Belongs values ('42N', 'PI', '1516', 'winter', '41893')

/************* DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu} *****************/

delete from Belongs where ( classId = '42N' 
							and courseAcr = 'PI' 
							and academicSemesterYear = '1516' 
							and academicSemesterType = 'winter' 
							and studentId = '41893'
							)