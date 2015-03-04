var formList = document.querySelectorAll('.answerWrite input[type=submit]');
for ( var j=0 ; j < formList.length ; j++) {
	formList[j].addEventListener('click', writeAnswers, false);
}

function writeAnswers(e) {
	 e.preventDefault();
	 
	 var answerForm = e.currentTarget.form;
	 var url = "/api/addanswer.next";
	 var params = "questionId=" + answerForm[0].value + "&writer=" + answerForm[1].value + "&contents=" + answerForm[2].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 location.reload(true);
		 }
	 }
	 
	 request.send(params);
}

var deleteBtnList = document.querySelectorAll('.comment a');
for ( var j=0 ; j < deleteBtnList.length ; j++) {
	deleteBtnList[j].addEventListener('click', deleteAnswers, false);
}

function deleteAnswers(e) {
	 e.preventDefault();

	 var answerId = e.currentTarget.dataset.answerno;
	 var questionId = e.currentTarget.dataset.questionno;

	 var url = "/api/deleteanswer.next";
	 var params = "answerId=" + answerId + "&questionId=" + questionId;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 location.reload(true);
		 }
	 }
	 
	 request.send(params);
}

