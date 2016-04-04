### Deploying to a server
We're using DeployHQ to deploy and manage our files on our servers.

For now we only have one active server, synchronized with master.  
This server is located [here](http://hartigehap.spring.ketelaar.me/hh/).

It simply performs the following commands:
- Before deployment: `cd ~/hhspring && mvn t7:stop-forked && exit`
- After deployment: `cd ~/hhspring && mvn t7:start-forked && exit`

As the base of the application runs on port 8080, we've added the following iptables rule to allow redirect from 80 to 8080.
```
iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 80 -j REDIRECT --to-port 8080
```
In that way we don't have to adjust the system settings.
