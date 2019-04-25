#coding: utf-8
import smtplib
import time
from firebase import firebase
import sys
from pyfcm import FCMNotification

firebase = firebase.FirebaseApplication('https://pchub-2018.firebaseio.com/')  # Firebase url
firebaseURL = 'https://pchub-2018.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script
push_service = FCMNotification(api_key="AAAA4_XhGR8:APA91bEcmjPYhU5sPgr549gen8c3Xl1wvMJDASHDsqMExExfZwT2dWRJ7MkGYGMSps62Gf_3a-xja_jlefzGmRcFAzEkN0yCOvQAVa4ITrybTHEp10VeTBuaoIzCWpbeVQRH_TkZbl4o")

def CPUTempAlert():
   
    username = "123hello2020@gmail.com"
    password = "dingatding2020"
    FROM = "123hello2020@gmail.com"
    TO = ["123hello2020@gmail.com"]
    SUBJECT = "CPU Temperature Limit Trigged"
    TEXT = "Your CPU has reached has reached the limit you have set, please check up on your computer as something is not right."
    message = """\From: %s\nTo: %s\nSubject: %s\n\n%s """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
    
    #temp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/6/Value')
    #if (temp >= "83.0 °C"):
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
        server = smtplib.SMTP("smtp.gmail.com", 587) #or port 465 doesn't seem to work!
        server.ehlo()
        server.starttls()
        server.login(username, password)
        server.sendmail(FROM, TO, message)
        server.close()
        print ("successfully sent the mail -GPUEmail")
      
            
    except:
        print ("failed to send mail")
        


while True:
    #User set temp limits
    CPUTempLimit = firebase.get(firebaseURL, '/EmailNotification/CPUTempAlert')
    GPUTempLimit = firebase.get(firebaseURL, '/EmailNotification/GPUTempAlert')
    #Returning values from firebase that contains temp readings from hardware, such as CPU,GPU etc
    currentCPUTemp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/4/Value')
    currentGPUTemp = firebase.get(firebaseURL, '/PCHub/ComputerStatistics/number/Children/0/Children/3/Children/1/Children/0/Value') 
    if currentCPUTemp >= CPUTempLimit:
        CPUTempAlert()
        registration_id = "eDMylVCYWws:APA91bG1YJmttle8Rm8XWAArxSZCG8tGKLiVK3jqUZz-3gy832XSq7lEtwf0ozfURhRHLMycOh4F9uNaaSMrAOkgatMbFUxE7iZ7NlIgisuKpGFSIja66ZQkuBfajm7UPi3aQlJW1E89"
        message_title = "CPU Alert - Attention Needed"
        message_body = "Your CPU has reached the temperature limit you have set, please check on your computer "+"Your computers current CPU tempareture is "+currentCPUTemp
        result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
        print (result)
    if currentGPUTemp >= GPUTempLimit:
        GPUTempAlert()
        registration_id = "eDMylVCYWws:APA91bG1YJmttle8Rm8XWAArxSZCG8tGKLiVK3jqUZz-3gy832XSq7lEtwf0ozfURhRHLMycOh4F9uNaaSMrAOkgatMbFUxE7iZ7NlIgisuKpGFSIja66ZQkuBfajm7UPi3aQlJW1E89"
        message_title = "GPU Alert - Attention Needed"
        message_body = "Your GPU has reached the temperature limit you have set, please check on your computer "+"Your computers current GPU tempareture is "+currentGPUTemp
        result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
        print (result)
    Frequency = firebase.get(firebaseURL, '/EmailNotification/FrequencyOfNotifications')
    time.sleep(Frequency)   