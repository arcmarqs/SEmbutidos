
import os
import logging
from random import randint
import flask
import warnings

# Set up logging
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')

logging.info('Initialising app')
app = flask.Flask(__name__)

# State

currentNumber = 0
highestNumber = 0
guicheArray = [
    {
        "name": "Guiche 1",
        "number": None,
        "pin": None
    }, {
        "name": "Guiche 2",
        "number": None,
        "pin": None
    }
]


# Helper functions

def generatePin():
    return str(randint(0, 9999)).zfill(4)


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

@app.route('/getTicket')
def getTicket():
    global guicheArray
    global currentNumber
    global highestNumber

    highestNumber = highestNumber + 1;

    for guiche in guicheArray:
        if (guiche["number"] == None):
            currentNumber = highestNumber
            guiche["number"] = highestNumber
            guiche["pin"] = generatePin()
            break

    return flask.jsonify({
        "highestNumber": highestNumber
    })

@app.route('/checkIn')
def checkIn():
    global guicheArray
    global currentNumber
    global highestNumber
    
    guicheNumber = flask.request.args.get('guicheNumber')
    pinCode = flask.request.args.get('pinCode')

    wasCheckedIn = False

    for guiche in guicheArray:
        if (str(guiche["number"]) == guicheNumber and guiche["pin"] == pinCode):
            wasCheckedIn = True
            guiche["number"] = None
            guiche["pin"] = None
            if(highestNumber > currentNumber):
                currentNumber = currentNumber + 1
                guiche["number"] = currentNumber
                guiche["pin"] = generatePin()
            break

    return flask.jsonify({
        "wasCheckedIn": wasCheckedIn
    })


if __name__ == '__main__':
    # When invoked as a program.
    logging.info('Starting app')
    app.run(host='0.0.0.0', port=8080, debug=True)
