#!/usr/bin/python
# -*- coding: utf-8 -*-

import threading

import pigpio
from firebase import firebase

RED_PIN = 17  # Using Pin 17 to Red LED
GREEN_PIN = 22  # Using Pin 22 to Green LED
BLUE_PIN = 24  # Using Pin 24 to Blue LED

firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/')  # Firebase url
firebaseURL = 'https://pchub-2018.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script

pi = pigpio.pi()


# Function that allows us to change the brightness of lights
def set_lights(pin, brightness):
    real_brightness = int(int(brightness) * (float(bright) / 255.0))
    pi.set_PWM_dutycycle(pin, real_brightness)
    
def getlight(r,g,b):
    set_lights(RED_PIN, r)
    set_lights(GREEN_PIN, g)
    set_lights(BLUE_PIN, b)
    



#threading.Thread(target=set_lights()).start()

while True:
    bright = firebase.get(firebaseURL, '/Brightness')  # Max = 255, Min = 0 , getting value from Firebase
    r = firebase.get(firebaseURL, '/Red')  # Max = 255, Min = 0 , getting value from Firebase
    g = firebase.get(firebaseURL, '/Green')  # Max = 255, Min = 0 , getting value from Firebase
    b = firebase.get(firebaseURL, '/Blue')  # Max = 255, Min = 0 , getting value from Firebase
    set_lights(RED_PIN, r)
    set_lights(GREEN_PIN, g)
    set_lights(BLUE_PIN, b)
    bright = firebase.get(firebaseURL, '/Brightness')  # Max = 255, Min = 0 , getting value from Firebase
    r = firebase.get(firebaseURL, '/Red')  # Max = 255, Min = 0 , getting value from Firebase
    g = firebase.get(firebaseURL, '/Green')  # Max = 255, Min = 0 , getting value from Firebase
    b = firebase.get(firebaseURL, '/Blue')  # Max = 255, Min = 0 , getting value from Firebase
    set_lights(RED_PIN, r)
    set_lights(GREEN_PIN, g)
    set_lights(BLUE_PIN, b)


pi.stop()
