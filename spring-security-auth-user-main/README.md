### Training

## Register
http://localhost:8080/api/v1/training/student/register
{ 
    "firstname": "toto", 
    "lastname": "toto", 
    "email": "toto3@gmail.com", 
    "password":"569874",
    "dob": "1992-12-24",
    "fournitureList": [
         { 
            "name":"gomme", 
            "quantity":"45", 
            "price":700
        } 
    ]
}


## Autenticate
http://localhost:8080/api/v1/training/student/authenticate
{
"email":"email@email.com",
"password":"12344"
}
### TODO
## - Add OneToMany

## update
http://localhost:8080/api/v1/training/student/1?password=kkdms1165956q**/