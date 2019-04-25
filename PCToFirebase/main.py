from grovepi import *
import time
from firebase import firebase
from threading import Thread
import pigpio
import smtplib
from requests.exceptions import ConnectionError
from requests.exceptions import ReadTimeout

firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/')  # Firebase url
firebaseURL = 'https://pchub-2018.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script
pi = pigpio.pi()



RED_PIN = 17# Using Pin 22 to Red LED
GREEN_PIN = 27  # Using Pin 27 to Green LED
BLUE_PIN = 22  # Using Pin 17 to Blue LED
Relay_pin = 2
pinMode(Relay_pin,"OUTPUT")
Relay_pinPCControl = 3
pinMode(Relay_pinPCControl,"OUTPUT")


def relayControl():
    computerControl = firebase.get(firebaseURL, '/computerControl')
    if computerControl == 1:
        digitalWrite(Relay_pinPCControl,1)
        time.sleep(1)
        digitalWrite(Relay_pinPCControl,0)
        firebase.put(firebaseURL, '/computerControl',0)

#Method that turns on the fan
def fanControlOn():
    fanControl = firebase.get(firebaseURL, '/fanControl')
    if fanControl == 1:
        digitalWrite(Relay_pin,1)
    elif fanControl == 0:
        digitalWrite(Relay_pin,0)

#Method that turns off the fan        
def fanControlOff():
    fanControl = firebase.get(firebaseURL, '/fanControl')
    if fanControl == 0:
        digitalWrite(Relay_pin,0)
        
# Function that allows us to change the brightness of lights and also assign values to each pin
def set_lights(pin, brightness):
    real_brightness = int(int(brightness) * (float(bright) / 255.0))
    pi.set_PWM_dutycycle(pin, real_brightness)
    
            
while True:
    try:
        bright = firebase.get(firebaseURL, '/Brightness')  # Max = 255, Min = 0 , getting value from Firebase
        r = firebase.get(firebaseURL, '/Red')  # Max = 255, Min = 0 , getting value from Firebase
        g = firebase.get(firebaseURL, '/Green')  # Max = 255, Min = 0 , getting value from Firebase
        b = firebase.get(firebaseURL, '/Blue')  # Max = 255, Min = 0 , getting value from Firebase
        set_lights(RED_PIN, r)
        set_lights(GREEN_PIN, g)
        set_lights(BLUE_PIN, b)
        relayControl()
        fanControlOn()
        #fanControlOff()
    except ConnectionError as e:
        print("No Internet connection, please check your Internet connection")
        time.sleep(5)
    except ReadTimeout as b:
        print("ReadTime out error")

    



pi.stop()