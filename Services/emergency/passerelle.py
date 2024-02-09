import paho.mqtt.client as mqtt
from random import randrange
import time
import json
import requests
from flask import Flask, json , request
import serial
import time

SERIALPORT = "COM5"
BAUDRATE = 115200
ser = serial.Serial()
timeout = None
api = Flask(__name__)
HOST = "localhost"
PORT = 1883
TOPIC = "sensors/fire"
USER = 'user'
PWD = 'password'

def initUART():
    ser.port = SERIALPORT
    ser.baudrate = BAUDRATE
    ser.bytesize = serial.EIGHTBITS  # number of bits per bytes
    ser.parity = serial.PARITY_NONE  # set parity check: no parity
    ser.stopbits = serial.STOPBITS_ONE  # number of stop bits
    ser.timeout = None  # block read

    # ser.timeout = 0             #non-block read
    # ser.timeout = 2              #timeout block read
    ser.xonxoff = False  # disable software flow control
    ser.rtscts = False  # disable hardware (RTS/CTS) flow control
    ser.dsrdtr = False  # disable hardware (DSR/DTR) flow control
    # ser.writeTimeout = 0     #timeout for write
    print("Starting Up Serial Monitor")
    try:
        ser.open()
    except serial.SerialException:
        print("Serial {} port not available".format(SERIALPORT))
        exit()
def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
    # S'abonner à tous les sous-topics de sensors/fire
    client.subscribe(f"{TOPIC}/#")


def on_message(client, userdata, msg):
    print(msg.topic + " " + str(msg.payload))


def on_publish(client, userdata, mid):
    print(" ")

def send_emer(msg):
    msg_d = msg.split("]".encode())
    msg_d[0] += "]".encode()
    if msg_d[0][:1] == "c".encode():
        print(msg_d[0][1:].decode('utf-8'))
        r = requests.post('http://localhost:8080/incident/new', data=msg_d[0][1:].decode('utf-8'))
    if msg_d[0][:1] == "t".encode():
        print("")
        #r = requests.post('http://127.0.0.1:port/trucks', json=msg_d[0][1:])
    if msg_d[0][:1] == "i".encode():
        print("")
        #r = requests.post('http://127.0.0.1:port/infos', json=msg_d[0][1:])

client = mqtt.Client("API")

# callbacks
client.on_connect = on_connect
client.on_message = on_message
client.on_publish = on_publish

client.username_pw_set(USER, PWD)
client.connect(HOST, PORT, 60)

client.loop_start()
initUART()
try:
    while True:
        data = ser.readline()
        print(data)
        data_split = data.split("},".encode())
        if data_split[0][:1] == "c".encode():
            send_emer(data)
            data_split[0] = data_split[0][1:]
            i = 0
            while i < len(data_split):
                data_split[i] = data_split[i][1:]
                data_split[i] += "}".encode()
                # Publier des données sur le topic
                if i == len(data_split)-1:
                    data_split[i] = data_split[i].split("]".encode())
                    data_split[i] = data_split[i][0]
                json_d = json.loads(data_split[i])
                id = json_d["id"]
                dat = data_split[i].split("ue".encode())
                client.publish(f"{TOPIC}/{id}", payload=dat[0]+dat[1])
                i += 1
        if data_split[0][:1] == "t".encode():
            send_emer(data)
        if data_split[0][:1] == "i".encode():
            send_emer(data)
        time.sleep(0.01)

except KeyboardInterrupt:
    pass

client.loop_stop()
client.disconnect()
