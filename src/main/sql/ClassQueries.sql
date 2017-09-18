/****************************************************** CLASS MANAGEMENT **********************************************************/

/************* POST / courses / {acr} / classes *****************/

insert into Class values ('42D', 'LS', '1617', 'winter')

/************* GET / courses / {acr} / classes *****************/

select * from Class where courseAcr = 'LS'
	
/************* GET / courses / {acr} / classes / {sem} *****************/

select * from Class where (courseAcr = 'LS' and academicSemesterYear = '1617' and academicSemesterType = 'summer')

/************* GET / courses / {acr} / classes / {sem} / {num}  *****************/

select * from Class where (courseAcr = 'LS' and academicSemesterYear = '1617' and academicSemesterType = 'summer' and classId = '41D')