from flask import Flask, json , request
import serial
import time

SERIALPORT = "COM4"
BAUDRATE = 115200
ser = serial.Serial()
timeout = None
api = Flask(__name__)

def sendUARTMessage(msg):
    for i in range(0, len(msg), 16):
        time.sleep(0.01)
        chunk = msg[i:i + 16]
        while len(chunk) < 16:
            chunk = chunk + " "
        chunk = chunk+"END"
        ser.write(chunk.encode())
        print(chunk)
        data = ser.read(8)
        print(data)
    print("Message <" + msg + "> sent to micro-controller.")

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

initUART()
ser.close()

@api.route('/capteurs', methods=['POST'])
def send_capteurs():
    if request.is_json:
        msg = json.dumps(request.get_json())
        initUART()
        sendUARTMessage("c"+str(msg))
        ser.close()
    return {"error": "Request must be JSON"}, 415

@api.route('/trucks', methods=['POST'])
def send_trucks():
    if request.is_json:
        msg = json.dumps(request.get_json())
        initUART()
        sendUARTMessage("t"+str(msg))
        ser.close()
    return {"error": "Request must be JSON"}, 415

@api.route('/infos', methods=['POST'])
def send_infos():
    if request.is_json:
        msg = json.dumps(request.get_json())
        initUART()
        sendUARTMessage("i"+str(msg))
        ser.close()
    return {"error": "Request must be JSON"}, 415

if __name__ == '__main__':
    api.run()
