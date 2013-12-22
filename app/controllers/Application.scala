package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.Concurrent
import play.api.libs.iteratee.Iteratee
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {
       
	  def index =  WebSocket.using[String] { request =>
	 
		   //Concurernt.broadcast returns (Enumerator, Concurrent.Channel)
		    val (out,channel) = Concurrent.broadcast[String]
		 
		    //log the message to stdout and send response back to client
		    val in = Iteratee.foreach[String] {
		      		   
		        msg => println(msg)
		        //the Enumerator returned by Concurrent.broadcast subscribes to the channel and will 
		    	//receive the pushed messages

		    	channel push("RESPONSE: " + msg)
		    }
		    (in,out)
	  }
	   
}