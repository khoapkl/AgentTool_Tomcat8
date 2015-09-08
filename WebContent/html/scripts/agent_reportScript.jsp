<script>
	var selectList = "";
	var selostype = "";
	var selCosmetic = "";
	
	function selectOS(selValue){
		selostype = selValue;
	}
	
	//Add Category to the destination list.	
function addSrcToDestList(avalList, selList) {
		destList = selList;
		srcList = avalList;
		var len = destList.length;
		if(avalList.length > 0){
		for(var i = 0; i < srcList.length; i++) {
			if ((srcList.options[i] != null) && (srcList.options[i].selected)) {
			//Check if this value already exist in the destList or not
			//if not then add it otherwise do not add it.
				var found = false;
				for(var count = 0; count < len; count++) {
					if (destList.options[count] != null) {
						if ((srcList.options[i].text == destList.options[count].text)) {
							found = true;
							break;
						}
					}
				}
				if (found != true) {
				destList.options[len] = new Option(srcList.options[i].text); 
				destList.options[len].value = srcList.options[i].value;
				len++;
				}
			}
		 }
		 selectList = "";
		 for (var i = 0; i < destList.length; i++){
		 	if ((selectList.length) > 0)
				selectList = selectList + ", '" + destList.options[i].value + "'";
			else
				selectList = "'" + destList.options[i].value + "'";
		 }
	}	else alert("The Selected List is Empty!");
		}


	//Deletes Category from the destination list.
function deleteFromDestList(selList) {
	var destList  = selList;
	var len = destList.options.length;
	for(var i = (len-1); i >= 0; i--) {
		if ((destList.options[i] != null) && (destList.options[i].selected == true)) {
			destList.options[i] = null;
	  	}
   	}
   
   	selectList = "";
	for (var i = 0; i < destList.length; i++){
		if ((selectList.length) > 0)
			selectList = selectList + ", '" + destList.options[i].value + "'";
		else
			selectList = "'" + destList.options[i].value + "'";
	 }
}
//Add Brand Type to the destination list.	
function addProcessor(avalList, selList, catselList, date1, date2)
	{
		if (catselList.length > 0)
		{
			var catString = "";
			//generate brand type string
			for (var i = 0; i < catselList.length; i++){
				if ((catString.length) > 0)
					catString = catString + ", '" + catselList.options[i].value + "'";
				else
					catString = "'" + catselList.options[i].value + "'";
			}
			addSrcToDestList(avalList, selList);
			if (selectList.length > 0)
				//showProcessor(selectList, catString, '01/01/2000', '12/30/2011');	
					showProcessor(selectList, catString, date1, date2);		
			else
				$('#processorList').html("<select size=\"10\" name=\"processorList\" multiple > </select>");
		}
		else
		{
			alert("Please select at least one Category ID.");
		}	
	}

function removeProcessor(selList, catselList, date1, date2)
{
	deleteFromDestList(selList);
	var catString = "";
	var brandString = "";
	//generate cat type string
	for (var i = 0; i < catselList.length; i++){
		if ((catString.length) > 0)
			catString = catString + ", '" + catselList.options[i].value + "'";
		else
			catString = "'" + catselList.options[i].value + "'";
	}
	//generate brand type string
	for (var i = 0; i < selList.length; i++){
		if ((brandString.length) > 0)
			brandString = brandString + ", '" + selList.options[i].value + "'";
		else
			brandString = "'" + selList.options[i].value + "'";
	}
		
	if (selList.length > 0)
	//	showProcessor(brandString, catString, '01/01/2000', '12/30/2011');	
		showProcessor(brandString, catString, date1, date2);
	else
		$('#processorList').html("<select size=\"10\" name=\"processorList\" multiple > </select>");
	for (var i = document.report.processorselList.length; i >= 0; i--){
		document.report.processorselList.options[i] = null;
	}
}

function showProcessor(brandType, catType, date1, date2)
{
	$.ajax({			
		url : "order_db.do",
		type : 'POST',
		cache : false,
		data : {
			 method : "showProcessor",				 
			 brandType : brandType,
			 catType : catType,
			 date1:date1,
			 date2:date2
			 		 
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#processorList').html(data);
				}	
			
		},
		error : function() {
			alert("Error occured in ajax call");
		}
	});
}


