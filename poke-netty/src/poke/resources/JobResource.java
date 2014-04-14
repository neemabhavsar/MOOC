/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import eye.Comm.JobOperation;
import eye.Comm.JobOperation.JobAction;
import eye.Comm.NameValueSet;
import eye.Comm.JobDesc.JobCode;
import eye.Comm.NameValueSet.NodeType;
import eye.Comm.JobDesc;
import eye.Comm.Payload;
import eye.Comm.PokeStatus;
import eye.Comm.Request;
import eye.Comm.SignUp;

public class JobResource implements Resource {
	
	protected static Logger logger = LoggerFactory.getLogger("server");
	private Request.Builder rb;
	

	@Override
	public Request process(Request request) {

		// TODO add code to process the message/event received
		logger.info("Outside Request for ListCourses ::" + request.getBody().getJobOp().getData().getNameSpace()
				+ " :: " +  request.getBody().getJobOp().getData().getJobId());
		
		if(request.getBody().getJobOp().getData().getNameSpace().equals("listcourses")){
			logger.info("Request for ListCourses ::" + request.getBody().getJobOp().getData().getOptions().getName()
					+ " :: " +  request.getBody().getJobOp().getData().getOptions().getValue());
			
			//DB Call to Fetch

			
			Payload.Builder pb = Payload.newBuilder();
			
			NameValueSet.Builder nb1 = NameValueSet.newBuilder();
			nb1.setName("coursename");
			nb1.setValue("CMPE277");
			nb1.setNodeType(NodeType.VALUE);
			
			
			NameValueSet.Builder nb3 = NameValueSet.newBuilder();
			nb3.setName("coursename");
			nb3.setValue("CMPE273");
			nb3.setNodeType(NodeType.VALUE);
			
			
			NameValueSet.Builder nb4 = NameValueSet.newBuilder();
			nb4.setName("coursename");
			nb4.setValue("CMPE239");
			nb4.setNodeType(NodeType.VALUE);
			
			
			NameValueSet.Builder nb2 = NameValueSet.newBuilder();
			nb2.setName("coursename");
			nb2.setValue("CMPE275");
			nb2.setNodeType(NodeType.VALUE);
			
			
			NameValueSet.Builder main = NameValueSet.newBuilder();
			main.setNodeType(NodeType.NODE);
			main.addNode(0, nb1.build());
			main.addNode(1, nb2.build());
			main.addNode(2, nb3.build());
			main.addNode(3, nb4.build());
			
			
			
			JobDesc.Builder jb = JobDesc.newBuilder();
			jb.setNameSpace("listcourses");
			jb.setOwnerId(request.getBody().getJobOp().getData().getOwnerId());
			jb.setJobId("listcourses");
			jb.setStatus(JobCode.JOBRECEIVED);
			jb.setOptions(main.build());
			
			JobOperation.Builder jo = JobOperation.newBuilder();
			jo.setData(jb.build());
			jo.setAction(JobAction.REMOVEJOB);
			
			pb.setJobOp(jo.build());
			rb = Request.newBuilder();

			// metadata
			rb.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
					PokeStatus.SUCCESS, "Fetch List Of Courses"));

