'use strict';
//import fetchJsonp from 'fetch-jsonp';
// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
//const canvasArrow = require('./canvas-arrow');
const go = require('./go-debug');
//const jquery = require('./jquery');

// end::vars[]

//window.filename = "MED-4771_tmf-validation.trace";
// tag::app[]
class App extends React.Component{
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	    this.fileInput = React.createRef();
	    //this.state = {filename : ""};
		this.state = {id: "", loginStatus:"", responseBody:"", filename : "MED-4771_tmf-validation.trace"};
	}
    componentDidMount(){
    	client({
			method: 'GET',
			//path: "/login?username=admin&passwd=admin"
			path: "/getArrowView?filename="+this.state.filename
		}).done(response => {
			/*this.setState({
	        	id:response.entity.id,
	        	loginStatus:response.entity.loginStatus,
	        	responseBody:response.entity.responseBody
	        });*/
			var nodeDataArray = response.entity.nodeDataArray;
			var linkDataArray = response.entity.linkDataArray;
			linkDataArray.forEach(function(node){
				node.curviness = 20;
			});
			var content = { "class": "go.GraphLinksModel",
					  "nodeKeyProperty": "id",
					  "nodeDataArray": nodeDataArray,
					  "linkDataArray": linkDataArray
			};
					  
			myDiagram.model = go.Model.fromJson(content);
			/*
			var content = { "class": "go.GraphLinksModel",
					  "nodeKeyProperty": "id",
					  "nodeDataArray": [
					    { "id": 0, "loc": "0 0", "text": "Mediator" },
					    { "id": 1, "loc": "900 0", "text": "DEVICES" },
						
					    { "id": 2, "loc": "0 100", "text": "Mediator" },
					    { "id": 3, "loc": "900 100", "text": "CDB" },
						{ "id": 4, "loc": "0 200", "text": "Mediator" },
					    { "id": 5, "loc": "900 200", "text": "MDS" },
						
						
						{ "id": 10000, "loc": "0 300", "text": "Mediator" },
					    { "id": 10001, "loc": "900 300", "text": "DEVICES" },
						
						
					    //{ "id": 4, "loc": "226 226", "text": "Wait" }
					  ],
					  "linkDataArray": [
					    //{ "from": 0, "to": 1, "text": "", "curviness": 0 },
					    
						{ "from": 2, "to": 3, "text": "request", "color":"#7CFC00", "curviness": 20, 'details': 'TMFNAS {"name":"ConfigurableMobileService","description":"This is generic \nmobile service.","state":"active","serviceSpecification":{"name":"Configurable\nMobileService","href":"serviceCatalogManagement/v2/serviceSpecification/\nConfigurable MobileService/89b55fb6-9a96-4fd8-a860-d42328e1374e","version":\n"v1.0.0"},"relatedParty":[{"role":"InstanceConsumerGroup","id":"B2CConsumer\nAndSmallBusiness"},{"role":"PairingId","id":"B2CFORCE"}],"serviceCharacteristic":\n[{"name":"SERVICEPROFILE","value":[{"PROVISION":"TRUE","VALUE":"NORMAL"\n}]},{"name":"PAYMENTTYPE","value":[{"PROVISION":"TRUE","TYPE":\n"SUBSCRIPTION_RETAIL"}]},{"name":"SERVICEPROVIDERID","value":\n[{"PROVISION":"TRUE","VALUE":"0002","TYPE":"PUBLIC"}]},{"name":"SERVICEKEYS",\n"value":[{"PROVISION":"TRUE","UUID":"74ebdc86-5edb-40b1-b369-c26265005041"\n,"SERVICEID":"61417111111","IMSI":"505013400118787"}]}]}' },
					    { "from": 3, "to": 2, "text": "response", "color":"#7CFC00", "curviness": 20 },
						{ "from": 4, "to": 5, "text": "request", "color":"#7CFC00", "curviness": 20 },
					    { "from": 5, "to": 4, "text": "response", "color":"#FF0000", "curviness": 20 },
						
						{ "from": 0, "to": 10000, "text": null,  "curviness": 0 },
						{ "from": 1, "to": 10001, "text": "", "curviness": 0 },
					    
					  ]
					};
			myDiagram.model = go.Model.fromJson(content);*/
		});
       }
    
    handleSubmit(event) {
	    event.preventDefault();
	    /*alert(
	      `Selected file - ${
	        this.fileInput.current.files[0].name
	      }`
	    );*/
	    const file = this.fileInput.current.files[0];
	    const formData = new FormData();
		  formData.append("filename", file);
		  //filename = file.name;
		  const url = "http://localhost:8081/upload";
		  //const url = "http://47.105.81.242:8081/upload";
		  fetch(url,{
		      method:'POST',
		      /*headers:{
			      	'Content-Type':'multipart/form-data',
			      },*/
		      body:formData,
		    })
		    .then((response) => response.text() )
		    .then((responseData)=>{
		 
		      console.log('responseData',responseData);
		      alert("complete!！");
		      //location.reload();
		      //load();
		      //this.state.filename = file.name;
		      client({
					method: 'GET',
					//path: "/login?username=admin&passwd=admin"
					path: "/getArrowView?filename="+file.name
				}).done(response => {
					/*this.setState({
			        	id:response.entity.id,
			        	loginStatus:response.entity.loginStatus,
			        	responseBody:response.entity.responseBody
			        });*/
					var nodeDataArray = response.entity.nodeDataArray;
					var linkDataArray = response.entity.linkDataArray;
					linkDataArray.forEach(function(node){
						node.curviness = 20;
					});
					var content = { "class": "go.GraphLinksModel",
							  "nodeKeyProperty": "id",
							  "nodeDataArray": nodeDataArray,
							  "linkDataArray": linkDataArray
					};
							  
					myDiagram.model = go.Model.fromJson(content);
				});
		    })
		    .catch((error)=>{console.error('error',error)});
	  }

	  render() {
	    return (
	      <form onSubmit={this.handleSubmit} encType="multipart/form-data">
	        <label>
	          Upload file:
	          <input type="file" ref={this.fileInput} />
	        </label>
	        <br />
	        <button type="submit">Submit</button>
	      </form>
	    );
	  }

