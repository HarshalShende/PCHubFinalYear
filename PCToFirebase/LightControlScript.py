#!/usr/bin/python
# -*- coding: utf-8 -*-

RED_PIN   = 17
GREEN_PIN = 22
BLUE_PIN  = 24

import os
import sys
import termios
import tty
import pigpio
import time
from firebase import firebase

firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/')  # Firebase url
firebaseURL = 'https://pchub-2018.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script


brightChanged = False
abort = False
state = True

pi = pigpio.pi()

def setLights(pin, brightness):
	realBrightness = int(int(brightness) * (float(bright) / 255.0))
	pi.set_PWM_dutycycle(pin, realBrightness)

while True:
    bright = firebase.get(firebaseURL, '/Brightness')   #Max = 255
    r = firebase.get(firebaseURL, '/Red') #Max = 255, Min = 0
    g = firebase.get(firebaseURL, '/Green')
    b = firebase.get(firebaseURL, '/Blue')
    setLights(RED_PIN, r)
    setLights(GREEN_PIN, g)
    setLights(BLUE_PIN, b)
    
pi.stop()

