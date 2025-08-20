@login

Feature: Checking Login Module
	

	Background:
	  Given user is on login page

#	testcase 1 		SST-t9 login in the website with valid credentials
	Scenario: Successful login with valid credentials
	  When user enter a valid username and password
	  		|username|password|
	  		|admin|admin|
	  And user enter a valid captcha
	  And user click on validate 
	  And user accepting the alert
	  And user click on the login button
	  And user accepting the alert
	  Then user should be redirected to the home page
	  
	  
	  
	  Scenario: To validate invalid username error message
	  When user enters a username as "wrongUser"
	  And user enters a password as "admin"		
	  And user enter a valid captcha
	  And user click on validate 
	  And user accepting the alert
	  And user click on the login button
	  Then username error message should be displayed
	  
	  
	  
	  
	   Scenario: To validate invalid password error message
	  When user enters a username as "admin"
	  And user enters a password as "wrongPassword"		
	  And user enter a valid captcha
	  And user click on validate 
	  And user accepting the alert
	  And user click on the login button
	  Then password error message should be displayed
	  
	  
	  
	  
	  Scenario: To validate invalid captcha error message
	  When user enter a valid username and password
	  		|username|password|
	  		|admin|admin|
	  And user enter a captcha as "invalidCaptcha"
	  And user click on validate 
	  Then captcha error message should be displayed
	  
	  
	  
	  
	  
	  Scenario: To validate empty username error message
	  When user enters a username as ""
	  And user enters a password as "admin"		
	  And user enter a valid captcha
	  And user click on validate 
	  And user accepting the alert
	  And user click on the login button
	  Then username error message should be displayed
	  
	  
	    Scenario: To validate empty password error message
	  When user enters a username as "admin"
	  And user enters a password as ""		
	  And user enter a valid captcha
	  And user click on validate 
	  And user accepting the alert
	  And user click on the login button
	  Then password error message should be displayed
	  
	   Scenario: To validate empty captcha error message
	  When user enter a valid username and password
	  		|username|password|
	  		|admin|admin|
	  And user enter a captcha as ""
	  And user click on validate 
	  Then captcha error message should be displayed
	  
	  
	  
	  
	  
#     ********	  sst-t11 reset password pannel *******
		Scenario: checking for forgot your password
			When user click on click here to reset 
			Then user should be redirected to reset panel
		

#		********  sst-t7 validate signin using google  ***********
		Scenario: Checking for signup using google functionality
			When user click on signup using google
			And user should be redirected to singup using goggle page
			And user enter the email in signup page as "a@a.com"
			And click on next button in signup page
			Then user should be redirected to the home page
			
			
			
			
			
#		**********  sst-t8  remember on this computer **********
		Scenario: checking remember me functiality
			  When user enter a valid username and password
		  		|username|password|
		  		|admin|admin|
			  And user enter a valid captcha
			  And user click on validate 
			  And user accepting the alert
			  And user click on remember me button
			  And user accepting the alert 
			  And user accepting the alert 
			  And user click on the login button
			  And user accepting the alert
			  Then user should be redirected to the home page
		
		
		
		
#		*******	sst-t13 check logout button *********
		Scenario: check logout button on home page
		  When user enter a valid username and password
		  		|username|password|
		  		|admin|admin|
		  And user enter a valid captcha
		  And user click on validate 
		  And user accepting the alert
		  And user click on the login button
		  And user accepting the alert
		  And user should be redirected to the home page
		  And user click on logout button
		  Then user should be redirected to login page
		  
		  
#		 ********* sst-t12 email validateion in reset panel *******
		Scenario Outline: email validation in reset panel for valid email
			When user click on click here to reset 
			Then user should be redirected to reset panel
			And user enters email as "<email>"
			And user click on reset password 
			And user accepting the alert if any 
			Then user get a password when email is valid and any error msg if email is invalid
			
		Examples:
			|email|
			|ram@gmail.com|
			||
			|asdfas|
			|jfaklsdjfasj|
			|kajsdklfjkalsjdfklajf|
		
			
	  
	  