/*
render(){
	return(
			<div><span>{this.state.id}</span> <span>{this.state.loginStatus}</span> <span>{this.state.responseBody}</span></div>
	);
   }*/
}
/*
ReactDOM.render(
		<App />,
		document.getElementById('react')
	);*/
ReactDOM.render(
		<App />,
		document.getElementById('react0')
	);



/*
class Upload extends React.Component {
	  constructor(props) {
	    super(props);
	    this.state = {value: ''};

	    this.handleChange = this.handleChange.bind(this);
	    this.handleSubmitFile = this.handleSubmitFile.bind(this);
	  }
	  
	  handleChange(event) {
		    this.setState({value: event.target.value});
		  }

	  handleSubmitFile(event) {
		    //alert('new file was submitted: ' + this.state.value);
		    //event.preventDefault();
		  const file = this.state.value;//event.target.files[0];
		    const formData = new FormData();
			  formData.append("filename", file);
			  client({
					method: 'POST',
					//enctype: "multipart/form-data",
					bady:formData,
					path: "/upload"
				}).done(response => {
					alert("complete!！");
				});
		  }
	  

	  render() {
	    return (
	      <form onSubmit={this.handleSubmitFile} encType="multipart/form-data">
	        <label>
	          Name:
	          <input type="file" value={this.state.value} onChange={this.handleChange} />
	        </label>
	        <input type="submit" value="Submit" />
	      </form>
	    );
	  }
	}
*/

/*

class Upload extends React.Component {
	  constructor(props) {
	    super(props);
	    this.handleSubmit = this.handleSubmit.bind(this);
	    this.fileInput = React.createRef();
	    //this.state = {filename : ""};
	  }
	  handleSubmit(event) {
	    event.preventDefault();
	    const file = this.fileInput.current.files[0];
	    const formData = new FormData();
		  formData.append("filename", file);
		  filename = file.name;
		  //const url = "http://localhost:8081/upload";
		  const url = "http://47.105.81.242:8081/upload";
		  fetch(url,{
		      method:'POST',
		      body:formData,
		    })
		    .then((response) => response.text() )
		    .then((responseData)=>{
		 
		      console.log('responseData',responseData);
		      alert("complete!！");
		      //location.reload();
		      //load();
		      ReactDOM.render(
		    			<App />,
		    			document.getElementById('react')
		    		);
		    })
		    .catch((error)=>{console.error('error',error)});
		  //formData.append("encType", "multipart/form-data");
	  }

	  render() {
	    return (
	      <form onSubmit={this.handleSubmit} encType="multipart/form-data">
	        <label>
	          Upload file:
	          <input type="file" ref={this.fileInput} />
	        </label>
	        <br />
	        <button type="submit">Submit</button>
	      </form>
	    );
	  }
	}


ReactDOM.render(
		<Upload />,
		document.getElementById('react0')
	);
*/
/*
class Upload extends React.Component{
	constructor(props) {
		super(props);
		this.state = {file:""};
	}
    componentDidMount(){
    	//var form = document.getElementById('upload'), 
  	  //formData = new FormData(form);
    	
    }
    
    handleSubmit(){
    	client({
			method: 'POST',
			data:formData,
			//path: "/login?username=admin&passwd=admin"
			path: "/upload"
		}).done(response => {
			alert("complete!！");
		});
    }
    
    render(){
    	return(
    			<form onSubmit={this.handleSubmit}> 
    			 <input type="file" value={this.state.file} onChange={this.handleChange}/> 
    			 <input type="button" value="commit" onclick={this.uploadFile}/> 
    			 <span class="showUrl"></span>  
    			</form>
    		); 
  }

ReactDOM.render(
		<Upload />,
		document.getElementById('react2')
	);

*/



// end::render[]