function addModel(avalList, selList, catselList, date1, date2, procsellist)
{
	if (catselList.length > 0)
	{
		var catString = "";
		var procString = "";
		//generate brand type string
		for (var i = 0; i < catselList.length; i++){
			if ((catString.length) > 0)
				catString = catString + ", '" + catselList.options[i].value + "'";
			else
				catString = "'" + catselList.options[i].value + "'";
		}
		
		for (var i = 0; i < procsellist.length; i++){
			if ((procString.length) > 0)
				procString = procString + ", '" + procsellist.options[i].value + "'";
			else
				procString = "'" + procsellist.options[i].value + "'";
		}
		addSrcToDestList(avalList, selList);
		if (selectList.length > 0 && procString != "")
		{
			//showModel(selectList, catString, '01/01/2000', '12/30/2011', procString);
			showModel(selectList, catString, date1, date2, procString);
		}
		else
		{
			$('#modelLoc').html("<select size=\"10\" name=\"modelList\" multiple > </select>");	
		}
	}
	else
	{
		alert("Please select at least one Category ID.");
	}	
}

function showModel(brandType, catType, date1, date2, procsellist)
{	
	
	var sid= Math.random();
	$.ajax({			
		url : "order_db.do",
		type : 'POST',
		cache : false,
		data : {
			 method : "showModel",				 
			 brandType : brandType,
			 catType : catType,
			 date1:date1,
			 date2:date2,
			 procsellist:procsellist		 
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#modelLoc').html(data);
				}	
			
		},
		error : function() {
			alert("Error occured in ajax call");
		}
	});
}


function removeModel(brandselList, catselList, processorselList, date1, date2)
{
	//deleteFromDestList(brandselList);
	
	var catString = "";
	var brandString = "";
	var processorString = "";
	//generate cat type string
	for (var i = 0; i < catselList.length; i++){
		if ((catString.length) > 0)
			catString = catString + ", '" + catselList.options[i].value + "'";
		else
			catString = "'" + catselList.options[i].value + "'";
	}
	//generate brand type string
	for (var i = 0; i < brandselList.length; i++){
		if ((brandString.length) > 0)
			brandString = brandString + ", '" + brandselList.options[i].value + "'";
		else
			brandString = "'" + brandselList.options[i].value + "'";
	}
	
	//generate processor type string
	for (var i = 0; i < processorselList.length; i++){
		if ((processorString.length) > 0)
			processorString = processorString + ", '" + processorselList.options[i].value + "'";
		else
			processorString = "'" + processorselList.options[i].value + "'";
	}
	
	if (processorselList.length > 0)
		//showModel(brandString, catString, '01/01/2000', '12/30/2011', processorString);
		showModel(brandString, catString, date1, date2, processorString);
	else
		$('#modelLoc').html("<select size=\"10\" name=\"modelLoc\" multiple > </select>");	
		
	for (var i = document.report.modelselList.length; i >= 0; i--){
		document.report.modelselList.options[i] = null;
	}
}

