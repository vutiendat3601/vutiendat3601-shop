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
  - Go to backend directory:
  \$ cd backend
  - Run command:
  \$ ./mvnw spring-boot:run
