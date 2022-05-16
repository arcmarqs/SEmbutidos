import os
import logging
from random import randint
import flask
import warnings


warnings.filterwarnings("ignore", category=FutureWarning)


# Set up logging
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')


logging.info('Initialising app')
app = flask.Flask(__name__)

# Inicialização do dia de trabalho
num_pessoas = 0
num_atendidos = 0

# A VARIÁVEL SEGUINTE SERÁ APAGADA!! ESSA INFO DEVE VIR DO ARDUINO
num_guiche = 3

def gerar_pin():
    codigo = randint(0,9999)
    codigo_em_str = str(codigo)
    zero_filled_number = codigo_em_str.zfill(4)
    return zero_filled_number


# End-point implementation.

@app.route('/')
def index():
    return flask.render_template('index.html')

@app.route('/aumentar_num_senhas')
def aumentar_num_senhas():
    global num_pessoas
    num_pessoas += 1
    data = num_pessoas
    print("xxxxx")
    print(num_pessoas)
    print(data)
    return flask.render_template('aumentar_num_senhas.html', data=data)

@app.route('/aumentar_num_atendidos')
def aumentar_num_atendidos():
    global num_atendidos
    num_atendidos += 1
    data = num_atendidos
    print("xxxxx")
    print(num_atendidos)
    print(data)
    return flask.render_template('aumentar_num_atendidos.html', data=data)

@app.route('/ver_meu_pin')
def ver_meu_pin():
    data = gerar_pin()
    return flask.render_template('ver_meu_pin.html', data=data)

@app.route('/esta_quase')
def esta_quase():
    global num_atendidos
    # isto deve ser aplicado ao meu nº de senha ao invés da "última senha" que é
    # num_pessoas
    global num_pessoas
    if num_pessoas == num_atendidos + 1:
        data = "A seguir sou eu!"
    else:
        data = "Ainda falta..."
    return flask.render_template('/esta_quase.html', data=data)

@app.route('/arduino_mandou_id')
def arduino_mandou_id():
    cod = gerar_pin()
    global num_guiche
    global num_pessoas
    tupulo = ("{}".format(num_pessoas),"{}".format(num_guiche),"{}".format(cod))
    data = tupulo
    
    return flask.render_template('/arduino_mandou_id.html', data=data)


if __name__ == '__main__':
    # When invoked as a program.
    logging.info('Starting app')
    app.run(host='0.0.0.0', port=8080, debug=True)
