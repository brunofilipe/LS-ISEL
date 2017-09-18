/****************************************************** USER MANAGEMENT **********************************************************/

/************* POST / teachers *****************/

insert into Teacher values ('Pedro Felix', 'pedrofelix@cc.isel.pt', 5)

/************* POST / students *****************/

insert into Student values ('Xavier Marques', 'xaviermarques@alunos.isel.pt', 43893, 'LEIM')

/************* GET / users *****************/

select * from Teacher
select * from Student

/************* GET / teachers *****************/

select * from Teacher

/************* GET / students *****************/

select * from Student

/************* GET / teachers / {num} *****************/

select * from Teacher where teacherId = '4'

/************* GET / students / {num} *****************/

select * from Student where studentId = '41893'

/************* PUT /teachers/{num} *****************/

update Teacher set teacherEmail = 'andredesousa@cc.isel.pt'
			   where teacherId = 4

/************* PUT /students/{num} *****************/

update Student set studentEmail = 'joaopmgameiro@alunos.isel.pt'
			   where studentId = 41893