## Git Flow
- Create a new branch for feature:
$ git checkout -b feature/\<jiraTicket\>
- Remember to merge latest code from branch 'dev' to new feature branch before push:
\$ git switch dev
\$ git pull
\$ git switch feature/\<jiraTicket\>
\$ git merge dev

- After push to GitHub Repository, please make a Pull Request from the 'feature/\<jiraTicket\>' branch to 'dev' branch

## Start Project in local for Development
### Prerequisite: 
  - Docker installed and started
  - JDK 17 installed
  - NodeJS 20 installed
### Start Backend:
  - Go to backend-v2 directory:
  \$ cd backend-v2
  - Run command:
  \$ ./gradlew :booRun
### Start Frontend:
 - Go to frontend-v2 directory:
  \$ cd frontend-v2
  - Run command:
  \$ npm install
  \$ npm i @angular/cli -g
  \$ ng serve --host 0.0.0.0
  
