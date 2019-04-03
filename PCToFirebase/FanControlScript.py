from grovepi import *
import time
from firebase import firebase


firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/')  # Firebase url
firebaseURL = 'https://pchub-2018.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script

# Connect the Grove Ultrasonic Ranger to digital port D4
# SIG,NC,VCC,GND

Relay_pin = 2

pinMode(Relay_pin,"OUTPUT")
  
def fanControlOn():
    fanControl = firebase.get(firebaseURL, '/fanControl')
    if fanControl == 1:
        digitalWrite(Relay_pin,1)
        
def fanControlOff():
    fanControl = firebase.get(firebaseURL, '/fanControl')
    if fanControl == 0:
        digitalWrite(Relay_pin,0)
    
    
while True:
    fanControlOn()
    fanControlOff()




