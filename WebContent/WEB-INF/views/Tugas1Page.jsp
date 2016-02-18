<!DOCTYPE html>
<html lang="en">
  <jsp:include page="/WEB-INF/views/Head.jsp"></jsp:include>
  <body>  
  <jsp:include page="/WEB-INF/views/Header.jsp"></jsp:include>
    <header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
					<h4 class="text-center" style="margin-top: 70px;">${msg}</h4>
	   				<div class="col-md-1"></div>
                    
                	<div class="col-md-10" >

   						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Pemilihan algoritma :
	   						</div>
	   						
	   						<div class="col-md-9" >
		   						<div class="dropdown">
								  <button id="selectedAlgorithm"  class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								    Algoritma
								    <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								    <li><a onclick="showKeyInstruction(0)" href="#" data-value="0">a) Vigenere Cipher </a></li>
								    <li><a onclick="showKeyInstruction(1)" href="#" data-value="1">b) Vigenere Cipher extended </a></li>
								    <li><a onclick="showKeyInstruction(2)" href="#" data-value="2">c) Playfair Cipher</a></li>
								    <li><a onclick="showKeyInstruction(3)" href="#" data-value="3">d) Cipher substitusi abjad-tunggal</a></li>
								  </ul>
								</div>
							</div>
						</div>
						
						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Kunci :
	   						</div>
	   						
	   						<div class="col-md-9">
						    	<textarea id="key"  rows="1" maxlength="25"  style="    padding: 6px 12px; width:100%; border-radius: 4px;" placeholder="Select the algorithm before entering the key"></textarea>
								<div id="textarea_feedback"></div>
							</div>
						</div>
						
						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Model Teks Hasil :
	   						</div>
	   						
	   						<div class="col-md-9" >
		   						<div class="dropdown">
								  <button id="resultModel" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								    Model Hasil
								    <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								    <li><a href="#" data-value="0">a) Apa adanya </a></li>
								    <li><a href="#" data-value="1">b) Tanpa spasi </a></li>
								    <li><a href="#" data-value="2">c) Dalam kelompok 5 huruf</a></li>
								  </ul>
								</div>
							</div>
						</div>
						
						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Model Teks Input :
	   						</div>
	   						
	   						<div class="col-md-9" >
		   						<div class="dropdown">
								  <button id="inputModel" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								    Model Input
								    <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								    <li><a href="#" data-value="0">a) Apa adanya </a></li>
								    <li><a href="#" data-value="1">b) Tanpa spasi </a></li>
								    <li><a href="#" data-value="2">c) Dalam kelompok 5 huruf</a></li>
								  </ul>
								</div>
							</div>
						</div>
						
						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Input Teks :
	   						</div>
	   						
	   						<div class="col-md-9" >
						    	<div class="form-group">
						      		<textarea id="textInput" class="form-control" rows="5"></textarea>
							        <form id="formUploadFile" style="margin-top:5px"
							         method="POST" enctype="multipart/form-data" action="upload">
									
									<div class="col-md-6" style="padding-left:0px;">
										<a class='btn btn-default' href='javascript:;'>
								            Open File
								            <input required type="file" name="file" 
								            style='position:absolute;z-index:2;top:0;left:0;filter: 
								            alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
								            opacity:0;background-color:transparent;color:transparent;' name="file_source" size="40"  
								            onchange='$("#upload-file-info").html($(this).val());'>
								        </a>
							        <span class='label label-info' id="upload-file-info"></span>
									</div>
									<div class="col-md-4">
						    			<textarea required  name="name" id="name"  rows="1" maxlength="25"  style="    padding: 6px 12px; width:100%;
						    			 border-radius: 4px;" placeholder="Input a new filename!"></textarea>
									</div>
									<div class="col-md-2">
										<button type="submit"  class="btn btn-default" >Upload </button>
	                    			</div>
									</form>
								</div>
							
							</div>
						</div>
						
	                    <div class="text-right" style="margin-top:10px">
							<button type="button" class="btn btn-success" onclick="startExecuting(0)">Encrypt </button>
	                    
							<button type="button" class="btn btn-primary" onclick="startExecuting(1)">Decrypt </button>
	                    </div>
                    	
                    	<hr>
                    	
						<div class="row" style="margin-top: 15px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Result :
	   						</div>
	   						
	   						<div class="col-md-9">
		   						
		   						<form role="form">
							    	<div class="form-group">
							      	<textarea class="form-control" rows="5" id="result"></textarea>
							    	</div>
							  	</form>
	                    		<div class="text-right">
								  	<button type="button" onclick="useTextFile()" class="btn btn-success">Use Text File</button>
								  	
								  	<button type="button" class="btn btn-default">Save to File</button>
								  	<button type="button" onclick="copyResultAsInput()" class="btn btn-info">Copy as Input Teks</button>
								  	<button type="button" onclick="clearResult()" class="btn btn-danger">Clear</button>
								</div>
								
								<div>
								
								Catatan :
								<br>
								1. Algoritma 1 => Model Hasil dan Model Input bisa diterapkan dengan baik
								<br>
								2. Algoritma 2 => Model Hasil <b>Tanpa spasi</b> dan Model Input <b>Tanpa spasi</b> tidak bisa diterapkan karena sebagian data akan hilang
								<br>
								2. Algoritma 3 => Model Hasil <b>Tanpa spasi</b> dan Model Input <b>Tanpa spasi</b> tidak bisa diterapkan karena secara default algoritma ini tidak memproses spasi
								</div>
							
							</div>
						</div>
						
						
                    </div>
                    
                </div>
            </div>
        </div>
    </header>
	
	<script>
	$(document).ready(function() {
	    var text_max = 25;
	    $('#textarea_feedback').html('Tersisa '+text_max +' karakter' );

	    $('#key').keyup(function() {
	        var text_length = $('#key').val().length;
	        var text_remaining = text_max - text_length;

	        $('#textarea_feedback').html( 'Tersisa '+ text_remaining +' karakter');
	    });
	});
	
	function useBinaryFile(){
  	  $('#upload-file-info').html("outputBinaryFile");
	    document.getElementById("textInput").value = "";
	    document.getElementById("result").value = "";
  	    $('#name').attr('placeholder', 'file is ready!!');
	}

	function useTextFile(){
  	  $('#upload-file-info').html("outputTextFile");
	    document.getElementById("textInput").value = "";
	    document.getElementById("result").value = "";
  	    $('#name').attr('placeholder', 'file is ready!');
	}


 	$('#formUploadFile').submit(function(event){
	    $.ajax({
	      url: $('#formUploadFile').attr('action'),
	      data : new FormData($("#formUploadFile")[0]),
	      dataType: 'text',
	      enctype: 'multipart/form-data',
	      processData: false,
	      contentType: false,
	      type: 'POST',
	      success:  function(data) {
	    	  $('#upload-file-info').html(document.getElementById('name').value);
	    	  document.getElementById('textInput').value = "";
	    	  document.getElementById('name').value = "";
	    	  $('#name').attr('placeholder', 'File is uploaded!');
	    	  $('#upload-file-info').css('background-color', '#5CB85C');
	      }
	    });
	    return false;
	}); 
	
	function showKeyInstruction(id){
		if(id=="0")
			keyInstruction = "only accept 26 Latin Alphabet lower case"
		else if(id=="1")
			keyInstruction = "You can choose within 256 ASCII characters"
		else if(id=="3")
			keyInstruction = "Integer between 1-25"
		else
			keyInstruction = "CAPSLOCK should be ON and only use 26 Latin Alphabet"
			
		$('#key').attr("placeholder",keyInstruction);
	}
	
	function copyResultAsInput(){
	    result = document.getElementById("result").value;
	    document.getElementById("textInput").value = result;
  	    $('#upload-file-info').html(null);
	    document.getElementById("result").value = "";
  	    $('#name').attr('placeholder', 'Input a new filename!');
	    return false;
	}
	
	function clearResult(){
	    document.getElementById("result").value = "";
	    return false;
	}
	
	$(".dropdown-menu li a").click(function(){
		
		  $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
		  $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
		});
	
	 function startExecuting(idOperasi) {
	    var http = new XMLHttpRequest();
	    operationType = idOperasi;

	    selectedAlgorithm = document.getElementById("selectedAlgorithm").value;
	    if(selectedAlgorithm.length == 0){
	    	alert("Pilih jenis algoritma yang Anda inginkan!")
	    	return false;
	    }

	    key = document.getElementById("key").value;
	    if(key.length == 0){
	    	alert("Masukkan key yang Anda gunakan!")
	    	return false;
	    }
	    
	    resultModel = document.getElementById("resultModel").value;
	    if(resultModel.length == 0){
	    	alert("Pilih model teks hasil yang Anda inginkan!")
	    	return false;
	    }
	    
	    inputModel = document.getElementById("inputModel").value;
	    if(inputModel.length == 0){
	    	alert("Pilih model teks input yang sesuai!")
	    	return false;
	    }
	    
	    textInput = document.getElementById("textInput").value;
	    fileInput = document.getElementById("upload-file-info").innerHTML;
	    if(textInput.length == 0 && fileInput.length == 0 ){
	    	alert("Masukkan input teks yang Anda inginkan!")
	    	return false;
	    } else if(textInput.length != 0 && fileInput.length != 0){
	    	alert("Masukkan teks input dari text area atau dari file, jangan keduanya!!")
	    	return false;
	    }
	    
	    http.open("POST", "/KriptografiProject/tugas1/execute", true);
	    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    var params = "textInput=" +textInput 
	    	+ "&fileInput="+fileInput
	    	+ "&operationType="+operationType
	    	+ "&selectedAlgorithm="+selectedAlgorithm
	    	+ "&resultModel="+resultModel
	    	+ "&inputModel="+inputModel
	    	+ "&key="+key
	    	;
	    http.send((params));
	    http.onload = function() {
	         	document.getElementById('result').value = http.responseText;	        
	      }
	    }
	
	</script>
	

 </body>
</html>