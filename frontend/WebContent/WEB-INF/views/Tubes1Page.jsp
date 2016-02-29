<!DOCTYPE html>
<html lang="en">
  <jsp:include page="/WEB-INF/views/Head.jsp"></jsp:include>
  <body>  
  <jsp:include page="/WEB-INF/views/Header.jsp"></jsp:include>
    <header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
					<h4 class="text-center" >${msg}</h4>
	   				<div class="col-md-1"></div>
                    
                	<div class="col-md-10" >

						
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
	   							Input File untuk disisipkan :
	   						</div>
	   						
	   						<div class="col-md-9" >
						    	<div class="form-group">
							        <form id="formUploadFileUntukDisisipkan" style="margin-top:5px"
							         method="POST" enctype="multipart/form-data" action="upload">
										<div class="col-md-2" style="padding-left:0px;">
											<a class='btn btn-default' href='javascript:;'>
									            Choose File
									            <input required type="file" name="file" 
									            style='position:absolute;z-index:2;top:0;left:0;filter: 
									            alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
									            opacity:0;background-color:transparent;color:transparent;' name="file_source" size="40"  
									            onchange='$("#file-name").html($(this).val().replace(/C:\\fakepath\\/i,""));'>
									        </a>
										</div>
										<div class="col-md-7">
							    			<textarea required  name="name" id="file-name"  rows="1" maxlength="25"  style="padding: 6px 12px; width:100%;
							    			 border-radius: 4px;" placeholder="Input a new filename!"></textarea>
										</div>
										<div class="col-md-2">
											<button type="submit"  class="btn btn-default" >Upload </button>
		                    			</div>
									</form>
								</div>
							
							</div>
						</div>
						
						
						
						<div class="row" style="margin-top: 5px;">
	   						<div class="col-md-3" style="margin-top: 5px;">
	   							Input Gambar :
	   						</div>
	   						
	   						<div class="col-md-9" >
						    	<div class="form-group">
							        <form id="formUploadFile" style="margin-top:5px"
							         method="POST" enctype="multipart/form-data" action="upload">
										<div class="col-md-2" style="padding-left:0px;">
											<a class='btn btn-default' href='javascript:;'>
									            Choose Image
									            <input required type="file" name="file" 
									            style='position:absolute;z-index:2;top:0;left:0;filter: 
									            alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
									            opacity:0;background-color:transparent;color:transparent;' name="file_source" size="40"  
									            onchange='$("#name").html($(this).val().replace(/C:\\fakepath\\/i,""));'>
									        </a>
										</div>
										<div class="col-md-7">
							    			<textarea required  name="name" id="name"  rows="1" maxlength="25"  style="padding: 6px 12px; width:100%;
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
							<button type="button" class="btn btn-success" onclick="startExecuting(0)">Embed secret file </button>
	                    
							<button type="button" class="btn btn-primary" onclick="startExecuting(1)">Get secret file </button>
							<button type="button" class="btn btn-info" onclick="compareImage()">Compare image </button>
							<button type="button" class="btn btn-info" onclick="checkCapacity()">Check capacity </button>
	                    </div>
                    	
                    	<hr>
                    	
                    <br>
                    <br>
                    <div class="row " id="compareImage" style="">
                    	<div class="col-md-6 text-center">
							<div class="row">
							  	<p>Before</p>
								<img style="width:300px; height: 100%;" id="imageBefore" class="object-fit_fill" src="">
							</div>    	
                    	</div>
                    	<div class="col-md-6  text-center">
							<div class=" row">
							  	<p>After</p>   
							</div>
							<div> 
								<img style="width:300px; height: 100%;" id="imageAfter" class="object-fit_fill" src="">
							</div>   
							<div class="row" id="buttonDownload">
							</div>
                    	</div>
                </div>
                <div class="row" id="buttonDownloadSecretFile">
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
	

 	$('#formUploadFileUntukDisisipkan').submit(function(event){
	    $.ajax({
	      url: $('#formUploadFileUntukDisisipkan').attr('action'),
	      data : new FormData($("#formUploadFileUntukDisisipkan")[0]),
	      dataType: 'text',
	      enctype: 'multipart/form-data',
	      processData: false,
	      contentType: false,
	      type: 'POST',
	      success:  function(data) {
	    	  alert('File is uploaded!');
	    	  $('#upload-file-info').css('background-color', '#5CB85C');
	      }
	    });
	    return false;
	}); 
	
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
	    	  alert('Image is uploaded!');
	    	  $('#upload-file-info').css('background-color', '#5CB85C');
	      }
	    });
	    return false;
	}); 
	
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

	    key = document.getElementById("key").value;
	    if(key.length == 0){
	    	alert("Masukkan key yang Anda gunakan!")
	    	return false;
	    }
	    
	    fileInput = document.getElementById("name").innerHTML;
	    fileInputUntukDiSisipkan = document.getElementById("file-name").innerHTML;
	    
	    if( fileInput.length == 0){
	    	alert("Masukkan input gambar!")
	    	return false;
	    }
	    
	    http.open("POST", "/KriptografiProject/tubes1/execute", true);
	    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    var params = "&fileInput="+fileInput
	    	+ "&operationType="+operationType
	    	+ "&fileInputUntukDiSisipkan="+fileInputUntukDiSisipkan
	    	+ "&key="+key
	    	;
	    http.send((params));
	    http.onload = function() {
		    	alert(http.responseText)
	         	if(idOperasi==0){
	         		 $("#imageBefore").attr('src',"../resources/img/"+fileInput);
	         		 $("#imageAfter").attr('src',"../resources/img/"+fileInput+".bmp");
	         		document.getElementById("buttonDownload").innerHTML = "<a type=\"btn\" href=\"../resources/img/"+fileInput+".bmp\" download=\"New"+fileInput+"\" title=\"ImageName\">"
					  +"<button type=\"button\"class=\"btn btn-info\">Download</button></a>";
	  	    	  $('#compareImage').css('display', '');
	         	}else{ 
		  	      $('#compareImage').css('display', 'none');
         		document.getElementById("buttonDownloadSecretFile").innerHTML = "<a type=\"btn\" href='"+http.responseText+"' download=\""+fileInputUntukDiSisipkan+"\" title=\"ImageName\">"
				  +"<button type=\"button\"class=\"btn btn-info\">Download Secret File</button></a>";
	         	}
	      }
	    }
	
	function compareImage() {
	    var http = new XMLHttpRequest();	     
	    
	    fileInput = document.getElementById("name").innerHTML;
	    
	    if( fileInput.length == 0){
	    	alert("Masukkan input gambar!")
	    	return false;
	    }
	    
	    http.open("POST", "/KriptografiProject/tubes1/compareimage", true);
	    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    var params = "&fileInput="+fileInput
	    	+ "&operationType="+operationType
	    	;
	    http.send((params));
	    http.onload = function() {
		    	alert(http.responseText);
	      }
	    }
	
	function checkCapacity() {
	    var http = new XMLHttpRequest();	     
	    
	    fileInput = document.getElementById("name").innerHTML;
	    
	    if( fileInput.length == 0){
	    	alert("Masukkan input gambar!")
	    	return false;
	    }
	    
	    http.open("POST", "/KriptografiProject/tubes1/checkcapacity", true);
	    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    var params = "&fileInput="+fileInput
	    	+ "&operationType="+operationType
	    	;
	    http.send((params));
	    http.onload = function() {
		    	alert(http.responseText);
	      }
	    }
	
	</script>
	

 </body>
</html>