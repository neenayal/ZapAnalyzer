
ZapAnalyzer
Author : Neeraj Nayal
Date   : 12/07/2015
Application url : https://zs-analyzer.appspot.com

FAQ :

* Technologies :
Project Management tool : Maven 3.1
Java 7
Google App Engine 1.9.22

* How to set up the application
Setup following values

1. 	com.zapstitch.analyzer.base.Application.ACCESS_TYPE = "offline"
2.	com.zapstitch.analyzer.base.Application.CLIENT_ID = User's CLient Id
3.	com.zapstitch.analyzer.base.Application.CLIENT_SECRET = User's Client secret
4.	com.zapstitch.analyzer.base.Application.SCOPE = {"https://www.googleapis.com/auth/userinfo.profile",
			 "https://www.googleapis.com/auth/userinfo.email","https://www.googleapis.com/auth/gmail.readonly"}
5. com.zapstitch.analyzer.base.Constants.CALLBACK_URI = application's callback url
6. set	<app.id>zproject id</app.id> in pom file
Pom 
			 
* how to compile :

follow the following step :
[To build] mvn clean install
[To Test App locally] mvn appengine:devserver
[Update the Google app engine project] mvn appengine:update

* To increase the performance 
keep the size of BATCH_SIZE close to 100.Max value allowed by google is 100. 
for free account , only 10 request/sec is allowed so ZapAnalyzer's BATCH_SIZE is 10.

* Current version :
Do not run while hosting in google App Engine. 
Working fine in local environment.

* Future Scope :
1. Application hosting in google app engine.
2. Application design improvement.

*Reference :

Jquery :
http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css
http://code.jquery.com/jquery-1.10.2.js
http://code.jquery.com/ui/1.10.4/jquery-ui.js
    
	
Gmail API :
https://developers.google.com/gmail/api/