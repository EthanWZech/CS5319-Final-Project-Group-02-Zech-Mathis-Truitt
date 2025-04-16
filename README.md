# Loom
Project for Software Design &amp; Architecture 2025. Loom is a live chat service organized into threads. Users can create threads about topics, and then leave comments on them, updating everyone else on that thread in real-time, therfore facilitating live communication.

# Structure and Execution
This repository contains two versions of the same project, one implemented with a pub-sub architecture, and another with a client-server architecture. As such, the instructions for running each version of the project are the same. When file paths are described, you can assume that they descend from the Unselected (client-server) and Selected (pub-sub) directories, because the structure under each is mostly identical.

## Database
Postgres was used as the database system for this project. This is a tutorial for [installing Postgres](https://www.youtube.com/watch?v=GpqJzWCcQXY). You will need to use pgadmin to create a database named "loom", all lower case.

## Backend
The backend is located in ../Spring/loom/.

It is a Java Springboot project. I recommend opening ../Spring/loom in Intellij, but any Java IDE should work. It is a maven project; Intellij will prompt you to run the maven script when the project is opened in the bottom right corner. 

To ensure the connection with Postgres, you can navigate to ../Spring/loom/src/main/resources/application.properties, and change the values for:
- spring.datasource.url=jdbc:postgresql://localhost:5432/loom
- spring.datasource.name=loom
- spring.datasource.username=postgres
- spring.datasource.password=password
To match your Postgres credentials and setup.

Now you can run the project from ../Spring/loom/src/main/java/com/pg2/loom/LoomApplication.java. When ran for the first time, if using Intellij, you will be prompted to enable annotation processing. Enable this. If you miss it, you can do it through Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors to check Enable annotation processing and Obtain processors from project classpath.

The project should complete its Spring initialization without any problems.

If you run into the issue of the port already being taken, you will need to go into the command prompt to kill the process using the port. A group member had this happen with some Apache process. On windows, in an administrator powershell, you can run

```
Get-Process -Id (Get-NetTCPConnection -LocalPort {YourPortNumberHere}).OwningProcess
```

And then, using the process id obtained in the last column about the process, use

```
taskkill /PID {pid}
```

## Frontend
The frontend is located in ../React/loom.

The frontend is a React Vite project. I recommend working with it through Visual Studio Code.

You first need to navigate to ../React/loom, and then run in the console

```
npm install
```

After that, you can start the project using

```
npm run dev
```

and opening the localhost port that is reported in the console. This should be [localhost:5173](http://localhost:5173).

You can open a second frontend client by navigating to the same ../React/loom folder and running

```
npm run dev2
```

which will open [localhost:5174](http://localhost:5174). You can use this to run two clients and test the communication between them.

# Using Loom
Loom will automatically open on the homepage. You will see the ten newest threads. You can change your name in the top right, or make a new post. By clicking on a post, you will view that post and all of its comments. You can also upvote or downvote the post with the arrows in the top right corner of the post. You can write a comment at the bottom of the post, or respond to other comments. By clicking the Loom logo in the top right, you go back to the homepage. Making a new post allows you to enter a title, a tag, an optional image url, and post text, and will show up on the homepage at the top after posting.

Any posts will be reflected accross all clients connected to loom, and will update in real (or semi-real) time.

# Architecture Details

## Differences
As mentioned earlier, this project was implemented in the pub-sub, and then the client-server styles.

The main difference in each of these implementations is the way that the backend and frontend communicate with each other. In pub-sub (the selected directory), they communicate using websockets. The backend, and each frontend client are treated as both subscribers and publishers. The backend is subscribed to all channels (implemented as websockets), while each frontend client is either subscribed to the homepage channel for retrieving and posting threads, or a channel for a specific thread. When a frontend client enters a thread, it is subscribed to a unique web socket for that thread. If the socket doesn't already exist, it is created and the backend is also subscribed. If all frontend subscribers leave, the backend also unsubscribes and the socket is closed. Subscribers to the hompage channel can publish new threads which will update all subscribers' homepages. Subscribers to a thread can publish a comment or a vote which is updated for all subscribers immediately.

This is mostly managed through web socket classes in both the front and backend.

In client-server (the unselected directory), communication is instead done through a RESTful API using HTTP endpoints. The server doesn't keep track of client sessions as it doesn't know the client, and can only respond to client requests. As such, the clients each individually need to take iniative to check for new messages or threads, and repeatedly send out requests to the server to check for anything new.

This is managed through service classes in the frontend and controller classes in the backend.

## Reasons for Pub-sub
Pub-sub was ultimately chosen as the better selection over client-server. Client-server may be simpler and more uniform in that you know a request always comes from the client is always responded to by the server. But since the server can't know all the clients, unlike in pub-sub where a channel knows all of its subscribers, a new thread can't be instantly propogated through everyone watching the homepage in client-server, demanding that each client constantly badger the server for updates. Pub-sub on the other hand treats all these clients as subscribers and one client making an upload immediately alerts all subscribers. They don't have to be checking constantly because they know that if they weren't alerted, nothing is new. This immediate response is required for a live chat service such as ours, and the lack of constant checks also makes the service a lot more resource efficient under pub-sub.

## Change from Proposal
Orignally we proposed to implement pub-sub and event bus. These were chosen because they were similar, and we thought that their similarities would make the differences between them even more stark in contrast. But, we got feedback that it might make for a better learning experience implementation wise to pick two architectures that are further apart, and so we switched event bus for client-server instead.
