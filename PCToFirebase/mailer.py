#coding: utf-8
import smtplib
import time
from firebase import firebase #Importing firebase
import sys
from pyfcm import FCMNotification #Importing FCMNotifications
from twilio.rest import Client #Importaing Twilio library that allows us to send SMS to our phone
import configTwilio #importing configTwilio.py file to this class
from requests.exceptions import ConnectionError
from requests.exceptions import ReadTimeout


firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/') #Pointing to our Firebase through URL
firebaseURL = 'https://pchub-2018.firebaseio.com/'  #Firebase stored in variable so we can call it any in the file
push_service = FCMNotification(api_key="AAAA4_XhGR8:APA91bEcmjPYhU5sPgr549gen8c3Xl1wvMJDASHDsqMExExfZwT2dWRJ7MkGYGMSps62Gf_3a-xja_jlefzGmRcFAzEkN0yCOvQAVa4ITrybTHEp10VeTBuaoIzCWpbeVQRH_TkZbl4o") #API key for firebase

#Method which sends a SMS to users mobile number
def sendSMSToMobile(body):
	client = Client(configTwilio.account_sid, configTwilio.auth_token) #Getting account_sid and auth_token info from configTwilio.py file and storing that information in client varaible
	#Creating a message which also has the client information
	#sms_to = what phone number we are going to send info to
	#sms_from = which phone number will be used to send the message Twilio provides the number.
	message = client.messages.create(
		to = configTwilio.sms_to,
		from_ = configTwilio.sms_from_,
		body = body)
	print (message.sid)
	print ("SMS sent")

#Method which sends the user an email if temp of CPU reach a certain point
def CPUTempAlert(): 
    username = "123hello2020@gmail.com"
    password = "dingatding2020"
    FROM = "123hello2020@gmail.com"
    TO = ["123hello2020@gmail.com"]
    SUBJECT = "CPU Temperature Limit Trigged"
    TEXT = "Your CPU has reached has reached the limit you have set, please check up on your computer as something is not right."
    message = """\From: %s\nTo: %s\nSubject: %s\n\n%s """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
    try:
        server = smtplib.SMTP("smtp.gmail.com", 587) #or port 465 doesn't seem to work!
        server.ehlo()
        server.starttls()
        server.login(username, password)
        server.sendmail(FROM, TO, message)
        server.close()
        print ("successfully sent the mail - CPUEMail")
      
            
    except:
        print ("failed to send mail")
        
#Method which sends the user an email if temp of GPU reach a certain point   
def GPUTempAlert():
    username = "123hello2020@gmail.com"
    password = "dingatding2020"
    FROM = "123hello2020@gmail.com"
    TO = ["123hello2020@gmail.com"]
    SUBJECT = "GPU Temperature Limit Trigged"
    TEXT = "Your GPU has reached has reached the limit you have set, please check up on your computer as something is not right."
    message = """\From: %s\nTo: %s\nSubject: %s\n\n%s """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
    
    #temp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/6/Value')
    #if (temp >= "83.0 °C"):
    try:
        server = smtplib.SMTP("smtp.gmail.com", 587)
        server.ehlo()
        server.starttls()
        server.login(username, password)
        server.sendmail(FROM, TO, message)
        server.close()
        print ("successfully sent the mail -GPUEmail")
      
            
    except:
        print ("failed to send mail")
        


while True:
    try:
        #User set temp limits
        print ("Working")
        CPUTempLimit = firebase.get(firebaseURL, '/EmailNotification/CPUTempAlert')
        GPUTempLimit = firebase.get(firebaseURL, '/EmailNotification/GPUTempAlert')
        #Returning values from firebase that contains temp readings from hardware, such as CPU,GPU etc
        currentCPUTemp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/4/Value')
        currentGPUTemp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/3/Children/1/Children/0/Value') 
        if currentCPUTemp >= CPUTempLimit:
            CPUTempAlert()
            registration_id = firebase.get(firebaseURL, '/zTokenID')
            message_title = "CPU Alert - Attention Needed"
            message_body = "Your CPU has reached the temperature limit you have set, please check on your computer "+"Your computers current CPU tempareture is "+currentCPUTemp
            result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
            body = "CPU temperature have reached the limit you have set, please check on your computer" #Message for the sms
            sendSMSToMobile(body) #Calling sendSMSToMobile method with body, which is the message that will be sent to the user.
            print (result)
        if currentGPUTemp >= GPUTempLimit:
            GPUTempAlert()
            registration_id = firebase.get(firebaseURL, '/zTokenID')
            message_title = "GPU Alert - Attention Needed"
            message_body = "Your GPU has reached the temperature limit you have set, please check on your computer "+"Your computers current GPU tempareture is "+currentGPUTemp
            result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
            body = "GPU temperature  have reached the limit you have set, please check on your computer" #Message for the sms
            sendSMSToMobile(body) #Calling sendSMSToMobile method with body, which is the message that will be sent to the user.
            print (result)
        Frequency = firebase.get(firebaseURL, '/EmailNotification/FrequencyOfNotifications')
        time.sleep(Frequency)
    except ConnectionError as e:
        print("No Internet connection, please check your Internet connection")
        time.sleep(5)
    except ReadTimeout as b:
        print("ReadTime out error")