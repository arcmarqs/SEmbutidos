
import os
import queue
import logging
from random import randint
from unittest import result
import flask
import warnings
from flask_sse import sse

# Set up logging
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')

logging.info('Initialising app')
app = flask.Flask(__name__)



class MessageAnnouncer:

    def __init__(self):
        self.listeners = []

    def listen(self):
        q = queue.Queue(maxsize=5)
        self.listeners.append(q)
        self.listeners[0].put_nowait(format_sse(data="You have successfully connected."))
        return q

    def announce(self, msg):
        print("yyy",len(self.listeners))
        for i in reversed(range(len(self.listeners))):
            print("xxxxxxxxxxxxxx",self.listeners)
            print(self.listeners[i])
            try:
                self.listeners[i].put_nowait(msg)
            except queue.Full:
                del self.listeners[i]



# Instance of MessageAnnouncer
announcer = MessageAnnouncer()


# State

currentNumber = 0
highestNumber = 0
guicheArray = [
    {
        "guichetId": 1,
        "ticketNumber": None,
        "pin": None
    }, {
        "guichetId": 2,
        "ticketNumber": None,
        "pin": None
    }
]
usersDictionary = {}

# Helper functions

def generatePin():
    return str(randint(0, 9999)).zfill(4)

def format_sse(data: str, event=None) -> str:
    msg = f'data: {data}\n\n'
    if event is not None:
        msg = f'event: {event}\n{msg}'
    return msg


# End-points

@app.route('/')
def index():
    global currentNumber
    global highestNumber
    global guicheArray

    results = {
        "currentNumber": currentNumber,
        "guicheArray": guicheArray
    }
    return flask.render_template('index.html', data = results)

@app.route('/sendNotification')
def sendNotification():
    global currentNumber
    msg = format_sse(data=currentNumber)
    announcer.announce(msg=msg)
    return {}, 200

@app.route('/listen', methods=['GET'])
def listen():

    def stream():
        messages = announcer.listen()  # returns a queue.Queue
        while True:
            msg = messages.get()  # blocks until a new message arrives
            yield msg

    return flask.Response(stream(), mimetype='text/event-stream')

@app.route('/getState')
def getState():
    return flask.jsonify({
        "currentNumber": currentNumber,
        "highestNumber": highestNumber,
        "guicheArray": guicheArray
    })


@app.route('/getCurrentNumber')
def getCurrentNumber():
    return flask.jsonify({
        "currentNumber": currentNumber
    })

@app.route('/getGuicheArray')
def getGuicheArray():
    return flask.jsonify(guicheArray)

@app.route('/getTicket', methods = ['GET', 'POST'])
def getTicket():
    print("A emitir senha...")
    ipAndroid = flask.request.remote_addr
    print("O endereço IP de quem pediu a senha é:", ipAndroid)
    global guicheArray
    global currentNumber
    global highestNumber
    global usersDictionary

    highestNumber += 1;
    pinCode = generatePin()

    usersDictionary[highestNumber] = (pinCode,ipAndroid)

    print(usersDictionary)
    return flask.jsonify({
        "highestNumber": highestNumber,
        "pinCode":pinCode,
    })         


@app.route('/checkOut')
def checkOut():
    global guicheArray
    global currentNumber
    global highestNumber
    global usersDictionary
    guichetId = flask.request.args.get("guichetId", type=int)


    print("Checking out",guichetId)

    guiche = guicheArray[guichetId]
    print(str(guiche["ticketNumber"]))
    print(currentNumber)
    print(guiche["guichetId"])
    print("eval" + str(guiche["guichetId"]) == guichetId)
    guiche["ticketNumber"] = None
    guiche["pin"] = None
    if(highestNumber > currentNumber):
        currentNumber += 1
        guiche["ticketNumber"] = currentNumber
        guiche["pin"] = usersDictionary[currentNumber][0]
    
    msg = format_sse(data=currentNumber)
    announcer.announce(msg=msg)

    return flask.jsonify({
        "this_guichet": guichetId,
        "currentNumber": currentNumber,
        "pinCode": usersDictionary[currentNumber][0]
    })

if __name__ == '__main__':
    # When invoked as a program.
    logging.info('Starting app')
    app.run(host='0.0.0.0', port=8080, debug=True)
