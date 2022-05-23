import sseclient

messages = sseclient.SSEClient('http://172.17.66.214:8080/listen')

for msg in messages:
    print(msg)