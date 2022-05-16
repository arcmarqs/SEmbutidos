Como correr a API?

`python3 main.py`

Endpoints:

`GET /getState` -> Retorna o estado interno total

`GET /getCurrentNumber` -> Retorna o currentNumber do estado interno

`GET /getGuicheArray` -> Retorna o guicheArray do estado interno

`GET /getTicket` -> Devolve um novo ticket e actualiza o estado interno

`GET /checkIn?guicheNumber=YY&pinCode=XXXX` -> Faz check in no guiche número YY com o pin code XXXX e actualiza o estado interno

