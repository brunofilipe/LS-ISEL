/****************************************************** COURSE MANAGEMENT **********************************************************/

/************* POST / courses *****************/

insert into Course values ('Matematica 1' , 'M1', 1)

/************* GET / courses *****************/

select * from Course

/************* GET / courses / {acr} *****************/

select * from Course where courseAcr = 'AVE'