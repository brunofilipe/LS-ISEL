insert into Teacher values ('Jose Manuel', 'zemanuel@cc.isel.pt', 1);
insert into Teacher values ('Carlos Fonseca', 'carlosfonseca@cc.isel.pt', 2);
insert into Teacher values ('Vitor Oliveira', 'vitoroliveira@cc.isel.pt', 3);
insert into Teacher values ('Andre Sousa', 'andresousa@cc.isel.pt', 4);

select * from Teacher

/************************************************************/

insert into Programme values('Licenciatura Engenharia Informatica e Computadores', 'LEIC', 6);
insert into Programme values('Licenciatura Engenharia Informatica e Multimedia', 'LEIM', 6);
insert into Programme values('Licenciatura Engenharia Quimica', 'LEQ', 6);

select *from Programme

/*************************************************************/

insert into Student values('Joao Gameiro','joaogameiro@alunos.isel.pt', 41893, 'LEIC');
insert into Student values('Bruno Filipe','brunofilipe@alunos.isel.pt', 41484, 'LEIC');
insert into Student values('Nuno Pinto','nunopinto@alunos.isel.pt', 41529, 'LEIC');
insert into Student values('Joao Mario','joaomario@alunos.isel.pt', 41000, 'LEIC');
insert into Student values('Renato Sanches','rsanches@alunos.isel.pt', 41001, 'LEIC');
insert into Student values('Cristiano Ronaldo','cr7@alunos.isel.pt', 77777, 'LEIC');
insert into Student values('Salvador Sobral','ssobral@alunos.isel.pt', 41002, 'LEIC');
insert into Student values('Eusebio Ferreira','euferreira@alunos.isel.pt', 41003, 'LEIC');
insert into Student values('Ana Teixeira','anateixeira@alunos.isel.pt', 41480, 'LEQ');

select *from Student

/*************************************************************/

insert into Course values('Laboratorio de Software', 'LS', 1);
insert into Course values('Ambientes Virtuais de Execucao', 'AVE', 2);
insert into Course values('Programacao Internet', 'PI', 2);
insert into Course values('Matematica 1', 'M1', 3);
insert into Course values('Redes de Computadores', 'RCP', 4);
insert into Course values('Sistemas de Gestao', 'SG', 3);

select *from Course

/************************************************************/


insert into Class values('41D', 'LS', '1617', 'summer');
insert into Class values('41N', 'LS', '1617', 'summer');
insert into Class values('42D', 'AVE', '1617', 'summer');
insert into Class values('42N', 'PI', '1516', 'winter');
insert into Class values('10D', 'M1', '1516', 'winter');
insert into Class values('31D', 'SG', '1516', 'summer');
insert into Class values('44N', 'RCP', '1516', 'winter');

select *from Class

/************************************************************/

insert into CourseAvailability values(1);
insert into CourseAvailability values(2);
insert into CourseAvailability values(3);
insert into CourseAvailability values(4);
insert into CourseAvailability values(5);
insert into CourseAvailability values(6);

select *from CourseAvailability;

/*********************************************************/

insert into Teaches values (1,'41D','LS','1617','summer');
insert into Teaches values(3,'41D','LS','1617','summer');
insert into Teaches values(3,'41N','LS','1617','summer');
insert into Teaches values(2,'42D','AVE','1617','summer');
insert into Teaches values(1,'42N','PI','1516','winter');
insert into Teaches values(3,'10D','M1','1516','winter');
insert into Teaches values(4,'44N','RCP','1516','winter');
insert into Teaches values(2,'31D','SG','1516','summer');

select *from Teaches

/************************************************************/

insert into Has values('LS', 'LEIC', 4, 1);
insert into Has values('AVE', 'LEIC', 3, 1);
insert into Has values('PI', 'LEIC', 5, 1);
insert into Has values('M1', 'LEIC', 1, 1);
insert into Has values('RCP', 'LEIC', 4, 1);
insert into Has values('RCP', 'LEIM', 4, 1);
insert into Has values('SG', 'LEIC', 3, 0);
insert into Has values('SG', 'LEIC', 5, 0);

select *from Has;

/************************************************************/

insert into Belongs values ('41D', 'LS', '1617', 'summer', 41893);
insert into Belongs values ('41D', 'LS', '1617', 'summer', 41529);
insert into Belongs values ('41D', 'LS', '1617', 'summer', 41484);
insert into Belongs values ('41D', 'LS', '1617', 'summer', 41001);

insert into Belongs values ('41N', 'LS', '1617', 'summer', 41003);
insert into Belongs values ('41N', 'LS', '1617', 'summer', 41000);
insert into Belongs values ('41N', 'LS', '1617', 'summer', 77777);
insert into Belongs values ('41N', 'LS', '1617', 'summer', 41002);

insert into Belongs values ('42D', 'AVE', '1617', 'summer', 41003);
insert into Belongs values ('42D', 'AVE', '1617', 'summer', 41000);
insert into Belongs values ('42D', 'AVE', '1617', 'summer', 77777);
insert into Belongs values ('42D', 'AVE', '1617', 'summer', 41002);
insert into Belongs values ('42D', 'AVE', '1617', 'summer', 41893);

insert into Belongs values ('42N', 'PI', '1516', 'winter', 41000);
insert into Belongs values ('42N', 'PI', '1516', 'winter', 77777);
insert into Belongs values ('42N', 'PI', '1516', 'winter', 41003);

insert into Belongs values ('10D', 'M1', '1516', 'winter', 41893);
insert into Belongs values ('10D', 'M1', '1516', 'winter', 41002);
insert into Belongs values ('10D', 'M1', '1516', 'winter', 41000);
insert into Belongs values ('10D', 'M1', '1516', 'winter', 41001);

insert into Belongs values ('31D', 'SG', '1516', 'summer', 41893);
insert into Belongs values ('31D', 'SG', '1516', 'summer', 41529);
insert into Belongs values ('31D', 'SG', '1516', 'summer', 41484);
insert into Belongs values ('31D', 'SG', '1516', 'summer', 41001);
insert into Belongs values ('31D', 'SG', '1516', 'summer', 41003);
insert into Belongs values ('31D', 'SG', '1516', 'summer', 41000);

insert into Belongs values ('44N', 'RCP', '1516', 'winter', 77777);
insert into Belongs values ('44N', 'RCP', '1516', 'winter', 41000);

select * from Belongs;

select * from Student