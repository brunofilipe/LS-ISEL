/****************************************************** PROGRAMME MANAGEMENT **********************************************************/

/************* GET / programmes *****************/

select * from Programme

/************* POST / programmes *****************/

insert into Programme values ('Licenciatura Engenharia Química', 'LEQ', 6)

/************* GET / programmes / {pid} *****************/

select * from Programme where programmeAcr = 'LEIC'

/************* POST / programmes / {pid} / courses *****************/

insert into Has values ('M1', 'LEIC', 1, 1)

/************* GET / programmes / {pid} / courses *****************/

select * from Has where programmeAcr = 'LEIC'



