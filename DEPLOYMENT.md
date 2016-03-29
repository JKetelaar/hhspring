### Deploying to a server
We're using DeployHQ to deploy and manage our files on our servers.

For now we only have one active server, synchronized with master.  
This server is located [here](http://hartigehap.spring.ketelaar.me/hh/).

It simply performs the following commands:
- Before deployment: `cd ~/hhspring && mvn t7:stop-forked && exit`
- After deployment: `cd ~/hhspring && mvn t7:start-forked && exit`
