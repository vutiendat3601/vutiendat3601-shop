## Git Flow
- Create a new branch for feature:
$ git checkout -b feature/\<jiraTicket\> <br />
- Remember to merge latest code from branch 'dev' to new feature branch before push:
\$ git switch dev <br />
\$ git pull <br />
\$ git switch feature/\<jiraTicket\> <br />
\$ git merge dev <br />

- After push to GitHub Repository, please make a Pull Request from the 'feature/\<jiraTicket\>' branch to 'dev' branch

## Start Project in local for Development
### Prerequisite: 
  - Docker installed and started
  - JDK 17 installed
  - NodeJS 20 installed
### Start Backend:
  - Go to backend-v2 directory:
  \$ cd backend-v2 <br />
  - Run command:
  \$ ./gradlew :booRun <br />
### Start Frontend:
 - Go to frontend-v2 directory:
  \$ cd frontend-v2 <br />
  - Run command:
  \$ npm install <br />
  \$ npm i @angular/cli -g <br />
  \$ ng serve --host 0.0.0.0 <br />
  
