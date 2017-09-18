create table Students(
    studentID int primary key ,
    studentName varchar (150),
    studentMatriculations int
)

insert into Students values (41893, 'Joao Gameiro' , 3  )
insert into Students values (41484, 'Bruno Filipe' , 3 )
insert into Students values (41529, 'Nuno Pinto' , 3 )

select * from Students
update Students set studentMatriculations = 3 where studentID = 41529
delete from Students where studentID = 4444
drop table Students