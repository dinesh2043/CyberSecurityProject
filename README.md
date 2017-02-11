# CyberSecurityProject
Course Project To develop a Web application with top 5 vulnerablities

As a default I have created 3 default users to provide the login, authentication and authorization features for the application. This is the web forum application which provides the features to create a user account and post comment in the forum. The login credentials of those user for using application are as follows;

User Name	Password
admin	admin
user	user
userA	userA

The vulnerabilities available in this application are discussed in the upcoming section of this document. In the beginning there are steps to check the vulnerabilities and after that there is a discussion on how to prevent these security flaws. 
Issue: SQL Injection

Steps to reproduce:
1. Login to the application using username and password mentioned above.
2. Go to the Forum page
3. Select the text field for “Search User With ID”
4. Type “105 or 1=1” to provide always true statement to the database
5. Click Submit
6. Now you will be able to see all the registered user in the database.

This security vulnerabilities can be overcome using prepared statements where user inputs can be escaped and validated. In this application I have used JPA which are secured from SQL injection the code for this implementation can be found in ForumController class inside findUser() method. This secured codes are commented to show the execution of unsecured execution of SQL statements.
    
Issue: Stored XSS Attack
Steps to reproduce:
1. Open the signup page of the application
2. Signup form appears in the page
3. Provide normal username and password.
3. In address field type this XSS script "<Script>alert(document.cookie);</Script>".
4. Submit the signup request.
5. Now, login as an admin user to see the signup information’s.
6. Click the Admin page.
7. When the Admin page loads it displays the alert message stored in database.

This security vulnerability can be prevented by validating the user inputs in the client side, while displaying the data stored in the database the data can be escaped to use it as data but not a script. To prevent the storing of these data into the database we can enable csrf() method in security configuration file and using this “<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />” script in index.html page.

Issue: Access Control
Steps to reproduce:
1. Login as admin user and check the role of userA, it is ROLE_USER and ROLE_USER should not have admin rights to the application.
2. Login to the application using userA as both username and password.
3. Click Admin page 
4. This userA can access the Admin information even though he has the role ROLE_USER

This vulnerability is cause by the broken authentication and authorization for admin page. Security configuration should provide access to the Admin page for user with role “ROLE_ADMIN”, but it has been misconfigured and it provides access with ROLE_USER also. This can be fixed by removing the role USER from the access of Admin page.

Issue: CSRF Prompt-By Pass
Steps to reproduce:
1. Login to the application.
2. Go to the Forum page.
3. Provide a title "Claim the Award provided by Admin".
4. In the message field type these scripts to perform CSRF attack  
/**
“<img src="http://localhost:8080/done/attack?Screen=XXX&menu=YYY&transferFunds=5000"
onload="document.getElementById('image2').src='http://localhost:8081/WebGoat/attack?Screen=XXX&menu=YYY&transferFunds=CONFIRM'">
</img>
<img id="image2" width="800" scrolling=yes height="300" src='http://img.allw.mn/content/lifestyle/2013/05/1_cash-only.jpg'>
<form accept-charset='UNKNOWN' method='POST' action='attack?Screen=XXX&menu=YYY' enctype='application/x-www-form-urlencoded'>
	<input name='transferFunds' type='submit' value='CONFIRM'>
	<input name='transferFunds' type='submit' value='CANCEL'>
</form>
</img>"
**/
5. Submit the message.
6. Find the message displayed in the page. 
7. Press "CONFORM" button to conform the award.
8. When the button is pressed you are redirected to the malicious application.

These vulnerabilities can also be prevented by following the procedure explained in the section of stored XSS attack.

Issue: Insecure Direct Object Reference
Steps to reproduce:
1. Run the application
2. Try to access to this file through URL: “http://localhost:8080/css/hibernate.reveng.sql”
3. You will be asked if you want to download this SQL file.
4. Save the file.
5. All the sensitive data and the database schema of this application are vulnerable now.
This is the vulnerability caused by providing the unnecessary access to the files of the application. We can prevent this by removing the access provided to this file in security configuration file in line 34.

Issue: Using Vulnerable Software Development Libraries
Steps to reproduce:
1. Go to the pom.xml and remove the comments for plugins for dependency check. 
2. Run maven dependency check to see the software vulnerabilities of used libraries versions.
3. When the dependency check is complete, html file will be created in target folder
4. Open the file and see the vulnerabilities available with the current versions of the library.

To prevent these vulnerabilities up-to-date versions of the libraries should be used and before the software deployment, developers should perform the dependency checks. If there exists some vulnerabilities then they should be mitigated to make the application secure.