//-----------------
		
		function runReport(){
			var catID = "";
			var brandType = "";
			var modelType = "";
			var checkCatID = "";
			var processorType = "";
			var isSystem = false;
			selCosmetic = $('#cosmetic').val();
			
			//check if any system category is selected
			for (var i = 0; i < document.report.catselList.length; i++){
				checkCatID = document.report.catselList.options[i].value;
				if ((checkCatID == "11946") || (checkCatID == "11947") || (checkCatID == "11949") || (checkCatID == "11950") || (checkCatID == "11958") )
				{
					isSystem = true;
					i = document.report.catselList.length;
				}
				else
				{	
					isSystem = false;
				}
			}
			
			
			if (isSystem == true){
				if ((document.report.catselList.length > 0) && (document.report.brandselList.length > 0) && (document.report.modelselList.length > 0) && (document.report.processorselList.length > 0)){
					for (var i = 0; i < document.report.catselList.length; i++){
						if ((catID.length) > 0)
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = catID + ", '" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = catID + ", '" + document.report.catselList.options[i].value + "'";
						else
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = "'" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = "'" + document.report.catselList.options[i].value + "'";
					}
					
					for (var i = 0; i < document.report.brandselList.length; i++){
						if ((brandType.length) > 0)
							brandType = brandType + ", '" + document.report.brandselList.options[i].value + "'";
						else
							brandType = "'" + document.report.brandselList.options[i].value + "'";
					}
					
					for (var i = 0; i < document.report.modelselList.length; i++){
						if ((modelType.length) > 0)
							modelType = modelType + ", '" + document.report.modelselList.options[i].value + "'";
						else
							modelType = "'" + document.report.modelselList.options[i].value + "'";
					}
					
					for (var i = 0; i < document.report.processorselList.length; i++){
						if ((processorType.length) > 0)
							processorType = processorType + ", '" + document.report.processorselList.options[i].value + "'";
						else
							processorType = "'" + document.report.processorselList.options[i].value + "'";
					}
					
					showreport(catID, selostype, selCosmetic, brandType, modelType, document.report.datebox1.value, document.report.datebox2.value, processorType);		
				}
				else if((document.report.catselList.length > 0) && (document.report.brandselList.length > 0)){

					if((document.report.modelselList.length == 0) && (document.report.processorselList.length == 0)){
					modelType = "'%'";
					processorType = "'%'";
					}
					else{
						for (var i = 0; i < document.report.modelselList.length; i++){
							if ((modelType.length) > 0)
								modelType = modelType + ", '" + document.report.modelselList.options[i].value + "'";
							else
								modelType = "'" + document.report.modelselList.options[i].value + "'";
						}
						
						for (var i = 0; i < document.report.processorselList.length; i++){
							if ((processorType.length) > 0)
								processorType = processorType + ", '" + document.report.processorselList.options[i].value + "'";
							else
								processorType = "'" + document.report.processorselList.options[i].value + "'";
						}
					}
					
					for (var i = 0; i < document.report.catselList.length; i++){
						if ((catID.length) > 0)
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = catID + ", '" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = catID + ", '" + document.report.catselList.options[i].value + "'";
						else
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = "'" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = "'" + document.report.catselList.options[i].value + "'";
					}
					for (var i = 0; i < document.report.brandselList.length; i++){
						str = document.report.brandselList.options[i].value;
						if (str.search("DOCKING") > -1)
						{
							if ((brandType.length) > 0){
								brandType = brandType + ", '" + document.report.brandselList.options[i].value + "'";
							}
							else{
								brandType = "'" + document.report.brandselList.options[i].value+ "'";
							}								
						}
					}
					if(brandType.length>0)
						showreport(catID, selostype, selCosmetic, brandType, modelType, document.report.datebox1.value, document.report.datebox2.value, processorType);
					else if (document.report.processorselList.length <= 0)
							alert("Please select at least one Processor Type.");
					else if (document.report.modelselList.length <= 0)
						alert("Please select at least one Model Number.");
				}	
				else
				{
					if (document.report.catselList.length <= 0)
						alert("Please select at least one Category ID.");
					else if (document.report.brandselList.length <= 0)
						alert("Please select at least one Brand Type.");
					else if (document.report.modelselList.length <= 0)
						alert("Please select at least one Model Number.");
					else if (document.report.processorselList.length <= 0)
						alert("Please select at least one Processor Type.");
				}
			}
			else
			{
				if (document.report.catselList.length > 0){
					for (var i = 0; i < document.report.catselList.length; i++){
						if ((catID.length) > 0)
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = catID + ", '" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = catID + ", '" + document.report.catselList.options[i].value + "'";
						else
							if ((document.report.catselList.options[i].value == "11940") || (document.report.catselList.options[i].value == "11941"))
								catID = "'" + document.report.catselList.options[i].value + "', '11941'";
							else
								catID = "'" + document.report.catselList.options[i].value + "'";
					}
					
					// if catID.search("11961") != -1 is true then check user choose Brand Type 
					if(catID.search("11961") != -1 || catID.search("11962") != -1 || catID.search("11963")  != -1 || catID.search("11940")  != -1 || catID.search("11941")  != -1 || catID.search("11964")  != -1){
						
						if(document.report.brandselList.length>0){
							for (var i = 0; i < document.report.brandselList.length; i++){
								str = document.report.brandselList.options[i].value;
								// if (str.search("VIS") > -1)
								//no check vis and check all	
								{
									if ((brandType.length) > 0)
										brandType = brandType + ", '" + document.report.brandselList.options[i].value + "'";
									else
										brandType = "'" + document.report.brandselList.options[i].value + "'";
									
								}
							}
							if ((brandType.length) <= 0) brandType = "'%'";
							modelType = "'%'";
							processorType = "'%'";
							showreport(catID, selostype, selCosmetic, brandType, modelType, document.report.datebox1.value, document.report.datebox2.value, processorType);
						}else{
							alert("Please select at least one Brand Type.");
						}
						
					}else{
						for (var i = 0; i < document.report.brandselList.length; i++){
							str = document.report.brandselList.options[i].value;
							// if (str.search("VIS") > -1)
							//no check vis and check all	
							{
								if ((brandType.length) > 0)
									brandType = brandType + ", '" + document.report.brandselList.options[i].value + "'";
								else
									brandType = "'" + document.report.brandselList.options[i].value + "'";
								
							}
						}
						if ((brandType.length) <= 0) brandType = "'%'";
						modelType = "'%'";
						processorType = "'%'";
						showreport(catID, selostype ,selCosmetic, brandType, modelType, document.report.datebox1.value, document.report.datebox2.value, processorType);
					}
					//alert("THIS IS BRAND TYPE!!!! " + brandType);
					/* if ((brandType.length) <= 0) brandType = "'%'";
					modelType = "'%'";
					processorType = "'%'";
					showreport(catID, selostype, brandType, modelType, document.report.datebox1.value, document.report.datebox2.value, processorType); */		
				}
				else
					alert("Please select at least one Category ID.");	
				
			}
		}

		function showreport(catid, ostype , cosmetic, brandtype, model, date1, date2, proctype)
		{			
			var sid= Math.random();
			dialogOpen();
			$.ajax({			
				url : "order_db.do",
				type : 'POST',
				cache : false,
				data : {
					 method : "showSummaryReport",
					 catid : catid,				 
					 ostype : ostype,
					 cosmetic : cosmetic,
					 brandType : brandtype,
					 model : model,
					 date1 : date1,
					 date2 : date2,
					 proctype : proctype
				 
				},
				dataType : 'html',
				async : true,
				success : function(html) {
					var data = html.replace(/[\r\n]+/g, "");
					if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
						document.location = "<%=request.getContextPath()%>/authenticate.do";
					}
					else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
						document.location = "<%=request.getContextPath()%>/login.do";
					}
					else{
						$('#report_result').html(data);
						}
					dialogClose();						
				},
				error : function() {
					alert("Error occured in ajax call");
				}
			});
		}
		function removeCatelogy(selList){
			deleteFromDestList(selList);
			if(selList.length == 0 ){
				$('#brandselDiv').html("<select size=\"10\" name=\"brandselList\" multiple > </select>");
				$('#processorList').html("<select size=\"10\" name=\"processorList\" multiple > </select>");	
				$('#processorselDiv').html("<select size=\"10\" name=\"processorselList\" multiple > </select>");	
				$('#modelLoc').html("<select size=\"10\" name=\"modelList\" multiple > </select>");	
				$('#modelselDiv').html("<select size=\"10\" name=\"modelselList\" multiple > </select>");		
				}
			}
		function clearSelected(){			
			$('#catlDiv').html("<select size=\"10\" name=\"catselList\" multiple > </select>");
			$('#brandselDiv').html("<select size=\"10\" name=\"brandselList\" multiple > </select>");
			$('#processorList').html("<select size=\"10\" name=\"processorList\" multiple > </select>");	
			$('#processorselDiv').html("<select size=\"10\" name=\"processorselList\" multiple > </select>");	
			$('#modelLoc').html("<select size=\"10\" name=\"modelList\" multiple > </select>");	
			$('#modelselDiv').html("<select size=\"10\" name=\"modelselList\" multiple > </select>");		
			}
		$('#pageTitle').text("Orders Summary Report");
		$('#topTitle').text("Orders Summary Report");

		</script>