			rb.setBody(pb.build());
				
		}
		else if(request.getBody().getJobOp().getData().getNameSpace().equals("coursedescription")){
			
			logger.info("coursedescription: " + request.getBody().getJobOp().getData().getOptions().getName()
					+ " " + request.getBody().getJobOp().getData().getOptions().getValue());
			String desc = "";
			if (request.getBody().getJobOp().getData().getOptions().getValue().equals("CMPE277"))
			{
				desc = "Architectures, technologies, and programming concepts for developing smartphone applications. " +
						"Covers current smartphone/tablet OSs, application development, and deployment environments. " +
						"Prerequisites: Classified graduate standing or instructor consent";
			}else if (request.getBody().getJobOp().getData().getOptions().getValue().equals("CMPE275"))
			{
				desc = "Enterprise Application Development ::: Distributed component design, scalability, messaging, and integration practices for modern and" +
						" emerging architectures and technologies. Prerequisite: CMPE 273 or instructor consent.";
			}

			Payload.Builder pb = Payload.newBuilder();
			
			NameValueSet.Builder nb1 = NameValueSet.newBuilder();
			nb1.setName("coursedescription");
			nb1.setValue(desc);
			nb1.setNodeType(NodeType.VALUE);
			
			JobDesc.Builder jb = JobDesc.newBuilder();
			jb.setNameSpace("listcourses");
			jb.setOwnerId(request.getBody().getJobOp().getData().getOwnerId());
			jb.setJobId("listcourses");
			jb.setStatus(JobCode.JOBRECEIVED);
			jb.setOptions(nb1.build());
			
			JobOperation.Builder jo = JobOperation.newBuilder();
			jo.setData(jb.build());
			jo.setAction(JobAction.REMOVEJOB);
			
			pb.setJobOp(jo.build());
			rb = Request.newBuilder();

			// metadata
			rb.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
					PokeStatus.SUCCESS, "Fetch desc Of Courses"));

			rb.setBody(pb.build());

			
		}else if(request.getBody().getJobOp().getData().getNameSpace().equals("signup")){
   		 	logger.info("signup: " + request.getBody().getJobOp().getData().getOptions().getName()
                	+ " " + request.getBody().getJobOp().getData().getOptions().getValue());
        String desc = "";

        
        String fname ="" , lname = "", email = "", pwd = "";
        if (request.getBody().getJobOp().getData().getOptions().getNode(0).getName().equals("fname"))
        	fname =  request.getBody().getJobOp().getData().getOptions().getNode(0).getValue();
        if (request.getBody().getJobOp().getData().getOptions().getNode(1).getName().equals("lname"))
        	lname = request.getBody().getJobOp().getData().getOptions().getNode(1).getValue();
        if (request.getBody().getJobOp().getData().getOptions().getNode(2).getName().equals("email"))
            email = request.getBody().getJobOp().getData().getOptions().getNode(2).getValue();
        if (request.getBody().getJobOp().getData().getOptions().getNode(3).getName().equals("password"))
            pwd = request.getBody().getJobOp().getData().getOptions().getNode(3).getValue();
            
   	 
    	logger.info("##########"+ fname + "  " + lname + " " + email + " " + pwd + "##########");

   	 
    	Payload.Builder pb = Payload.newBuilder();
       	 
        	NameValueSet.Builder nb1 = NameValueSet.newBuilder();
        	nb1.setName("signup");
        	nb1.setValue("Signup successfull - User Added to the Database");
        	nb1.setNodeType(NodeType.VALUE);
       	 
        	JobDesc.Builder jb = JobDesc.newBuilder();
        	jb.setNameSpace("signup");
        	jb.setOwnerId(request.getBody().getJobOp().getData().getOwnerId());
        	jb.setJobId("signup");
        	jb.setStatus(JobCode.JOBRECEIVED);
        	jb.setOptions(nb1.build());
       	 
        	JobOperation.Builder jo = JobOperation.newBuilder();
        	jo.setData(jb.build());
        	jo.setAction(JobAction.REMOVEJOB);
       	 
        	pb.setJobOp(jo.build());
        	rb = Request.newBuilder();

        	// metadata
        	rb.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
                	PokeStatus.SUCCESS, "signup done"));

        	rb.setBody(pb.build());    	 
    }
		else if(request.getBody().getJobOp().getData().getNameSpace().equals("signin")){
   		 	logger.info("##############signin: " + request.getBody().getJobOp().getData().getOptions().getName()
                	+ " " + request.getBody().getJobOp().getData().getOptions().getValue());
                
        String email = "", pwd = "";
        if (request.getBody().getJobOp().getData().getOptions().getNode(0).getName().equals("email"))
            email = request.getBody().getJobOp().getData().getOptions().getNode(0).getValue();
        if (request.getBody().getJobOp().getData().getOptions().getNode(0).getName().equals("password"))
            pwd = request.getBody().getJobOp().getData().getOptions().getNode(1).getValue();
   	 
    	logger.info(email + " " + pwd);
		
    	Payload.Builder pb = Payload.newBuilder();
       	 
        	NameValueSet.Builder nb1 = NameValueSet.newBuilder();
        	nb1.setName("signin");
        	nb1.setValue("Sign-in Successfull :: User Credentials was Validated");
        	nb1.setNodeType(NodeType.VALUE);
        
        	JobDesc.Builder jb = JobDesc.newBuilder();
        	jb.setNameSpace("signin");
        	jb.setOwnerId(request.getBody().getJobOp().getData().getOwnerId());
        	jb.setJobId("signin");
        	jb.setStatus(JobCode.JOBRECEIVED);
        	jb.setOptions(nb1.build());
       	 
        	JobOperation.Builder jo = JobOperation.newBuilder();
        	jo.setData(jb.build());
        	jo.setAction(JobAction.REMOVEJOB);
       	 
        	pb.setJobOp(jo.build());
        	rb = Request.newBuilder();

        	// metadata
        	rb.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
                	PokeStatus.SUCCESS, "signin done"));

        	rb.setBody(pb.build());    	 
    }	

		Request reply = rb.build();
		return reply;
	}

}
