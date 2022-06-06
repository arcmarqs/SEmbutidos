import sseclient

messages = sseclient.SSEClient('http://127.0.0.1:8000/listen')

for msg in messages:
    print(msg)