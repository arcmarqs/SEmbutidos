Como correr a API?

gunicorn -k gevent -b 127.0.0.1:8000  wsgi:app


Endpoints:

`GET /getState` -> Retorna o estado interno total

`GET /getCurrentNumber` -> Retorna o currentNumber do estado interno

`GET /getGuicheArray` -> Retorna o guicheArray do estado interno

`GET /getTicket` -> Devolve um novo ticket e actualiza o estado interno

`GET /checkIn?guicheNumber=YY&pinCode=XXXX` -> Faz check in no guiche número YY com o pin code XXXX e actualiza o estado interno

