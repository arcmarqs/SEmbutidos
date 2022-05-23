import time
import requests

while True:
    requests.get('http://172.17.66.214:8080/ping')
    time.sleep(1)